package com.trInfo.dto;

import com.trInfo.entity.City;
import com.trInfo.entity.Country;
import com.trInfo.entity.FestivalInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Log4j2
@Getter
@Setter
@ToString
public class FestivalFormDto {

    private Long fid;

    @NotBlank(message = "제목 필수 입력")
    private String ftitle;

    @NotBlank(message = "내용 필수 입력")
    private String fcontent;

    @NotBlank(message = "주소 필수 입력")
    private String faddress;

    @NotBlank(message = "url 필수 입력")
    private String fmapurl;

    private String startdate;

    private String enddate;

    private String imgUrl;

    private Country country;
    private City city;

    private Long countryid;

    private Long cityid;

    private List<FestivalImgDto> festivalImgDtoList = new ArrayList<>();

    private List<Long> festivalImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public FestivalInfo createFestivalinfo(){
        return modelMapper.map(this,FestivalInfo.class);
    }

    public static FestivalFormDto of(FestivalInfo festivalinfo){
        log.info("dto of : " + festivalinfo);
        log.info("mapped festivalformdto : " + modelMapper.map(festivalinfo,FestivalFormDto.class));
        return modelMapper.map(festivalinfo,FestivalFormDto.class);
    }

}
