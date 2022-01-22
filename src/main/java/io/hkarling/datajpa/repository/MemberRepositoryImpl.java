package io.hkarling.datajpa.repository;

import io.hkarling.datajpa.entity.Member;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {
    // 명명규칙: MemberRepository + Impl, custom 인터페이스의 명칭은 아무렇게나 해도 된다.
    // 스프링 데이터 2.x 부터는 커스텀 인터페이스명 + Impl 로도 동작은 한다.

    private final EntityManager em;

    @Override
    public List<Member> findMemberCustom() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }
}
