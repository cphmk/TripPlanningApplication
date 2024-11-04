package dat.daos;


import dat.dtos.GuideDTO;
import dat.entities.Guide;
import dat.entities.Trip;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class GuideDAO implements IDAO<GuideDTO, Long> {

    private static GuideDAO instance;
    private static EntityManagerFactory emf;

    public static GuideDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new GuideDAO();
        }
        return instance;
    }

    @Override
    public GuideDTO read(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            Guide guide = em.find(Guide.class, id);
            if (guide == null) {
                throw new EntityNotFoundException("Appointment not found");
            }
            return new GuideDTO(guide);
        }
    }

    @Override
    public List<GuideDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<GuideDTO> query = em.createQuery(
                    "SELECT new dat.dtos.GuideDTO(g) FROM Guide g", GuideDTO.class);
            return query.getResultList();
        }
    }

    @Override
    public GuideDTO create(GuideDTO guideDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Guide guide = new Guide(guideDTO);

            TypedQuery<Guide> query = em.createQuery("SELECT g FROM Guide g WHERE g.firstName = :firstName", Guide.class);
            query.setParameter("firstName", guideDTO.getFirstName());

            if (query.getResultList().isEmpty()) {
                em.persist(guide);
            } else {
                guide = query.getSingleResult();
            }

            em.getTransaction().commit();
            return new GuideDTO(guide);
        }
    }

    @Override
    public GuideDTO update(Long id, GuideDTO guideDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Guide guide = em.find(Guide.class, id);

            if (guide == null) {
                throw new EntityNotFoundException("Appointment not found");
            }

            // Update fields
            guide.setFirstName(guideDTO.getFirstName());
            guide.setLastName(guideDTO.getLastName());
            guide.setEmail(guideDTO.getEmail());
            guide.setPhone(guideDTO.getPhone());

            // Find and set Trip (if different ID provided in DTO)
            if (guideDTO.getTrip().getId() != null) {
                Trip trip = em.find(Trip.class, guideDTO.getTrip().getId());
                if (trip != null) {
                    guide.setTrip(trip);
                } else {
                    throw new EntityNotFoundException("Doctor not found");
                }
            }

            Guide mergedGuide = em.merge(guide);
            em.getTransaction().commit();
            return new GuideDTO(mergedGuide);
        }
    }

    @Override
    public void delete(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Guide guide = em.find(Guide.class, id);
            if (guide != null) {
                em.remove(guide);
            }
            em.getTransaction().commit();
        }
    }

    @Override
    public boolean validatePrimaryKey(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT COUNT(g) > 0 FROM Guide g WHERE g.id = :id", Boolean.class)
                    .setParameter("id", id)
                    .getSingleResult();
        }
    }
}
