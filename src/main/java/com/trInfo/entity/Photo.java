package com.trInfo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "photo")
@Getter
@Setter
public class Photo {
    /*fid	fk
    tid	fk*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;		            //pk
    private String photoname;	        //파일명

    @ManyToOne
    @JoinColumn(name = "tid")
    private TravelInfo travelInfo;
    @ManyToOne
    @JoinColumn(name = "fid")
    private FestivalInfo festivalInfo;
}
