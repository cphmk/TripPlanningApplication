package dat.dtos;


import dat.entities.Guide;
import dat.entities.Trip;
import dat.security.entities.User;
import jakarta.persistence.*;
import lombok.*;
import dk.bugelhartmann.UserDTO;
import dat.entities.Trip;
import lombok.*;
import dk.bugelhartmann.UserDTO;

import java.time.LocalDate;
import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class GuideDTO {

    private Integer id;
    private User user;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private TripDTO trip;


    public GuideDTO(Guide guide) {
        this.id = guide.getId();
        this.user = new User(guide.getUser().getUsername(), guide.getUser().getPassword());
        this.firstName = guide.getFirstName();
        this.lastName = guide.getLastName();
        this.email = guide.getEmail();
        this.phone = guide.getPhone();
        this.trip = new TripDTO(guide.getTrip());

    }

}
