package hello.basic.main;

import hello.basic.domain.member.Address;
import hello.basic.domain.member.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class CollectionMain {
  public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
    EntityManager em = emf.createEntityManager();

    EntityTransaction tx = em.getTransaction();
    tx.begin();

    try{
      Address address = new Address("home", "street", "10000");
      Address address1 = new Address("old1", "street", "10000");
      Address address2 = new Address("old2", "street", "10000");

      Member member = new Member();
      member.setName("member1");
      member.setHomeAddress(address);

      member.getFavoriteFoods().add("치킨");
      member.getFavoriteFoods().add("족발");
      member.getFavoriteFoods().add("피자");

      member.getAddressHistory().add(address1);
      member.getAddressHistory().add(address2);

      em.persist(member);

      em.flush();
      em.clear();

      System.out.println("==========START==========");
      Member findMember = em.find(Member.class, member.getId());

      // 컬렉션 타입은 지연 로딩으로 실제 사용 시점에 join 된다.
      System.out.println("==========FOOD_JOIN==========");
      for(String foodName : findMember.getFavoriteFoods()){
        System.out.println(foodName);
      }

      System.out.println("==========ADDRESS_HISTORY_JOIN==========");
      for(Address a : findMember.getAddressHistory()){
        System.out.println(a.getCity());
      }

      tx.commit();
    } catch (Exception e) {
      tx.rollback();
    } finally {
      em.close();
    }
    emf.close();
  }
}
