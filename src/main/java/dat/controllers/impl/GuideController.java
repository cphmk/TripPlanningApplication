package dat.controllers.impl;

import dat.config.HibernateConfig;
import dat.controllers.IController;
import dat.dtos.GuideDTO;
import dat.services.GuideService;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public class GuideController implements IController<GuideDTO, Long> {

    private final GuideService guideService;

    public GuideController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.guideService = GuideService.getInstance(emf);
    }

    @Override
    public void read(Context ctx) {
        Long id = ctx.pathParamAsClass("id", Long.class).check(this::validatePrimaryKey, "Not a valid ID").get();
        try {
            GuideDTO guideDTO = guideService.read(id);
            ctx.res().setStatus(200);
            ctx.json(guideDTO);
        } catch (EntityNotFoundException e) {
            ctx.res().setStatus(404);
            ctx.json(e.getMessage());
        } catch (Exception e) {
            ctx.res().setStatus(500);
            ctx.json(e.getMessage());
        }
    }

    @Override
    public void readAll(Context ctx) {
        try {
            List<GuideDTO> guides = guideService.readAll();
            ctx.res().setStatus(200);
            ctx.json(guides);
        } catch (Exception e) {
            ctx.res().setStatus(500);
            ctx.json(e.getMessage());
        }
    }

    @Override
    public void create(Context ctx) {
        GuideDTO guideDTO = ctx.bodyAsClass(GuideDTO.class);
        try {
            GuideDTO createdGuide = guideService.create(guideDTO);
            ctx.res().setStatus(201);
            ctx.json(createdGuide);
        } catch (IllegalArgumentException e) {
            ctx.res().setStatus(400);
            ctx.json("Invalid Guide data: " + e.getMessage());
        } catch (Exception e) {
            ctx.res().setStatus(500);
            ctx.json(e.getMessage());
        }
    }

    @Override
    public void update(Context ctx) {
        Long id = ctx.pathParamAsClass("id", Long.class).check(this::validatePrimaryKey, "Not a valid ID").get();
        GuideDTO guideDTO = ctx.bodyAsClass(GuideDTO.class);

        try {
            GuideDTO updatedGuide = guideService.update(id, guideDTO);
            ctx.res().setStatus(200);
            ctx.json(updatedGuide);
        } catch (EntityNotFoundException e) {
            ctx.res().setStatus(404);
            ctx.json("guide not found: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            ctx.res().setStatus(400);
            ctx.json("Invalid data: " + e.getMessage());
        } catch (Exception e) {
            ctx.res().setStatus(500);
            ctx.json(e.getMessage());
        }
    }

    @Override
    public void delete(Context ctx) {
        Long id = ctx.pathParamAsClass("id", Long.class).check(this::validatePrimaryKey, "Not a valid ID").get();
        try {
            guideService.delete(id);
            ctx.status(204);
        } catch (EntityNotFoundException e) {
            ctx.res().setStatus(404);
            ctx.json("guide not found: " + e.getMessage());
        } catch (Exception e) {
            ctx.res().setStatus(500);
            ctx.json(e.getMessage());
        }
    }

    @Override
    public boolean validatePrimaryKey(Long id) {
        return id != null && id > 0;
    }

    @Override
    public GuideDTO validateEntity(Context ctx) {
        GuideDTO guideDTO = ctx.bodyAsClass(GuideDTO.class);
        if (!guideService.validateGuideDTO(guideDTO)) {
            throw new IllegalArgumentException("Invalid GuideDTO: All required fields must be filled.");
        }
        return guideDTO;
    }
}
