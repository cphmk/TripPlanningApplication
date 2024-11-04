package dat.dtos;


import dat.entities.Trip;
import dat.security.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder

public class TripDTO {

    private Integer id;
    private String name;
    private LocalDate startTime;
    private LocalDate endTime;
    private String startPosition;
    private int price;
    private Trip.Category category;
    private User user;


    public TripDTO(String name, LocalDate startTime, LocalDate endtime, String startPosition, int price, Trip.Category category, User user) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endtime;
        this.startPosition = startPosition;
        this.price = price;
        this.category = category;
        this.user = user;
    }

    public TripDTO(Trip trip) {
        this.id = trip.getId();
        this.name = trip.getName();
        this.startTime = trip.getStartTime();
        this.endTime = trip.getEndTime();
        this.startPosition = trip.getStartPosition();
        this.price = trip.getPrice();
        this.category = trip.getCategory();
    }
}
