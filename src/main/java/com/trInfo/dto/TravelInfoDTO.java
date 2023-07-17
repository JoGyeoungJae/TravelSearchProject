package com.trInfo.dto;

import com.trInfo.entity.City;
import com.trInfo.entity.Country;
import com.trInfo.entity.TravelInfo;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
public class TravelInfoDTO {

    private Long tid;		                //pk

    @NotBlank(message = "여행지명은 필수 입력 값입니다.")
    private String ttitle;                  //제목

    @NotBlank(message = "내용은 필수 입력 값입니다.")
    private String tcontent;        		//내용

    @NotBlank(message = "지도는 필수 입력 값입니다.")
    private String taddress;		        //상세주소 ->map

    private String ttimediffer;         	//시차
    private String textchange;	            //환율

    private String imgUrl;   //이미지 조회 경로

    private Country country;

    private City city;

    private Long countryid;

    private Long cityid;

    private List<TravelImgDTO> travelImgDTOList = new ArrayList<>();

    private List<Long> travelImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public TravelInfo createTravelinfo(){

        return modelMapper.map(this, TravelInfo.class);
    }

    public static TravelInfoDTO of(TravelInfo travelInfo){

        return modelMapper.map(travelInfo, TravelInfoDTO.class);
    }


}
