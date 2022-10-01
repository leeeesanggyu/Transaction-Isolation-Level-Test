package com.salgu.transaction.transaction;

import com.salgu.transaction.member.entity.Member;
import com.salgu.transaction.member.repository.MemberRepository;
import com.salgu.transaction.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.salgu.transaction.utils.ThreadService.threadSleep;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class TransactionTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    static private String name = "이상규";
    static private String newName = "김형준";

    private Member makeMember() {
        return Member.builder()
                .name(name)
                .build();
    }

    @Test
    @DisplayName("READ UNCOMMITTED 테스트")
    void changeNameWaitDirtyReadTest() throws ExecutionException, InterruptedException {
        final Member member = memberService.saveMember(name);
        assertThat(member.getName()).isEqualTo(name);

        CompletableFuture<Void> 이름변경 = CompletableFuture.runAsync(() -> {
            memberService.changeWaitName(member.getId(), newName);
        });

        CompletableFuture<Member> 중간조회 = CompletableFuture.supplyAsync(()-> {
            threadSleep(500);
            return memberService.findByMemberId(member.getId());
        });
        Member result = 중간조회.get();
        assertThat(result.getName()).isEqualTo("temp");

        CompletableFuture.allOf(이름변경, 중간조회).join();
    }
}
