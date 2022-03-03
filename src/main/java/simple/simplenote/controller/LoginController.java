package simple.simplenote.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import simple.simplenote.encode.RSA;

import java.util.HashMap;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
@Transactional(readOnly = true)
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class LoginController {


    @ResponseBody
    @GetMapping("/public-key")
    public String createKey() throws JsonProcessingException {

        RSA.createKeypairAsString();

        StringBuilder sb = new StringBuilder();
        sb.append("{\"PUBLIC_KEY\":").append("\""+RSA.rsaKeyPair.get("publicKey")+"\"}");
        return sb.toString();
    }
}
