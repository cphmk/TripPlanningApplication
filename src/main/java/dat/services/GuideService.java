package dat.services;


import dat.daos.GuideDAO;
import dat.dtos.GuideDTO;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class GuideService {

    private static GuideDAO guideDAO;
    private static GuideService instance;

    public static GuideService getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            instance = new GuideService();
            guideDAO = GuideDAO.getInstance(_emf);
        }
        return instance;
    }

    public GuideDTO read(Long id) {
        return guideDAO.read(id);
    }

    public List<GuideDTO> readAll() {
        return guideDAO.readAll();
    }

    public GuideDTO create(GuideDTO guideDTO) {
        return guideDAO.create(guideDTO);
    }

    public GuideDTO update(Long id, GuideDTO guideDTO) {
        return guideDAO.update(id, guideDTO);
    }

    public void delete(Long id) {
        guideDAO.delete(id);
    }

    public boolean validatePrimaryKey(Long id) {
        return guideDAO.validatePrimaryKey(id);
    }

    public boolean validateGuideDTO(GuideDTO guideDTO) {
        return guideDTO.getFirstName() != null && !guideDTO.getFirstName().isEmpty()
                && guideDTO.getLastName() != null
                && guideDTO.getEmail() != null;
    }
}

