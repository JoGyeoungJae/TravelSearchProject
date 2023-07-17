package com.trInfo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservation")
@Getter
@Setter
public class Reservation {
    /*bkid fk*/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reid;                  //  pk
    private LocalDateTime startdate;   //시작날짜
    private LocalDateTime enddate;     //마지막날짜

    @ManyToOne
    @JoinColumn(name = "mid")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "tid")
    private TravelInfo travelInfo;

    @ManyToOne
    @JoinColumn(name = "fid")
    private FestivalInfo festivalInfo;
}
