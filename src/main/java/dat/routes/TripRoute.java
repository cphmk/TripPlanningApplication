package dat.routes;


import dat.controllers.impl.TripController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class TripRoute {

    private final TripController tripController = new TripController();

    protected EndpointGroup getRoutes() {
        return () -> {
            get("/", tripController::readAll, Role.ANYONE);
            get("/{id}", tripController::read, Role.ANYONE);
            post("/", tripController::create, Role.ADMIN);
            put("/{id}", tripController::update, Role.ADMIN);

        };
    }
}