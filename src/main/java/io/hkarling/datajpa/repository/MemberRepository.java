package io.hkarling.datajpa.repository;

import io.hkarling.datajpa.entity.Member;
import io.hkarling.datajpa.repository.dto.MemberDTO;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    List<Member> findTop3HelloBy();

    // Named Query 호출. 실무에서 거의 사용하지 않음.
//    @Query(name = "Member.findByUsername") // 메소드 명이 규칙에 적합하여 지워도 상관없다. Named Query 가 우선하긴 한다.
    List<Member> findByUsername(@Param("username") String username);

    @Query("select m from Member m where m.username = :username and m.age = :age") // 여기 에러는 어플리케이션 로딩 시점에 오류 확인가능하다.
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findByUsernameList();

    @Query("select new io.hkarling.datajpa.repository.dto.MemberDTO(m.id, m.username, t.name) from Member m join m.team t")
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

}
