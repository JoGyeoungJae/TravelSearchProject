package com.trInfo.entity;

import com.trInfo.dto.FestivalFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@ToString
@Entity
@Table(name = "festivalinfo")
@Getter
@Setter
public class FestivalInfo {
    /*country_id FK
    city_id FK*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fid;                    //pk
    private String ftitle;                //제목
    private String fcontent;            //내용
    private String faddress;            //주소
    private String fmapurl;            //구글맵 url
    private String startdate;    //축제 시작 날짜
    private String enddate;        //축제 끝 날짜

    @ManyToOne
    @JoinColumn(name = "countryid")
    private Country country;
    @ManyToOne
    @JoinColumn(name = "cityid")
    private City city;

    public void updateFestival(FestivalFormDto festivalFormDto) {
        this.ftitle = festivalFormDto.getFtitle();
        this.fcontent = festivalFormDto.getFcontent();
        this.faddress = festivalFormDto.getFaddress();
        this.fmapurl = festivalFormDto.getFmapurl();
        this.startdate = festivalFormDto.getStartdate();
        this.enddate = festivalFormDto.getEnddate();
        this.country =festivalFormDto.getCountry();
        this.city = festivalFormDto.getCity();

    }

}
