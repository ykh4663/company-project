package datajpaptc.forPractice.Controller;

import datajpaptc.forPractice.domain.Member;
import datajpaptc.forPractice.dto.MemberDto;
import datajpaptc.forPractice.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id){
        Member findMember = memberRepository.findById(id).get();
        return findMember.getUsername();
    }

    @GetMapping("/members2/{id}")
    public String findMember(@PathVariable("id") Member member){
//도메인 클래스 컨버터는 member객체를 단순 조회용으로만 사용해야지 update를 쳐버리면 안된다
        return member.getUsername();
    }

    @GetMapping("/members")
    public Page<MemberDto> list(Pageable pageable){
        Page<Member> page = memberRepository.findAll(pageable);
        //postman에서 사용법  : http://localhost:8080/members?page=0&size=3&sort=id,desc&sort=username,desc
        //전역적으로 선언한 페이지 값 말고 지역적으로 값 결정하고 싶은 경우 @PageableDefault사용하기
        return page.map(MemberDto::new);
    }


    @PostConstruct
    public void init(){
        for (int i = 0; i < 100; i++) {
            memberRepository.save(new Member("user"+i, i, null));
        }
    }




}
