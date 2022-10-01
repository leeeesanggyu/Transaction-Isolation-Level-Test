package com.salgu.transaction.transaction;

import com.salgu.transaction.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TransactionTest {

    @Autowired
    MemberService memberService;


}
