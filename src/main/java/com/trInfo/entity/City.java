package com.trInfo.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "city")
@Data
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cityid;		    //pk
    private String ccity;       	//도시명

    @ManyToOne
    @JoinColumn(name = "countryid")
    private Country country;
}
