package com.salgu.transaction.member.service;

import com.salgu.transaction.member.entity.Member;
import com.salgu.transaction.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

//    @Transactional
    public Member saveMember(String name) {
        return memberRepository.save(Member.builder()
                .name(name)
                .build());
    }

    public Member findMember(String name) {
        return memberRepository.findByName(name);
    }

    @Transactional
    public Member changeName(Long id, String newName) {
        Member member = memberRepository.findById(id)
                        .orElseThrow(IllegalAccessError::new);
        member.setName(newName);
        memberRepository.flush();
        return member;
    }
}
