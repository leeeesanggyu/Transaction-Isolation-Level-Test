package com.salgu.transaction.member.service;

import com.salgu.transaction.member.entity.Member;
import com.salgu.transaction.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import static com.salgu.transaction.utils.ThreadService.threadSleep;

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

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public Member findByMemberId(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(IllegalAccessError::new);
    }

    @Transactional
    public Member changeName(Long id, String newName) {
        Member member = memberRepository.findById(id)
                        .orElseThrow(IllegalAccessError::new);
        member.setName(newName);
        memberRepository.flush();
        return member;
    }

    /**
     * 이름을 변경하는데 n초가 걸리고
     * 변경되는 과정에선 temp 란 임시 이름으로 존재합니다.
     */
    @Transactional
    public Member changeWaitName(Long id, String newName) {
        Member member = memberRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        member.setName("temp");
        memberRepository.flush();

        threadSleep(1000);

        member.setName(newName);
        memberRepository.flush();
        return member;
    }
}
