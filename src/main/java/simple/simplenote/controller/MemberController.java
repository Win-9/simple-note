package simple.simplenote.controller;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import simple.simplenote.controller.form.LogInForm;
import simple.simplenote.controller.form.statusform.LoginStatusForm;
import simple.simplenote.controller.form.statusform.SignUpStatusForm;
import simple.simplenote.domain.Member;
import simple.simplenote.encode.RSA;
import simple.simplenote.encode.SHA256;
import simple.simplenote.service.MemberService;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;



@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
@Transactional(readOnly = true)
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class MemberController {

    private final ObjectMapper objectMapper;
    private final MemberService memberService;

    @PostMapping("/sign-in")
    @ResponseBody
    @Transactional(readOnly = false)
    public ResponseEntity<LoginStatusForm> logInMember(@RequestBody LogInForm logInForm)
            throws NoSuchAlgorithmException{

        Member findMember = memberService.findByName(logInForm.getNickname());
        ResponseEntity<LoginStatusForm> loginStatusForm1 = validateLoginInfo(logInForm, findMember);
        if (loginStatusForm1 != null) return loginStatusForm1;


        LoginStatusForm loginStatusForm =
                new LoginStatusForm(true, true,"Correct Id");

        return new ResponseEntity<>(loginStatusForm, HttpStatus.OK);

    }

    private ResponseEntity<LoginStatusForm> validateLoginInfo(LogInForm logInForm, Member findMember)
            throws NoSuchAlgorithmException {
        if (findMember == null){
            LoginStatusForm loginStatusForm =
                    new LoginStatusForm(false, false,"NoneExistId");
            return new ResponseEntity<>(loginStatusForm, HttpStatus.FORBIDDEN);
        }

        String privateKey = RSA.decode(logInForm.getPassword(), RSA.rsaKeyPair.get("privateKey"));

        if (!(findMember.getPassWord()).equals(SHA256.encrypt(privateKey))){
            LoginStatusForm loginStatusForm =
                    new LoginStatusForm(true, false,"Incorrect Password");
            return new ResponseEntity<>(loginStatusForm, HttpStatus.FORBIDDEN);
        }
        return null;
    }


    @PostMapping("/sign-up")
    @ResponseBody
    @Transactional(readOnly = false)
    public ResponseEntity<SignUpStatusForm> signUpMember(@RequestBody LogInForm logInForm)
            throws NoSuchAlgorithmException {//?????????, ???????????? ?????????????????????

        ResponseEntity<SignUpStatusForm> SignUpStatusForm = validateSignUpInfo(logInForm);
        if (SignUpStatusForm != null) return SignUpStatusForm;

        String privateKey = RSA.decode(logInForm.getPassword(), RSA.rsaKeyPair.get("privateKey"));
        log.info("privateKey={}",privateKey);

        log.info("logInMember={}",logInForm.getNickname());

        Member signUpMember = new Member();

        signUpMember.setNickName(logInForm.getNickname());

        signUpMember.setPassWord(SHA256.encrypt(privateKey));

        memberService.addMember(signUpMember);
        log.info("memberId={}",signUpMember.getPassWord());

        return new ResponseEntity<>(new SignUpStatusForm(true, "Registered"), HttpStatus.OK);
    }

    private ResponseEntity<SignUpStatusForm> validateSignUpInfo(LogInForm logInForm) {
        if (memberService.findByName(logInForm.getNickname()) != null) {
            SignUpStatusForm SignUpStatusForm = new SignUpStatusForm(false,"Duplicated Id");
            return new ResponseEntity<>(SignUpStatusForm, HttpStatus.FORBIDDEN);
        }
        return null;
    }

}
