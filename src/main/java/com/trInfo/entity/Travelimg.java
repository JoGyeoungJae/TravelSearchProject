package com.trInfo.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "travelimg")
@Data
public class Travelimg {

    @Id
    @Column(name = "travelimgid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tid;

    private String imgName;  //이미지파일명

    private String oriImgName;  //원본 이미지 파일명

    private String imgUrl;   //이미지 조회 경로

    private String repimYn;   //대표 이미지 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tid")
    private TravelInfo travelInfo;

    public void updateTravelimg(String oriImgName, String imgName, String imgUrl){
        this.oriImgName=oriImgName;
        this.imgName=imgName;
        this.imgUrl=imgUrl;
    }



}
