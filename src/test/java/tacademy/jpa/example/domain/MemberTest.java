package tacademy.jpa.example.domain;

import static org.assertj.core.api.Assertions.assertThat;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemberTest {

    @Autowired
    MemberRepository members;

    @PersistenceUnit
    EntityManagerFactory emf;

    @Test
    void saveWithoutSpringFramework() {
        // 사실 Spring 위에서 돌아가지만... 그래도 최대한 비슷한 코드로

        // EntityManagerFactory emf = Persistence.createEntityManagerFactory("~~");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member expected = new Member();
            expected.setName("test");
            expected.setMemberType(MemberType.USER);

            em.persist(expected);
            tx.commit();

            Member actual = em.find(Member.class, expected.getId());
            assertThat(actual).isEqualTo(expected);
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

    @Test
    void save() {
        Member expected = new Member();
        expected.setName("test");
        expected.setMemberType(MemberType.USER);

        Member actual = members.save(expected);

        assertThat(actual).isEqualTo(expected);
    }
}