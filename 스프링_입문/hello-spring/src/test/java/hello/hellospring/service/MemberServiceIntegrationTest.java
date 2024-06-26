package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {
    @Autowired MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void join() {
        Member member = new Member();
        member.setName("spring");

        long start = System.currentTimeMillis();

        try{
            Long saveId = memberService.join(member);
            Member findMember = memberService.findOne(saveId).get();
            assertThat(member.getName()).isEqualTo(findMember.getName());
        } finally {
            long end = System.currentTimeMillis();
            System.out.println(end - start + "ms");
        }
    }

    @Test
    public void duplicateMemberException(){
        // given
        Member member1 = new Member();
        member1.setName("spring1");

        Member member2 = new Member();
        member2.setName("spring1");

        // when
        memberService.join(member1);

        // 메시지는 반환해서 받을 수 있음
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2)); // IllegalStateException이 터지면 성공
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}