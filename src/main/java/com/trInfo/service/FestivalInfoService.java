package com.trInfo.service;

import com.trInfo.dto.FestivalFormDto;
import com.trInfo.dto.TravelInfoDTO;
import com.trInfo.entity.*;
import com.trInfo.repository.CityRepository;
import com.trInfo.repository.CountryRepository;
import com.trInfo.repository.FestivalImgRepository;
import com.trInfo.repository.FestivalRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FestivalInfoService {
    @Autowired
    private FestivalRepository festivalRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private FestivalImgRepository festivalImgRepository;

    //    public List<FestivalInfo> getList(){
//
//        return festivalRepository.findAll();
//    }
    public Page<FestivalInfo> festivallist(Pageable pageable){

        return festivalRepository.findAll(pageable);
    }

    public Page<FestivalFormDto> getAllList(Pageable pageable){
        List<FestivalInfo> festivalInfoList = festivalRepository.findAll();
        List<FestivalFormDto> festivalInfoDtoList = new ArrayList<>();

        for (int i=0; i<festivalInfoList.size(); i++){
            Country country = countryRepository.findByCountryid(festivalInfoList.get(i).getCountry().getCountryid());
            City city = cityRepository.findByCityid(festivalInfoList.get(i).getCity().getCityid());
            FestivalImg festivalImg = festivalImgRepository.findAllByFestivalinfo_FidAndRepImgYn(festivalInfoList.get(i).getFid(),"Y");
            FestivalFormDto dto = FestivalFormDto.of(festivalInfoList.get(i));
            dto.setCountry(country);
            dto.setCity(city);
            dto.setImgUrl(festivalImg.getImgUrl());
            festivalInfoDtoList.add(i,dto);
        }
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), festivalInfoDtoList.size());
        final Page<FestivalFormDto> page = new PageImpl<>(festivalInfoDtoList.subList(start, end), pageable, festivalInfoDtoList.size());

        return page;
    }

    public Page<FestivalFormDto> getsearchlist(Pageable pageable, Long countryid, Long cityid){
        List<FestivalInfo> festivalInfoList = festivalRepository.findAllByCountry_CountryidAndCity_Cityid(countryid,cityid);
        List<FestivalFormDto> fetivalInfoDTOsearchList = new ArrayList<>();
        for (int i=0; i<festivalInfoList.size(); i++){
            Country country = countryRepository.findByCountryid(festivalInfoList.get(i).getCountry().getCountryid());
            City city =  cityRepository.findByCityid(festivalInfoList.get(i).getCity().getCityid());
            FestivalImg festivalImg = festivalImgRepository.findAllByFestivalinfo_FidAndRepImgYn(festivalInfoList.get(i).getFid(),"Y");
            FestivalFormDto dto = FestivalFormDto.of(festivalInfoList.get(i));
            dto.setCountry(country);
            dto.setCity(city);
            dto.setImgUrl(festivalImg.getImgUrl());
            fetivalInfoDTOsearchList.add(i,dto);
            System.out.println(fetivalInfoDTOsearchList.get(i));
        }
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), fetivalInfoDTOsearchList.size());
        final Page<FestivalFormDto> page = new PageImpl<>(fetivalInfoDTOsearchList.subList(start, end), pageable, fetivalInfoDTOsearchList.size());
        return page;
    }

    public Page<FestivalFormDto> getsearchcitylist(Pageable pageable,Long countryid){
        List<FestivalInfo> festivalInfoList =  festivalRepository.findAllByCountry_Countryid(countryid);
        List<FestivalFormDto>  fetivalInfoDTOsearchList = new ArrayList<>();
        for (int i=0; i<festivalInfoList.size(); i++){
            Country country = countryRepository.findByCountryid(festivalInfoList.get(i).getCountry().getCountryid());
            City city =  cityRepository.findByCityid(festivalInfoList.get(i).getCity().getCityid());
            FestivalImg festivalImg = festivalImgRepository.findAllByFestivalinfo_FidAndRepImgYn(festivalInfoList.get(i).getFid(),"Y");
            FestivalFormDto dto = FestivalFormDto.of(festivalInfoList.get(i));
            dto.setCountry(country);
            dto.setCity(city);
            fetivalInfoDTOsearchList.add(i,dto);
            dto.setImgUrl(festivalImg.getImgUrl());
            System.out.println(fetivalInfoDTOsearchList.get(i));
        }
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), fetivalInfoDTOsearchList.size());
        final Page<FestivalFormDto> page = new PageImpl<>(fetivalInfoDTOsearchList.subList(start, end), pageable, fetivalInfoDTOsearchList.size());
        return page;
    }
}
