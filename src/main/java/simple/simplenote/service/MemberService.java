package simple.simplenote.service;



import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simple.simplenote.domain.Member;
import simple.simplenote.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = false)
    public String addMember(Member member){
        validateDuplicatedMember(member);
        memberRepository.save(member);

        return member.getNickName();
    }

    public Member findByName(String name){
        return memberRepository.find(name);
    }

    private void validateDuplicatedMember(Member member) {
        Member findMember = memberRepository.find(member.getNickName());

        if (findMember != null){
            throw new IllegalStateException("duplicated NickName!");
        }
    }
}
