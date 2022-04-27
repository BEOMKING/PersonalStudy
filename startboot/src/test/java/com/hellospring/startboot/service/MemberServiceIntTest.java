package com.hellospring.startboot.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.hellospring.startboot.domain.Member;
import com.hellospring.startboot.repository.MemberRepository;
import com.hellospring.startboot.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberServiceIntTest {

    @Autowired
    MemberService memberService;

    @Test
    void join() {
        // given
        Member member = new Member();
        member.setName("spring");
        // when
        Long saveId = memberService.join(member);
        // then
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        Member member = new Member();
        member.setName("spring");
        Member member1 = new Member();
        member1.setName("spring");

        memberService.join(member);
        IllegalStateException illegalStateException = assertThrows(IllegalStateException.class,
            () -> memberService.join(member));

        Assertions.assertThat(illegalStateException.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}