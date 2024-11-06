package datajpaptc.forPractice.repository;

import datajpaptc.forPractice.domain.Member;
import datajpaptc.forPractice.domain.Team;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TeamJpaRepository {
    private final EntityManager em;


    public Team save(Team team){
        em.persist(team);
        return team;
    }

    public Optional<Team> findById(Long id)
    {
        return Optional.ofNullable(em.find(Team.class, id));
    }

    public long count(){
        return em.createQuery("select count(t) from Team t", Long.class).getSingleResult();
    }

    public List<Team> findAll(){
        return em.createQuery("select t from Team t", Team.class).getResultList();
    }

}
