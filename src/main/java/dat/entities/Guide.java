package dat.entities;


import dat.dtos.GuideDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import dat.security.entities.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "guides")

public class Guide {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @ManyToOne
        @JoinColumn(name = "user_id", referencedColumnName = "username")
        private User user;


        @Setter
        @Column(name = "first_name", nullable = false)
        private String firstName;

        @Setter
        @Column(name = "last_name", nullable = false)
        private String lastName;

        @Setter
        @Column(name = "email", nullable = false)
        private String email;


        @Setter
        @Column(name = "phone", nullable = false)
        private String phone;

       @ManyToOne
       @JoinColumn(name = "trip_id")
       private Trip trip;


    public Guide(User user, String firstName, String lastName, String email, String phone, Trip trip) {
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.trip = trip;
    }

    public Guide(User user, Trip trip) {
            this.user = user;
            this.trip = trip;
        }

        public Guide(GuideDTO guideDTO) {
            this.user = new User(guideDTO.getUser().getUsername(), guideDTO.getUser().getPassword());
            this.trip = new Trip(guideDTO.getTrip());
        }
}
