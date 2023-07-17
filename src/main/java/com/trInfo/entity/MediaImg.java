package com.trInfo.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "mediaimg")
@Getter
@Setter
public class MediaImg {

    @Id
    @Column(name = "eiid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long eiid;
    private String imgName; // 파일명
    private String oriImgName; // 원본 이미지 파일명
    private String imgUrl; // 경로
    private String repImgYn; // 대표 이미지 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eid")
    private MediaInfo mediainfo;

    public void updateMediaImg(String oriImgName, String imgName, String imgUrl){
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }
}



