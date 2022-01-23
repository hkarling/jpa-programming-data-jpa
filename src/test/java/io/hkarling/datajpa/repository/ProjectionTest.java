package io.hkarling.datajpa.repository;

import static org.assertj.core.api.Assertions.assertThat;

import io.hkarling.datajpa.entity.Member;
import io.hkarling.datajpa.entity.Team;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback(value = false)
public class ProjectionTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;

    @Test
    public void projections() {
        // given
        Team teamA = new Team("teamA");
        em.persist(teamA);

        Member member1 = new Member("m1", 0, teamA);
        Member member2 = new Member("m2", 0, teamA);
        em.persist(member1);
        em.persist(member2);

        em.flush();
        em.clear();

        // when
        List<UsernameOnly> result = memberRepository.findProjectionsByUsername("m1", UsernameOnly.class);
        for (UsernameOnly usernameOnly : result) {
            System.out.println("usernameOnly = " + usernameOnly);
            System.out.println("usernameOnly = " + usernameOnly.getUsername());
        }

    }

    @Test
    public void projections2() {
        // given
        Team teamA = new Team("teamA");
        em.persist(teamA);

        Member member1 = new Member("m1", 0, teamA);
        Member member2 = new Member("m2", 0, teamA);
        em.persist(member1);
        em.persist(member2);

        em.flush();
        em.clear();

        // when
        List<UsernameOnlyDTO> result = memberRepository.findProjectionsByUsername("m1", UsernameOnlyDTO.class);
        for (UsernameOnlyDTO usernameOnly : result) {
            System.out.println("usernameOnly = " + usernameOnly);
            System.out.println("usernameOnly = " + usernameOnly.getUsername());
        }
    }

    @Test
    public void nestedClosedProjection() {
        // given
        Team teamA = new Team("teamA");
        em.persist(teamA);

        Member member1 = new Member("m1", 0, teamA);
        Member member2 = new Member("m2", 0, teamA);
        em.persist(member1);
        em.persist(member2);

        em.flush();
        em.clear();

        // when
        List<NestedClosedProjections> result = memberRepository.findProjectionsByUsername("m1", NestedClosedProjections.class);
        for (NestedClosedProjections nestedClosedProjections : result) {
            System.out.println("usernameOnly = " + nestedClosedProjections);
            System.out.println("usernameOnly = " + nestedClosedProjections.getUsername());
            System.out.println("nestedClosedProjections = " + nestedClosedProjections.getTeam().getName());
        }
    }
}
