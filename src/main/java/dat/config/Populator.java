package dat.config;

import dat.entities.Guide;
import dat.entities.Trip;
import dat.security.entities.Role;
import dat.security.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Populator {

    static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();

    public static void main(String[] args) {
        Set<Trip> trips = getTrips();

        User user1 = new User("user1", "test1234");
        User user2 = new User("user2", "test1234");
        User admin = new User("admin", "admin1234");
        Role userRole = new Role("USER");
        Role adminRole = new Role("ADMIN");
        user1.addRole(userRole);
        user2.addRole(userRole);
        admin.addRole(adminRole);

        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            trips.forEach(em::persist);
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user1);
            em.persist(user2);
            em.persist(admin);

            em.getTransaction().commit();
        }

        // Assign some appointments for the users
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Set<Guide> guides = new HashSet<>();

            // Add guides for user1
            Optional<Trip> firstTrip = trips.stream().findFirst();
            firstTrip.ifPresent(trip -> guides.add(new Guide(user1, "Jason", "Hansen", "JHBase@hotmail.com", "45257789", trip)));

            // Using a loop to avoid out-of-bounds issues
            for (int i = 0; i < trips.size(); i++) {
                Optional<Trip> trip = trips.stream().skip(i).findFirst();
                trip.ifPresent(tri -> guides.add(new Guide(user1, "Jason", "Hansen", "JHBase@hotmail.com", "45257789", tri)));
            }

            // Add trips for user2
            for (int i = 0; i < trips.size(); i++) {
                Optional<Trip> trip = trips.stream().skip(i).findFirst();
                trip.ifPresent(tri -> guides.add(new Guide(user2, "Jack", "Smith", "JackSmithy@Gmail.com", "52256799", tri)));
            }

            guides.forEach(em::persist);
            em.getTransaction().commit();
        }
    }

    private static Set<Trip> getTrips() {
        Set<Trip> trips = new HashSet<>();

        trips.add(new Trip("Family trip", LocalDate.of(2024, 5, 15), LocalDate.of(2024, 6, 15), "Copenhagen", 2000, Trip.Category.beach));
        trips.add(new Trip("Shopping trip", LocalDate.of(2025, 2, 1), LocalDate.of(2025, 3, 1), "London",5000, Trip.Category.city ));
        trips.add(new Trip("Mountain trip", LocalDate.of(2024, 11, 25), LocalDate.of(2024, 12, 2), "Oslo", 7000, Trip.Category.snow));
        trips.add(new Trip("Road trip", LocalDate.of(2026, 7, 10), LocalDate.of(2026, 9, 10), "Cairo", 1000, Trip.Category.beach));
        trips.add(new Trip("Family trip", LocalDate.of(2025, 5, 1), LocalDate.of(2024, 5, 25), "Oslo", 2255, Trip.Category.forest));
        trips.add(new Trip("Family trip", LocalDate.of(2024, 11, 6), LocalDate.of(2024, 12, 1), "Copenhagen", 500, Trip.Category.snow));

        return trips;
    }
}
