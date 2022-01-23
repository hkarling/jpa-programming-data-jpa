package io.hkarling.datajpa.repository;

public interface NestedClosedProjections {

    String getUsername();
    TeamInfo getTeam();

    interface TeamInfo {
        String getName();
    }

    // inner interface 는 최적화가 안된다. left outer join 으로 연결
}
