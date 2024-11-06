package datajpaptc.forPractice.repository;

import datajpaptc.forPractice.domain.Member;
import datajpaptc.forPractice.dto.MemberDto;
import jakarta.persistence.Entity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>,MemberRepositoryCustom {
    List<Member>findByUsername(String username);

//    @Query("select m from Member m where m.username =:username and m.age =:age")
//    List<Member>findUser(@Param("username") String username, @Param("age")int age);
    @Query("select m from Member m where m.username =:username and m.age =:age")
    List<Member>findUser(@Param("username") String username ,@Param("age") int age);

    @Query("select m.username from Member m")
    List<String>findUsernameList();

    @Query("select new datajpaptc.forPractice.dto.MemberDto(m) from Member m join m.team")
    List<MemberDto>findMemberDto();

    @Query("select m from Member m where m.username in :names")
    List<Member>findByNames(@Param("names") Collection<String> names);
    @Query("select m from Member m where m.username in :names")
    List<Member>findByStringList(Collection<String>names);


    Page<Member>findPageByAge(int age, Pageable pageable);

    Slice<Member> findSliceByAge(int age, Pageable pageable);

    @Query(value = "select m from Member m left join m.team t where m.age =:age",
            countQuery = "select count(m) from Member m where m.age =:age")
    Page<Member>findByAge(int age, Pageable pageable);

    @Modifying(clearAutomatically = true)//이것이 있으면은 executeUpdate처럼 동작한다
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age")int age);

    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member>findAll();

    @EntityGraph(attributePaths = {"team"})
    List<Member>findEntityByUsername(@Param("username") String username);

}
