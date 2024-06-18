package com.carauction.models;

import com.carauction.models.enums.AuctionStatus;
import com.carauction.models.enums.Transmission;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Auction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String name;
    private String contact;

    private String date;
    private String poster;
    private String[] screenshots;
    private String file;
    private int power;
    private int mileage;

    private int price;
    private int begin;

    private String dateAndTimeStart;
    private String dateAndTimeEnd;

    @Enumerated(EnumType.STRING)
    private AuctionStatus status = AuctionStatus.WAITING;

    @Column(length = 5000)
    private String description = "";

    @Enumerated(EnumType.STRING)
    private Transmission transmission;

    @ManyToOne
    private Users owner = null;
    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL)
    private List<Comments> comments = new ArrayList<>();

    public Auction(String name, String contact, String date, String poster, String[] screenshots, String file, int power, int mileage, int price, int begin, String dateAndTimeStart, String dateAndTimeEnd, String description, Transmission transmission) {
        this.name = name;
        this.contact = contact;
        this.date = date;
        this.poster = poster;
        this.screenshots = screenshots;
        this.file = file;
        this.power = power;
        this.mileage = mileage;
        this.price = price;
        this.begin = begin;
        this.dateAndTimeStart = dateAndTimeStart;
        this.dateAndTimeEnd = dateAndTimeEnd;
        this.description = description;
        this.transmission = transmission;
    }

    public String getStart() {
        return dateAndTimeStart.substring(0, 10) + " " + dateAndTimeStart.substring(11, 16);
    }

    public String getEnd() {
        return dateAndTimeEnd.substring(0, 10) + " " + dateAndTimeEnd.substring(11, 16);
    }
}
