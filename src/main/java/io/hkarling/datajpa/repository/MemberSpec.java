package io.hkarling.datajpa.repository;

import io.hkarling.datajpa.entity.Member;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class MemberSpec {

    public static Specification<Member> teamName(final String teamName) {
        return (root, query, criteriaBuilder) -> {
            if(StringUtils.isEmpty(teamName)) {
                return null;
            }
            Join<Object, Object> team = root.join("team", JoinType.INNER);
            return criteriaBuilder.equal(team.get("name"), teamName);
        };
    }

    public static Specification<Member> username(final String username) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("username"), username);
    }
}
