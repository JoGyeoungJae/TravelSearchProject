package com.trInfo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "touristdestination")
@Getter
@Setter
public class TouristDestination {
    /*country_id	FK
    city_id 		FK
    tid		FK*/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tdid;		//pk
    private String tdtitle; 		//제목
    private String tdcontent;		//내용
    private String tdaddress;		//상세주소 ->map

    @ManyToOne
    @JoinColumn(name = "countryid")
    private Country country;

    @ManyToOne
    @JoinColumn(name = "cityid")
    private City city;

    @ManyToOne
    @JoinColumn(name = "tid")
    private TravelInfo travelInfo;

}
