package com.trInfo.service;

import com.trInfo.dto.FestivalFormDto;
import com.trInfo.dto.TravelImgDTO;
import com.trInfo.dto.TravelInfoDTO;
import com.trInfo.entity.*;
import com.trInfo.repository.CityRepository;
import com.trInfo.repository.CountryRepository;
import com.trInfo.repository.TravelImgRepository;
import com.trInfo.repository.TravelInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TravelInfoService{

    @Autowired
    private TravelInfoRepository travelInfoRepository;
    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private TravelImgRepository travelImgRepository;

    public List<TravelInfo> getList(){

        return travelInfoRepository.findAll();
    }

//    public Page<TravelInfo> travellist(Pageable pageable){
//        return travelInfoRepository.findAll(pageable);
//    }

    public Page<TravelInfoDTO> getAllList(Pageable pageable){
        List<TravelInfo> travelInfoList =  travelInfoRepository.findAll();
        List<TravelInfoDTO>  travelInfoDTOList = new ArrayList<>();
        for (int i=0; i<travelInfoList.size(); i++){
            Country country = countryRepository.findByCountryid(travelInfoList.get(i).getCountry().getCountryid());
            City city =  cityRepository.findByCityid(travelInfoList.get(i).getCity().getCityid());
            Travelimg travelimg = travelImgRepository.findAllByTravelInfo_TidAndRepimYn(travelInfoList.get(i).getTid(),"Y");
            TravelInfoDTO dto = TravelInfoDTO.of(travelInfoList.get(i));
            dto.setCountry(country);
            dto.setCity(city);
            dto.setImgUrl(travelimg.getImgUrl());
            travelInfoDTOList.add(i,dto);
            System.out.println(travelInfoDTOList.get(i));
        }
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), travelInfoDTOList.size());
        final Page<TravelInfoDTO> page = new PageImpl<>(travelInfoDTOList.subList(start, end), pageable, travelInfoDTOList.size());
        return page;
    }

//    public Page<Board> getAllList(Pageable pageable) {
//        return boardRepository.findAll(pageable);
//    }

    public Page<TravelInfoDTO> getsearchlist(Pageable pageable,Long countryid, Long cityid){
        List<TravelInfo> travelInfoList =  travelInfoRepository.findAllByCountry_CountryidAndCity_Cityid(countryid,cityid);
        List<TravelInfoDTO>  travelInfoDTOsearchList = new ArrayList<>();
        for (int i=0; i<travelInfoList.size(); i++){
            Country country = countryRepository.findByCountryid(travelInfoList.get(i).getCountry().getCountryid());
            City city =  cityRepository.findByCityid(travelInfoList.get(i).getCity().getCityid());
            Travelimg travelimg = travelImgRepository.findAllByTravelInfo_TidAndRepimYn(travelInfoList.get(i).getTid(),"Y");
            TravelInfoDTO dto = TravelInfoDTO.of(travelInfoList.get(i));
            dto.setCountry(country);
            dto.setCity(city);
            dto.setImgUrl(travelimg.getImgUrl());
            travelInfoDTOsearchList.add(i,dto);
            System.out.println(travelInfoDTOsearchList.get(i));
        }
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), travelInfoDTOsearchList.size());
        final Page<TravelInfoDTO> page = new PageImpl<>(travelInfoDTOsearchList.subList(start, end), pageable, travelInfoDTOsearchList.size());
        return page;
    }

    public Page<TravelInfoDTO> getsearchcitylist(Pageable pageable,Long countryid){
        List<TravelInfo> travelInfoList =  travelInfoRepository.findAllByCountry_Countryid(countryid);
        List<TravelInfoDTO>  travelInfoDTOsearchcityList = new ArrayList<>();
        for (int i=0; i<travelInfoList.size(); i++){
            Country country = countryRepository.findByCountryid(travelInfoList.get(i).getCountry().getCountryid());
            City city =  cityRepository.findByCityid(travelInfoList.get(i).getCity().getCityid());
            Travelimg travelimg = travelImgRepository.findAllByTravelInfo_TidAndRepimYn(travelInfoList.get(i).getTid(),"Y");
            TravelInfoDTO dto = TravelInfoDTO.of(travelInfoList.get(i));
            dto.setCountry(country);
            dto.setCity(city);
            travelInfoDTOsearchcityList.add(i,dto);
            dto.setImgUrl(travelimg.getImgUrl());
            System.out.println(travelInfoDTOsearchcityList.get(i));
        }
        final int start = (int)pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), travelInfoDTOsearchcityList.size());
        final Page<TravelInfoDTO> page = new PageImpl<>(travelInfoDTOsearchcityList.subList(start, end), pageable, travelInfoDTOsearchcityList.size());
        return page;
    }

    public TravelImgDTO getRepimYn(Long tid, String repimYny){
        Travelimg travelimg =  travelImgRepository.findAllByTravelInfo_TidAndRepimYn(tid,repimYny);
        TravelImgDTO travelImgDTO = TravelImgDTO.of(travelimg);

        return travelImgDTO;
    }

}
