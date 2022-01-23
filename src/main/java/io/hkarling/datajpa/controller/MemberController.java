package io.hkarling.datajpa.controller;

import io.hkarling.datajpa.dto.MemberDTO;
import io.hkarling.datajpa.entity.Member;
import io.hkarling.datajpa.repository.MemberRepository;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/v1/members/{id}")
    public String findMember(@PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    // 도메인 클래스 컨버터 : 조회용. 잘 쓸일은 크게 없음.
    @GetMapping("/v2/members/{id}")
    public String findMember2(@PathVariable("id") Member member) {
        return member.getUsername();
    }

    @GetMapping("/v1/members")
    public Page<Member> list(@PageableDefault(size = 5) Pageable pageable) {
        Page<Member> page = memberRepository.findAll(pageable);
        return page;
    }

    @GetMapping("/v2/members")
    public Page<MemberDTO> list2(@PageableDefault(size = 5) Pageable pageable) {
//        Page<Member> page = memberRepository.findAll(pageable);
//        return page.map(member -> new MemberDTO(member.getId(), member.getUsername(), null));
        return memberRepository.findAll(pageable).map(MemberDTO::new);
    }

    @GetMapping("/v3/members")
    public Page<MemberDTO> list3(@PageableDefault(size = 5) Pageable pageable) {
//        PageRequest request = PageRequest.of(1, 2);
        return memberRepository.findAll(pageable).map(MemberDTO::new);
    }

//    @PostConstruct
    public void init() {
        for (int i = 0; i < 100; i++) {
            memberRepository.save(new Member("member" + i, 10+i));
        }
    }
}

