package io.hkarling.datajpa.repository;

import io.hkarling.datajpa.entity.Member;
import io.hkarling.datajpa.dto.MemberDTO;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    List<Member> findTop3HelloBy();

    // Named Query 호출. 실무에서 거의 사용하지 않음.
//    @Query(name = "Member.findByUsername") // 메소드 명이 규칙에 적합하여 지워도 상관없다. Named Query 가 우선하긴 한다.
    List<Member> findByUsername(@Param("username") String username);

    @Query("select m from Member m where m.username = :username and m.age = :age")
        // 여기 에러는 어플리케이션 로딩 시점에 오류 확인가능하다.
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findByUsernameList();

    @Query("select new io.hkarling.datajpa.dto.MemberDTO(m.id, m.username, t.name) from Member m join m.team t")
    List<MemberDTO> findMemberDTO();

    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

    // 다양한 반환타입 지원
    List<Member> findListByUsername(String username);

    Member findMemberByUsername(String username);

    Optional<Member> findOptionalByUsername(String username);

    // 페이징
    // @Query(value = "select m from Member m left join m.team t",
    //    countQuery = "select count(m.username) from Member m") // total query 의 최적화
    @Query(value = "select m from Member m")
    Page<Member> findByAge(int age, Pageable pageable); // page query + total count query
    // Slice<Member> findByAge(int age, Pageable pageable); // total count query 가 나가지 않는다. limit+1 개를 불러온다.
    // Slice<Member> findByAge(int age, Pageable pageable);

    @Modifying(clearAutomatically = true)
    // 없으면 InvalidDataAccessApiUsageException 뜬다.  clearAutomatically : 자동으로 flush
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    @Override
    @EntityGraph(attributePaths = {"team"}) // fetch join 역할 JPA 표준스펙 2.2
    List<Member> findAll();

    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findMemberEntityGraph();

//    @EntityGraph(attributePaths = {"team"})
    @EntityGraph("Member.all") // Entity 에 선언된 NamedEntityGraph 설정을 호출. 잘 안씀.
    List<Member> findEntityGraphByUsername(@Param("username") String username);

    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUsername(String username);

    @Lock(LockModeType.PESSIMISTIC_WRITE) // JPA가 제공하는 LOCK을 사용가능
    List<Member> findLockByUsername(String username);
}
