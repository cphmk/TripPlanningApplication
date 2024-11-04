package dat.entities;



import dat.dtos.TripDTO;
import dat.security.entities.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "trips")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id", nullable = false, unique = true)
    private Integer id;

    @Setter
    @Column(name = "trip_name", nullable = false, unique = true)
    private String name;

    @Setter
    @Column(name = "date", nullable = false)
    private LocalDate startTime;

    @Setter
    @Column(name = "time", nullable = false)
    private LocalDate endTime;

    @Setter
    @Column(name = "start_position", nullable = false)
    private String startPosition;

    @Setter
    @Column(name = "price", nullable = false)
    private int price;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "speciality", nullable = true)
    private Category category;

    @Setter
    @ManyToOne
    @JoinColumn(name = "User_id", nullable = true)
    private User user;


    public Trip(String name, LocalDate startTime, LocalDate endTime, String startPosition, int price, Category category) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startPosition = startPosition;
        this.price = price;
        this.category = category;
    }

    public Trip(TripDTO tripDTO) {
        this.id = tripDTO.getId();
        this.name = tripDTO.getName();
        this.startTime = tripDTO.getStartTime();
        this.endTime = tripDTO.getEndTime();
        this.startPosition = tripDTO.getStartPosition();
        this.price = tripDTO.getPrice();
        this.category = tripDTO.getCategory();
    }


    public enum Category {
        beach, city, forest, lake, sea, snow
    }
}