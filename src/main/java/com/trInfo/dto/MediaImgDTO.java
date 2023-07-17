package com.trInfo.dto;

import com.trInfo.entity.MediaImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class MediaImgDTO {

    private Long eiid;

    private String imgName;

    private String oriImgName;

    private String imgUrl;

    private String repImgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    public static MediaImgDTO of(MediaImg mediaImg){
        return modelMapper.map(mediaImg, MediaImgDTO.class);
    }

}


