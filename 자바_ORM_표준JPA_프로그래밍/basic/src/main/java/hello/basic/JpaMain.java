package hello.basic;

import hello.basic.domain.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            Member member = new Member(); // 비영속 상태, 영속성 컨텍스트에 등록되지 않은 상태
            member.setId(1L);
            member.setName("helloA");
            
            // 1차 캐시에 저장됨, 영속 상태
            em.persist(member);

            // 1차 캐시에서 조회
            Member findMember = em.find(Member.class, 1L);
            System.out.println("findMember.id = " + findMember.getId());
            System.out.println("findMember.name = " + findMember.getName());

            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(5) // offset
                    .setMaxResults(5) // limit
                    .getResultList();

            findMember.setName("HelloJPA"); // Dirty Checking, flush() 호출 시 1차 캐시에 저장된 스냅샷과 비교, 변경을 감지하여 update 쿼리 생성

            // em.detach(findMember); // 준영속 상태 변경, flush() 호출해도 DB 반영 X

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();

    }
}
