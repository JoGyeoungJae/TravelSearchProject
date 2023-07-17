package com.trInfo.entity;

import com.trInfo.dto.TravelInfoDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "travelinfo")
@Data
public class TravelInfo {
    /*
    country_id FK
    city_id FK    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tid;		                //pk
    private String ttitle;                  //제목
    private String tcontent;        		//내용
    private String taddress;		        //상세주소 ->map
    private String ttimediffer;         	//시차
    private String textchange;	            //환율

    @ManyToOne
    @JoinColumn(name = "countryid")
    private Country country;
    @ManyToOne
    @JoinColumn(name = "cityid")
    private City city;

    public void updateTravelInfo(TravelInfoDTO travelInfoDTO){
        this.ttitle = travelInfoDTO.getTtitle();
        this.tcontent = travelInfoDTO.getTcontent();
        this.taddress = travelInfoDTO.getTaddress();
        this.textchange = travelInfoDTO.getTextchange();
        this.ttimediffer = travelInfoDTO.getTtimediffer();
    }
}
