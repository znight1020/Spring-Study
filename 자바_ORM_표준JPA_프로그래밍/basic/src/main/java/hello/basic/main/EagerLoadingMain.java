package hello.basic.main;

import java.util.List;

import hello.basic.domain.member.Member;
import hello.basic.domain.member.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class EagerLoadingMain {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();
		tx.begin();

		try{
			Team teamA = new Team();
			teamA.setName("TeamA");
			em.persist(teamA);

			Team teamB = new Team();
			teamB.setName("TeamA");
			em.persist(teamB);

			Member member1 = new Member();
			member1.setName("member1");
			member1.setTeam(teamA);
			em.persist(member1);

			Member member2 = new Member();
			member2.setName("member2");
			member2.setTeam(teamB);
			em.persist(member2);

			em.flush();
			em.clear();

			// Member member = em.find(Member.class, member1.getId());

			List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList(); // N+1 문제 발생
			for(Member m : members) System.out.println("m.getName() = " + m.getName());

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}

		emf.close();

	}
}
