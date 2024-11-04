package dat.controllers.impl;

import dat.config.HibernateConfig;
import dat.controllers.IController;
import dat.dtos.TripDTO;;
import dat.security.exceptions.ApiException;
import dat.services.TripService;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;


import java.util.List;

public class TripController implements IController<TripDTO, Integer> {

    private final TripService service;

    public TripController() {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        this.service = TripService.getInstance(emf);
    }

    @Override
    public void create(Context ctx) {
        try {
            TripDTO jsonRequest = ctx.bodyAsClass(TripDTO.class);
            TripDTO tripDTO = service.create(jsonRequest);

            if (tripDTO == null) {
                ctx.status(400).result("Invalid trips data");
                return;
            }

            ctx.status(201).json(tripDTO);
        } catch (Exception e) {
            ctx.status(500).result("An error occurred while creating a trips");
        }
    }

    @Override
    public void readAll(Context ctx) {
        try {
            List<TripDTO> tripDTOS = service.readAll();
            if (tripDTOS.isEmpty()) {
                ctx.status(404).result("No trips found");
                return;
            }

            ctx.status(200).json(tripDTOS);
        } catch (Exception e) {
            ctx.status(500).result("An error occurred while fetching all trips");
        }
    }

    @Override
    public void read(Context ctx) {
        try {
            int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
            TripDTO tripDTO = service.read(id);
            ctx.status(200).json(tripDTO);
        } catch (Exception e) {
            ctx.status(500).result("An error occurred while fetching a trip");
        }
    }



    @Override
    public void update(Context ctx) {
        try {
            int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
            TripDTO tripDTO = ctx.bodyAsClass(TripDTO.class);
            TripDTO updatedTripDTO = service.update(id, tripDTO);
            ctx.status(200).json(updatedTripDTO);
        } catch (Exception e) {
            ctx.status(500).result("An error occurred while updating trips data");
        }
    }

    @Override
    public void delete(Context ctx) {
        try {
            int id = ctx.pathParamAsClass("id", Integer.class)
                    .check(this::validatePrimaryKey, "Not a valid id")
                    .get();
            service.delete(id);
            ctx.status(204);
        } catch (ApiException e) {
            if (e.getCode()== 404) {
                ctx.status(404).result(e.getMessage());
            } else {
                ctx.status(500).result("An error occurred while deleting trips data");
            }
        } catch (Exception e) {
            ctx.status(500).result("An error occurred while deleting trips data");
        }
    }


    @Override
    public boolean validatePrimaryKey(Integer integer) {
        return service.validatePrimaryKey(integer);
    }

    @Override
    public TripDTO validateEntity(Context ctx) {
        return ctx.bodyValidator(TripDTO.class)
                .check(t -> t.getName() != null && !t.getName().isEmpty(), "Trip's name must be set")
                .check(d -> d.getCategory() != null, "Trip's category must be set")
                .check(d -> d.getStartPosition() != null, "Trip's start position must be set")
                .get();
    }
}






