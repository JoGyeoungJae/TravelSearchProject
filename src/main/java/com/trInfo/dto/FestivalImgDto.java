package com.trInfo.dto;

import com.trInfo.entity.FestivalImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class FestivalImgDto {

    private Long fiid;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repImgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    public static FestivalImgDto of(FestivalImg festivalImg){
        return modelMapper.map(festivalImg, FestivalImgDto.class);
    }
}
