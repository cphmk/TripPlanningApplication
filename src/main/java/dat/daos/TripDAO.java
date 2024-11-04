package dat.daos;

import dat.dtos.TripDTO;
import dat.entities.Trip;
import dat.exceptions.ApiException;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class TripDAO implements IDAO<TripDTO, Integer> {

    private static TripDAO instance;
    private static EntityManagerFactory emf;

    public static TripDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new TripDAO();
        }
        return instance;
    }

    @Override
    public TripDTO read(Integer id) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            Trip trip = em.find(Trip.class, id);
            if (trip == null) {
                throw new ApiException(400,"Trip not found");
            }
            return new TripDTO(trip);
        }
    }



    @Override
    public List<TripDTO> readAll() throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<TripDTO> query = em.createQuery(
                    "SELECT new dat.dtos.TripDTO(t) FROM Trip t", TripDTO.class);
            return query.getResultList();
        } catch (PersistenceException e) {
            throw new ApiException(400, "Something went wrong during readAll");
        }
    }

    @Override
    public TripDTO create(TripDTO tripDTO) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Trip trip = new Trip(tripDTO);

            TypedQuery<Trip> query = em.createQuery("SELECT t FROM Trip t WHERE t.name = :name", Trip.class);
            query.setParameter("name", trip.getName());

            if (!query.getResultList().isEmpty()) {
                throw new ApiException(400, "Trip with the given name already exists");
            }

            em.persist(trip);
            em.getTransaction().commit();
            return new TripDTO(trip);
        } catch (PersistenceException e) {
            throw new ApiException(500, "Something went wrong during trip creation");
        }
    }


    @Override
    public TripDTO update(Integer id, TripDTO tripDTO) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Trip trip = em.find(Trip.class, id);

            if (trip == null) {
                throw new ApiException(404, "Trip is not found");
            }

            trip.setCategory(tripDTO.getCategory());
            trip.setName(tripDTO.getName());
            trip.setPrice(tripDTO.getPrice());

            Trip mergedTrip = em.merge(trip);
            em.getTransaction().commit();
            return new TripDTO(mergedTrip);
        } catch (PersistenceException e) {
            throw new ApiException(400, "Trip not found or something else went wrong during update");
        }
    }

    @Override
    public void delete(Integer id) throws ApiException {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Trip trip = em.find(Trip.class, id);
            if (trip != null) {
                em.remove(trip);
            }
            em.getTransaction().commit();
        } throw new ApiException(404, "No trip was deleted");
    }

    @Override
    public boolean validatePrimaryKey(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            Trip trip = em.find(Trip.class, id);
            return trip != null;
        }
    }
}
