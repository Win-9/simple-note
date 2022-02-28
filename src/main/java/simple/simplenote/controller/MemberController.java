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
import simple.simplenote.service.MemberService;


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
    public ResponseEntity<LoginStatusForm> logInMember(@RequestBody LogInForm logInForm) throws JsonProcessingException {

        Member findMember = memberService.findByName(logInForm.getNickname());
        if (findMember == null){
            LoginStatusForm loginStatusForm =
                    new LoginStatusForm(false, false,"NoneExistId");
            return new ResponseEntity<>(loginStatusForm, HttpStatus.FORBIDDEN);
        }

        if (!findMember.getPassWord().equals(logInForm.getPassword())){
            LoginStatusForm loginStatusForm =
                    new LoginStatusForm(true, false,"Incorrect Password");
            return new ResponseEntity<>(loginStatusForm, HttpStatus.FORBIDDEN);
        }

        LoginStatusForm loginStatusForm =
                new LoginStatusForm(true, true,"Correct Id");
        return new ResponseEntity<>(loginStatusForm, HttpStatus.OK);

    }

    @PostMapping("/sign-up")
    @ResponseBody
    @Transactional(readOnly = false)
    public ResponseEntity<SignUpStatusForm> signUpMember(@RequestBody LogInForm logInForm) throws JsonProcessingException {//회원폼, 로그인폼 동일하기때문에


        if (memberService.findByName(logInForm.getNickname()) != null) {
            SignUpStatusForm SignUpStatusForm = new SignUpStatusForm(false,"Duplicated Id");
            return new ResponseEntity<>(SignUpStatusForm, HttpStatus.FORBIDDEN);
        }

        log.info("logInMember={}",logInForm.getNickname());

        Member signUpMember = new Member();

        signUpMember.setNickName(logInForm.getNickname());
        signUpMember.setPassWord(logInForm.getPassword());

        memberService.addMember(signUpMember);
        log.info("memberId={}",signUpMember.getNickName());

        return new ResponseEntity<>(new SignUpStatusForm(true, "Registered"), HttpStatus.OK);
    }
}
