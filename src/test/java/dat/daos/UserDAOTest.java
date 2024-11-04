package dat.daos;

import dat.config.HibernateConfig;
import dat.security.daos.SecurityDAO;
import dat.security.entities.User;
import dat.security.exceptions.ValidationException;
import dk.bugelhartmann.UserDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.testcontainers.shaded.org.hamcrest.Matchers.hasItem;

class UserDAOTest {

    private static EntityManagerFactory emf;
    private static SecurityDAO securityDAO;

    @BeforeAll
    static void setUpAll() {
        // Set test to true and get the entity manager factory
        HibernateConfig.setTest(true);
        emf = HibernateConfig.getEntityManagerFactory();
        securityDAO = new SecurityDAO(emf);

        // Start the server
        //app = ApplicationConfig.startServer(7070);
    }

    @AfterEach
    void tearDown() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM LentBook").executeUpdate();
            em.createQuery("DELETE FROM Book").executeUpdate();
            em.createQuery("DELETE FROM User").executeUpdate();
            em.createQuery("DELETE FROM Role").executeUpdate();
            em.getTransaction().commit();
        }
    }

    @Test
    void createUser() {
        User user = securityDAO.createUser("testuser", "testpassword");
        assertNotNull(user);
    }

    @Test
    void getVerifiedUser() {
        securityDAO.createUser("testuser", "testpassword");
        UserDTO readUser;
        try {
            readUser = securityDAO.getVerifiedUser("testuser", "testpassword");
        } catch (ValidationException e) {
            throw new RuntimeException(e);
        }
        assertNotNull(readUser);
        assertEquals("testuser", readUser.getUsername());
    }

    /*@Test
    void addRole() {
        User user = securityDAO.createUser("testuser", "testpassword");
        User userAdmin = securityDAO.addRole(new UserDTO(user.getUsername(), user.getPassword()), "ADMIN");
        assertThat(userAdmin.getRolesAsStrings(), hasItem("ADMIN"));
    }*/
}