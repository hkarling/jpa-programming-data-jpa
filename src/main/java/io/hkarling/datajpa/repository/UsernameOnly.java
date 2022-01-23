package io.hkarling.datajpa.repository;

import org.springframework.beans.factory.annotation.Value;

public interface UsernameOnly {

    /**
     * Close Projections : 단순 메서드로만 호출
     * Open Projections : SpEL 추가 시 Select 최적화 불가
     */
    @Value("#{target.username + ' ' + target.age}")
    String getUsername();
}
