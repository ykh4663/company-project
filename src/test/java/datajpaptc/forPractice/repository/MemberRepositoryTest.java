package datajpaptc.forPractice.repository;

import datajpaptc.forPractice.domain.Member;
import datajpaptc.forPractice.domain.Team;
import datajpaptc.forPractice.dto.MemberDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberRepositoryTest {
    @Autowired TeamRepository teamRepository;
    @Autowired MemberRepository memberRepository;
    @PersistenceContext
    EntityManager em;
    @Test
    void testQuery(){
        Member member1 = new Member("AAA", 10, null);
        Member member2 = new Member("BBB", 20, null);
        memberRepository.save(member1);
        memberRepository.save(member2);
        List<Member> findMember = memberRepository.findUser("AAA", 10);


        assertThat(findMember.get(0)).isEqualTo(member1);

    }
    @Test
    void dto_Test(){
        Team team = new Team("myTeam");
        teamRepository.save(team);

        Member member = new Member("memberA", 20, team);
        memberRepository.save(member);

        List<MemberDto> memberDto = memberRepository.findMemberDto();
        for (MemberDto dto : memberDto) {
            System.out.println("dto.getTeamName() = " + dto.getTeamName());

        }

    }

    @Test
    void findByStringList(){
        Member member1 = new Member("AAA", 10, null);
        Member member2 = new Member("BBB", 20, null);

        memberRepository.save(member1);
        memberRepository.save(member2);

        List<Member> findMembers = memberRepository.findByStringList(Arrays.asList("AAA", "BBB"));
        for (Member findMember : findMembers) {
            System.out.println("findMember = " + findMember);

        }

    }
    @Test
    void paging(){

        memberRepository.save(new Member("AAA", 10, null));
        memberRepository.save(new Member("BBB", 10, null));
        memberRepository.save(new Member("CCC", 10, null));
        memberRepository.save(new Member("DDD", 10, null));
        memberRepository.save(new Member("EEE", 10, null));

        int age = 10;
        int offset = 0;
        int limit = 3;
        PageRequest pageRequest = PageRequest.of(offset,limit, Sort.by(Sort.Direction.ASC, "username"));

        Page<Member> page = memberRepository.findPageByAge(age, pageRequest);

        List<Member> content = page.getContent();
        for (Member findMember : content) {
            System.out.println("findMember = " + findMember);

        }
        long totalElements = page.getTotalElements();
        System.out.println("totalElements = " + totalElements);

        assertThat(content.size()).isEqualTo(3);
        assertThat(page.getTotalElements()).isEqualTo(5);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getTotalPages()).isEqualTo(2);
        assertThat(page.isFirst()).isTrue();
        assertThat(page.hasNext()).isTrue();




    }
    @Test
    void slicing(){

        memberRepository.save(new Member("AAA", 10, null));
        memberRepository.save(new Member("BBB", 10, null));
        memberRepository.save(new Member("CCC", 10, null));
        memberRepository.save(new Member("DDD", 10, null));
        memberRepository.save(new Member("EEE", 10, null));

        int age = 10;
        int offset = 0;
        int limit = 3;
        PageRequest pageRequest = PageRequest.of(offset,limit, Sort.by(Sort.Direction.ASC, "username"));

        Slice<Member> page = memberRepository.findSliceByAge(age, pageRequest);

        List<Member> content = page.getContent();
        for (Member findMember : content) {
            System.out.println("findMember = " + findMember);

        }
        //long totalElements = page.getTotalElements();
        //System.out.println("totalElements = " + totalElements);

        assertThat(content.size()).isEqualTo(3);
        //assertThat(page.getTotalElements()).isEqualTo(5);
        assertThat(page.getNumber()).isEqualTo(0);
       // assertThat(page.getTotalPages()).isEqualTo(2);
        assertThat(page.isFirst()).isTrue();
        assertThat(page.hasNext()).isTrue();




    }
    @Test
    void paging2(){

        memberRepository.save(new Member("AAA", 10, null));
        memberRepository.save(new Member("BBB", 10, null));
        memberRepository.save(new Member("CCC", 10, null));
        memberRepository.save(new Member("DDD", 10, null));
        memberRepository.save(new Member("EEE", 10, null));

        int age = 10;
        int offset = 0;
        int limit = 3;
        PageRequest pageRequest = PageRequest.of(offset,limit, Sort.by(Sort.Direction.ASC, "username"));

        Page<Member> page = memberRepository.findByAge(age, pageRequest);

        List<Member> content = page.getContent();
        for (Member findMember : content) {
            System.out.println("findMember = " + findMember);

        }
        long totalElements = page.getTotalElements();
        System.out.println("totalElements = " + totalElements);

        assertThat(content.size()).isEqualTo(3);
        assertThat(page.getTotalElements()).isEqualTo(5);
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getTotalPages()).isEqualTo(2);
        assertThat(page.isFirst()).isTrue();
        assertThat(page.hasNext()).isTrue();




    }
    @Test
    void bulkAgePlus(){
        memberRepository.save(new Member("AAA", 10, null));
        memberRepository.save(new Member("BBB", 15, null));
        memberRepository.save(new Member("CCC", 20, null));
        memberRepository.save(new Member("DDD", 25, null));
        memberRepository.save(new Member("EEE", 30, null));
        int count = memberRepository.bulkAgePlus(18);

        //em.clear();

        List<Member> result = memberRepository.findByUsername("EEE");
        Member findMember = result.get(0);
        assertThat(count).isEqualTo(3);
        assertThat(findMember.getAge()).isEqualTo(31);

    }

    @Test
    void findAllFetch(){
        Team team1 = new Team("teamA");
        Team team2 = new Team("teamB");
        teamRepository.save(team1);
        teamRepository.save(team2);

        memberRepository.save(new Member("AAA", 10, team1));
        memberRepository.save(new Member("BBB", 15, team2));

        em.flush();
        em.clear();

        List<Member> all = memberRepository.findAll();
        for (Member member : all) {
            System.out.println("member.getTeam().getClass() = " + member.getTeam().getClass());
            System.out.println("member.getTeam().getName() = " + member.getTeam().getName());
        }


    }
    @Test
    void JpaEventBaseEntity() throws InterruptedException {
        Member member = new Member("member1", 10, null);
        memberRepository.save(member);//prePersist됨
        //save가 실행되면 member객체는 엔티티 매니저에 의해 관리되므로 변경감지를 사용할 수 있게 된다

        Thread.sleep(100);
        //Member findMember = memberRepository.findById(member.getId()).get();
        //findMember.setUsername("member2");
        member.setUsername("member2");

        em.flush(); //PreUpdate됨
        em.clear();

        //Member member1 = memberRepository.findById(findMember.getId()).get();
        Member member1 = memberRepository.findById(member.getId()).get();

        System.out.println("member1.getCreatedDate() = " + member1.getCreatedDate());
        System.out.println("member1.getUpdatedDate() = " + member1.getLastModifiedDate());
        System.out.println("member1.getCreatedBy() = " + member1.getCreatedBy());
        System.out.println("member1 = " + member1.getLastModifiedBy());


    }





}