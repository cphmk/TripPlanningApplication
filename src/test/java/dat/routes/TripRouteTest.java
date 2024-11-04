package dat.routes;

import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import dat.config.Populator;
import dat.daos.TripDAO;
import dat.security.controllers.SecurityController;
import dat.security.daos.SecurityDAO;
import dat.security.exceptions.ValidationException;
import dk.bugelhartmann.UserDTO;
import io.javalin.Javalin;
import io.restassured.http.ContentType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

class TripRouteTest {

    static Javalin app;
    static String BASE_URL = "http://localhost:2020/api";

    private final static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private final static SecurityController securityController = SecurityController.getInstance();
    private final static SecurityDAO securityDAO = new SecurityDAO(emf);
    private final static TripDAO tripDAO = TripDAO.getInstance(emf);
    private static String userToken, adminToken;

    @BeforeAll
    public static void setup() {
        app = ApplicationConfig.startServer(2020);
    }

    @BeforeEach
    public void setUp() {
        // Fill up the database
        Populator.main(null);

        try {
            UserDTO verifiedUser = securityDAO.getVerifiedUser("user1", "test1234");
            UserDTO verifiedAdmin = securityDAO.getVerifiedUser("admin", "admin1234");
            userToken = "Bearer " + securityController.createToken(verifiedUser);
            adminToken = "Bearer " + securityController.createToken(verifiedAdmin);
        } catch (ValidationException e) {
            throw new RuntimeException("User validation failed: " + e.getMessage(), e);
        }
    }

    @AfterEach
    public void cleanUp() {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Guide").executeUpdate();
            em.createQuery("DELETE FROM Trip").executeUpdate();
            em.createQuery("DELETE FROM User").executeUpdate();
            em.createQuery("DELETE FROM Role").executeUpdate();
            em.getTransaction().commit();
        }
    }

    @AfterAll
    public static void tearDown() {
        ApplicationConfig.stopServer(app);
    }

    @Test
    public void testGetAllTrips() {
        given()
                .when()
                .get(BASE_URL + "/trips/")
                .then()
                .statusCode(200)
                .body("size()", equalTo(5));
    }


    @Test
    public void testGetTripById() {
        // First, create a trip to test fetching by ID
        String json = """
            {
              "name": "Family trip",
              "startTime": "2024-3-12",
              "endTime": "2024-27-12",,
              "startPosition": "Copenhagen",
              "price": "2000"
              "category": "snow"
            }
            """;

        // Create a new trip first
        int tripId = given()
                .contentType(ContentType.JSON)
                .header("Authorization", adminToken)
                .body(json)
                .when()
                .post(BASE_URL + "/trips")
                .then()
                .statusCode(201)
                .extract()
                .path("id"); // Extract the ID of the newly created doctor

        // test fetching the trips  by ID
        given()
                .when()
                .get(BASE_URL + "/trips/" + tripId)
                .then()
                .statusCode(200)
                .body("name", equalTo("Jane Foster"))
                .body("category", equalTo("snow"));
    }



    @Test
    public void testCreateTrip() {
        String json = """
            {
              "name": "family trip",
              "startTime": "2025-3-4",
              "endTime": "2025-10-4",,
              "startPosition": "Cairo",
              "price": "500"
              "category": "beach"
            }
            """;

        given()
                .contentType(ContentType.JSON)
                .header("Authorization", adminToken)
                .body(json)
                .when()
                .post(BASE_URL + "/trips")
                .then()
                .statusCode(201)
                .body("name", equalTo("family trip"));
    }

}
