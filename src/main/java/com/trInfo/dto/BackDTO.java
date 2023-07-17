package com.trInfo.dto;

import com.trInfo.entity.*;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class BackDTO {
    private Long bkid;	        	//pk
    private Long tid;
    private Long mid;
    private Long fid;



    private String imgUrl;

    private Country country;
    private City city;
    private Member member;
    private FestivalInfo festivalInfo;
    private TravelInfo travelInfo;


    private static ModelMapper modelMapper = new ModelMapper();

    public Back createBack(){
        return modelMapper.map(
                this,Back.class);
    }

    public static BackDTO of(Back back){

        return modelMapper.map(back,BackDTO.class);
    }
}
