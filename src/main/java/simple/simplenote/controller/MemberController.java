package simple.simplenote.controller;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import simple.simplenote.domain.Member;
import simple.simplenote.controller.form.AddUserForm;
import simple.simplenote.controller.form.StatusForm;
import simple.simplenote.service.MemberService;


@Controller
@RequestMapping("api/members")
@RequiredArgsConstructor
@Transactional(readOnly = true)
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MemberController {

    private final ObjectMapper objectMapper;
    private final MemberService memberService;


    @PostMapping("")
    @ResponseBody
    @Transactional(readOnly = false)
    public String addMember(@RequestBody AddUserForm addUserForm) throws JsonProcessingException {

        System.out.println("MemberController.addMember");
        Member member = new Member();
        member.setNickName(addUserForm.getNickname());

        memberService.addMember(member);

        return objectMapper.writeValueAsString(new StatusForm("Good Received"));
    }
}
