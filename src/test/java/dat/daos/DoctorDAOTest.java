/*
package dat.daos;


import dat.config.HibernateConfig;

import dat.dtos.TripDTO;
import dat.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TripDAOTest {

    private static EntityManagerFactory emf;
    private static TripDAO tripDAO;

    @BeforeAll
    static void setUpAll() {
        // Set test to true and get the entity manager factory
        HibernateConfig.setTest(true);
        emf = HibernateConfig.getEntityManagerFactory();
        tripDAO = TripDAO.getInstance(emf);

    }

    @AfterEach
    void tearDown() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Guide ").executeUpdate();
            em.createQuery("DELETE FROM Trip ").executeUpdate();
            em.createQuery("DELETE FROM User").executeUpdate();
            em.createQuery("DELETE FROM Role").executeUpdate();
            em.getTransaction().commit();
        }
    }

    @Test
    void read() throws ApiException {
        TripDTO tripDTO = tripDAO.create();
        TripDTO readTrip = tripDAO.read(tripDTO.getId());
        assertNotNull(readTrip);
        assertEquals("Dr. Alice Smith", readTrip.getName());
    }

    @Test
    void readAll() throws ApiException {
        doctorDAO.create(new DoctorDTO("Dr. Bob Johnson", LocalDate.of(1975, 3, 10), 2000, "Downtown Medical Center", Doctor.Speciality.SURGERY));
        doctorDAO.create(new DoctorDTO("Dr. Clara Lee", LocalDate.of(1983, 7, 22), 2008, "Green Valley Hospital", Doctor.Speciality.PEDIATRICS));
        List<DoctorDTO> doctors = doctorDAO.readAll();
        assertFalse(doctors.isEmpty());
        assertTrue(doctors.size() >= 2);
    }

    @Test
    void create() throws ApiException {
        DoctorDTO doctorDTO = new DoctorDTO("Dr. Owen James", LocalDate.of(1940, 7, 22), 1970, "Green Valley Hospital", Doctor.Speciality.PSYCHIATRY);
        DoctorDTO createdDoctor = doctorDAO.create(doctorDTO);
        assertNotNull(createdDoctor);
        assertEquals("Dr. Owen James", createdDoctor.getName());
    }

    @Test
    void update() throws ApiException {
        DoctorDTO doctorDTO = doctorDAO.create(new DoctorDTO("Dr. Owen James", LocalDate.of(1940, 7, 22), 1970, "Green Valley Hospital", Doctor.Speciality.PSYCHIATRY));
        doctorDTO.setName("Dr. Von Hausenberg");
        DoctorDTO updatedDoctor = doctorDAO.update(doctorDTO.getId(), doctorDTO);
        assertNotNull(updatedDoctor);
        assertEquals("Dr. Von Hausenberg", updatedDoctor.getName());
    }

    @Test
    void delete() throws ApiException {
        DoctorDTO doctorDTO = doctorDAO.create(new DoctorDTO("Dr. Owen James", LocalDate.of(1940, 7, 22), 1970, "Green Valley Hospital", Doctor.Speciality.PSYCHIATRY));
        doctorDAO.delete(doctorDTO.getId());
        assertThrows(EntityNotFoundException.class, () -> {
            doctorDAO.read(doctorDTO.getId());
        });
    }
}


 */