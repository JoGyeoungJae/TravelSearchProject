package com.trInfo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MediaInfoDTO {


    private Long eid;		    //pk
    private String etitle;      //제목
    private String elink;
    private Long countryid;
    private Long cityid;
}
