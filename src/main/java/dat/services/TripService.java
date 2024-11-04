package dat.services;


import dat.daos.TripDAO;
import dat.dtos.TripDTO;
import dat.security.exceptions.ApiException;
import jakarta.persistence.EntityManagerFactory;


import java.util.List;

public class TripService {

    private static TripDAO tripDAO;
    private static TripService instance;

    public static TripService getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            instance = new TripService();
            tripDAO = TripDAO.getInstance(_emf);
        }
        return instance;
    }

    public TripDTO read(int id) throws dat.exceptions.ApiException {
        return tripDAO.read(id);
    }


    public List<TripDTO> readAll() throws dat.exceptions.ApiException {
        return tripDAO.readAll();
    }

    public TripDTO create(TripDTO tripDTO) throws dat.exceptions.ApiException {
        return tripDAO.create(tripDTO);
    }

    public TripDTO update(int id, TripDTO tripDTO) throws dat.exceptions.ApiException {
        return tripDAO.update(id, tripDTO);
    }

    public void delete(int id) throws dat.exceptions.ApiException {
        if (!validatePrimaryKey(id)) {
            throw new ApiException(404,"Doctor not found"); // Custom message and status code
        }
        tripDAO.delete(id);
    }



    public boolean validatePrimaryKey(Integer id) {
        return tripDAO.validatePrimaryKey(id);
    }

    public boolean validateDoctorDTO(TripDTO tripDTO) {
        return tripDTO.getName() != null && !tripDTO.getName().isEmpty()
                && tripDTO.getCategory() != null
                && tripDTO.getStartPosition() != null;
    }
}

