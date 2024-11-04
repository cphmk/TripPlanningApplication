package dat.routes;

import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.path;

public class Routes {

    private final TripRoute tripRoute = new TripRoute();
    private final GuideRoute guideRoute = new GuideRoute();

    public EndpointGroup getRoutes() {
        return () -> {
            path("/trips", tripRoute.getRoutes());
            path("/guides", guideRoute.getRoutes());
        };
    }
}

