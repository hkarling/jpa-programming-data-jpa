package io.hkarling.datajpa.repository;

import io.hkarling.datajpa.entity.Member;
import java.util.List;

public interface MemberRepositoryCustom {

    List<Member> findMemberCustom();
}
