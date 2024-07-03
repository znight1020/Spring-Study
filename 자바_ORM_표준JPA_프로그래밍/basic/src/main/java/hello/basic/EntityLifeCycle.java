package hello.basic;

import hello.basic.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class EntityLifeCycle {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            // 비영속 상태
            Member member = new Member();
            member.setId(100L);
            member.setName("memberA");

            // 영속 상태, 해당 코드는 DB에 저장하는 코드가 아니다.
            // -> 로그를 찍어보면 tx.commit()에서 insert 쿼리가 날라가는 것을 확인할 수 있다.
            em.persist(member);

            // 회원 엔티티를 영속성 컨텍스트에서 분리, 준영속 상태
            // em.detach(member);

            // 객체를 삭제한 상태
            // em.remove(member);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();

    }

}
