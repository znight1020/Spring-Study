package study.jpql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.util.List;
import study.jpql.domain.dto.MemberDTO;
import study.jpql.domain.member.Member;
import study.jpql.domain.member.Team;

public class JpqlMain {
  public static void main(String[] args) {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
    EntityManager em = emf.createEntityManager();

    EntityTransaction tx = em.getTransaction();
    tx.begin();

    try{
      Team team = new Team();
      team.setName("A");
      em.persist(team);

      Member member = new Member();
      member.setUsername("member1");
      member.setAge(10);
      member.changeTeam(team);

      em.persist(member);

      em.flush();
      em.clear();

      System.out.println("==========조회 API==========");
      TypeVariables(em);

      System.out.println("==========파라미터 바인딩==========");
      parameterBinding(em);

      System.out.println("==========여러 반환 값==========");
      manyReturnValues(em);

      System.out.println("==========페이징==========");
      paging(em);

      System.out.println("==========조인==========");
      join(em);

      System.out.println("==========서브 쿼리==========");
      subQueries(em);

      tx.commit();
    } catch (Exception e) {
      System.out.println("[ERROR] : " + e.getMessage());
      tx.rollback();
    } finally {
      em.close();
    }
    emf.close();
  }

  private static void TypeVariables(EntityManager em) {
    TypedQuery<Member> findMembers = em.createQuery("select m from Member m", Member.class);
    List<Member> resultList = findMembers.getResultList();

    // TypedQuery<Member> findMember = em.createQuery("select m from Member m where id=10", Member.class);
    // Member singleResult = findMember.getSingleResult(); // No result Exception
    // Spring Data JPA, NoResultException 에 대한 try-catch {return null;} 되어있다.
  }

  private static void manyReturnValues(EntityManager em) {
    List<MemberDTO> toDTOs = em.createQuery(
        "select new study.jpql.domain.dto.MemberDTO(m.username, m.age) from Member m",
        MemberDTO.class
    ).getResultList();
  }

  private static void parameterBinding(EntityManager em) {
    Member nameQuery = em.createQuery("select m from Member m where username=:username", Member.class)
        .setParameter("username", "member1")
        .getSingleResult();

    Member indexQuery = em.createQuery("select m from Member m where username=?1", Member.class)
        .setParameter(1, "member1")
        .getSingleResult();
  }


  private static void paging(EntityManager em) {
    for(int i = 1; i < 100; i++) {
      Member member = new Member();
      member.setUsername("member" + (i+1));
      member.setAge(i);
      em.persist(member);
    }

    em.flush();
    em.clear();

    // 10개의 content 조회
    List<Member> result = em.createQuery("select m from Member m order by m.age", Member.class)
        .setFirstResult(0)
        .setMaxResults(10)
        .getResultList();

    for(Member m : result) System.out.println("m = " + m.getUsername());
  }

  private static void join(EntityManager em) {
    // Inner Join, inner 생략 가능
    List<Team> inner = em.createQuery("select t from Member m inner join m.team t", Team.class).getResultList();

    // Outer Join, outer 생략 가능
    List<Team> outer = em.createQuery("select t from Member m left outer join m.team t", Team.class).getResultList();

    // theta Join
    List<Team> theta = em.createQuery("select t from Member m, Team t where m.team.name = t.name", Team.class).getResultList();

    // ON Join (연관관계 O)
    em.createQuery("select m, t from Member m left join m.team t on t.name =:teamName")
        .setParameter("teamName", "A")
        .getResultList();

    // ON Join (연관관계 X)
    em.createQuery("select m, t from Member m left join Team t on m.team.name = t.name").getResultList();

    for(Team t : theta) {
      System.out.println("t.getName() = " + t.getName());
    }
  }

  private static void subQueries(EntityManager em) {
    // select
    double result1 = em.createQuery("select distinct (select avg(m1.age) from Member m1) as avgAge from Member m", Double.class).getSingleResult();

    // from
    double result2 = em.createQuery("select avg(m.mAge) from (select mm.username as mUsername, mm.age as mAge from Member mm) m", Double.class).getSingleResult();

    // where
    double result3 = em.createQuery("select avg(m.age) from Member m where m.age in (select mm.age from Member mm)", Double.class).getSingleResult();

    System.out.println("평균 나이: " + result1);
  }
}
