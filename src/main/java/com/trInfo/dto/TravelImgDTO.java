package com.trInfo.dto;

import com.trInfo.entity.Travelimg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
@Getter
@Setter
public class TravelImgDTO {

    private Long tid;

    private String imgName;  //이미지파일명

    private String oriImgName;  //원본 이미지 파일명

    private String imgUrl;   //이미지 조회 경로

    private String repimYn;   //대표 이미지 여부

    private static ModelMapper modelMapper = new ModelMapper();

    public static TravelImgDTO of(Travelimg travelImg){

        return modelMapper.map(travelImg, TravelImgDTO.class);
    }
}
