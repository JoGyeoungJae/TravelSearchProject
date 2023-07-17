package com.trInfo.dto;

import com.trInfo.entity.City;
import com.trInfo.entity.Country;
import com.trInfo.entity.MediaInfo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Getter
@Setter
@ToString
public class MediaFormDTO {

    private Long eid;

    @NotBlank(message = "제목 필수 입력")
    private String etitle;

    @NotBlank(message = "url 필수 입력")
    private String elink;

    private Country country;
    private City city;
    private Long countryid;
    private Long cityid;

    private List<MediaImgDTO> mediaImgDTOList = new ArrayList<>();

    private List<Long> mediaImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public MediaInfo createMediaInfo(){

        return modelMapper.map(this,MediaInfo.class);
    }

    public static MediaFormDTO of(MediaInfo mediainfo){
        log.info("dto of:" + mediainfo);
        log.info("mepped mediaformdto:"+ modelMapper.map(mediainfo,MediaFormDTO.class));
        return modelMapper.map(mediainfo,MediaFormDTO.class);
    }
}
