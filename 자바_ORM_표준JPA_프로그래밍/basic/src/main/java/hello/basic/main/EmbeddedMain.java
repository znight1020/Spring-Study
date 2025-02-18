package hello.basic.main;

import hello.basic.domain.member.Address;
import hello.basic.domain.member.Member;
import hello.basic.domain.member.Period;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class EmbeddedMain {
  public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
    EntityManager em = emf.createEntityManager();

    EntityTransaction tx = em.getTransaction();
    tx.begin();

    try{
      Address address = new Address("city", "street", "10000");

      Member member1 = new Member();
      member1.setName("Hello");
      member1.setHomeAddress(address);
      member1.setWorkPeriod(new Period());

      Address copyAddress = new Address(address.getCity(), address.getStreet(), address.getZipcode());

      Member member2 = new Member();
      member2.setName("Hello");
      member2.setHomeAddress(copyAddress);
      member2.setWorkPeriod(new Period());

      em.persist(member1);

      member1.getHomeAddress().setCity("newCity");

      tx.commit();
    } catch (Exception e) {
      tx.rollback();
    } finally {
      em.close();
    }
    emf.close();
  }
}
