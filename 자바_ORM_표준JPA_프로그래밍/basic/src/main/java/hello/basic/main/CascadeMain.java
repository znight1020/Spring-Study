package hello.basic.main;

import hello.basic.domain.cascade.Child;
import hello.basic.domain.cascade.Parent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class CascadeMain {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();
		tx.begin();

		try{
			Child child1 = new Child();
			Child child2 = new Child();

			Parent parent = new Parent();
			parent.addChild(child1);
			parent.addChild(child2);

			em.persist(parent);
			// em.persist(child1);
			// em.persist(child2);

			em.flush();
			em.clear();

			Parent findParent = em.find(Parent.class, parent.getId());
			findParent.getChildren().remove(0); // 부모 Entity 에서 자식 Entity를 삭제하면 고아 객체가 된다.

			// 이후 자동으로 delete 쿼리가 나가게 된다.

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
		} finally {
			em.close();
		}
		emf.close();
	}
}
