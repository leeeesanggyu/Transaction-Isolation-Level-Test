package com.salgu.transaction.member;

import com.salgu.transaction.member.entity.Member;
import com.salgu.transaction.member.repository.MemberRepository;
import com.salgu.transaction.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class MemberTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;

    static private String name = "이상규";
    static private String newName = "김형준";

    private Member makeMember() {
        return Member.builder()
                .name(name)
                .build();
    }

    @Test
    void saveMember() {
        final Member member = makeMember();
        Member saveMember = memberRepository.save(member);
        assertThat(saveMember.getName()).isEqualTo(member.getName());

        Member findMember = memberRepository.findById(saveMember.getId())
                        .orElseThrow(IllegalAccessError::new);
        assertThat(findMember.getName()).isEqualTo(member.getName());
    }

    @Test
    void findByNameMember() {
        final Member member = makeMember();
        Member saveMember = memberRepository.save(member);
        assertThat(saveMember.getName()).isEqualTo(member.getName());

        Member findMember = memberRepository.findByName(saveMember.getName());
        assertThat(findMember.getName()).isEqualTo(member.getName());
    }

    @Test
    @Transactional
    void changeName() {
        final Member member = makeMember();
        Member saveMember = memberRepository.save(member);
        assertThat(saveMember.getName()).isEqualTo(member.getName());

        Member changeNameResult = memberService.changeName(saveMember.getId(), newName);
        assertThat(changeNameResult.getName()).isEqualTo(newName);

        Member findChangedNameMember = memberRepository.findById(saveMember.getId())
                .orElseThrow(IllegalAccessError::new);
        assertThat(findChangedNameMember.getName()).isEqualTo(newName);
    }
}
