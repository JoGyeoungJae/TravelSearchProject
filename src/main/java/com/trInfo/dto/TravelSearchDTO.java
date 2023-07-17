package com.trInfo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TravelSearchDTO {

    private String searchDateType;   //현재 시간과 여행지 등록일을 비교해서 데이터 조회

    private String searchBy;     //여행지를 조회할 때 어떤 유형으로 조회할 지 선택

    private String searchQuery = "";   //조회할 검색어 저장할 변수
}
