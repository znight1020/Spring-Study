package hello.basic.main;

import hello.basic.domain.member.Member;
import hello.basic.domain.member.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class ProxyMain {
  public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
    EntityManager em = emf.createEntityManager();

    EntityTransaction tx = em.getTransaction();
    tx.begin();

    try{
      Member member1 = new Member();
      member1.setName("helloA");

      Member member2 = new Member();
      member2.setName("helloB");

      em.persist(member1);
      em.persist(member2);
      em.flush();
      em.clear();

//      Member findMember = em.find(Member.class, 1L);
//      System.out.println("findMember = " + findMember.getClass());

      Member findProxyMember = em.getReference(Member.class, member1.getId());
      System.out.println("findProxyMember = " + findProxyMember.getClass());
      em.detach(findProxyMember);
      System.out.println("findProxyMember.getName() = " + findProxyMember.getName());
      System.out.println("findProxyMember = " + findProxyMember.getClass()); // 여전히 Proxy 객체이다.

      Member findMember = em.find(Member.class, member2.getId());
      System.out.println("findMember = " + findMember.getClass());
      System.out.println("m1 == m2 (==) : " + (findMember.getClass() == findProxyMember.getClass())); // false

      boolean b1 = findMember instanceof Member;
      boolean b2 = findProxyMember instanceof Member;
      System.out.println("m1 == m2 (instance) : " + (b1==b2)); // true

      tx.commit();
    } catch (Exception e) {
      System.out.println("예외 발생" + e.getMessage());
      tx.rollback();
    } finally {
      em.close();
    }

    emf.close();

  }
}
