package com.trInfo.service;

import com.trInfo.entity.MediaInfo;
import com.trInfo.repository.CityRepository;
import com.trInfo.repository.CountryRepository;
import com.trInfo.repository.MediaImgRepository;
import com.trInfo.repository.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MediaInfoService {
    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private MediaImgRepository mediaImgRepository;


    public List<MediaInfo> getList(){

        return mediaRepository.findAll();
    }



    public Page<MediaInfo> medialist(Pageable pageable) {

        return mediaRepository.findAll(pageable);
    }
//    }   기존에 있던 새끼

//    public List<MediaInfoDTO> getAllList(){
//        List<MediaInfoDTO> mediaInfoList =  mediaRepository.findAll();
//        List<MediaInfoDTO>  mediaInfoDTOList = new ArrayList<>();
//        List<MediaImg> mediaimgList = new ArrayList<>();
//        for (int i=0; i<mediaInfoList.size(); i++){
//            Country country = countryRepository.findByCountryid(mediaInfoList.get(i).getCountryid());
//            City city =  cityRepository.findByCityid(mediaInfoList.get(i).getCityid());
//            MediaInfoDTO dto = MediaInfoDTO.of(mediaInfoList.get(i));
//            dto.setCountry(country);
//            dto.setCity(city);
//            dto.setImgUrl(mediaImg.getImgUrl());
//            mediaInfoDTOList.add(i,dto);
//            System.out.println(mediaInfoDTOList.get(i));
//
//        }
//        return mediaInfoDTOList;
//    }
//    public List<MediaInfoDTO> getsearchlist(Long countryid, Long cityid){
//        List<MediaInfo> medialInfoList =  mediaRepository.findAllByCountry_CountryidAndCity_Cityid(countryid,cityid);
//        List<MediaInfoDTO>  travelInfoDTOsearchList = new ArrayList<>();
//        for (int i=0; i<mediaInfoList.size(); i++){
//            Country country = countryRepository.findByCountryid(mediaInfoList.get(i).getCountry().getCountryid());
//            City city =  cityRepository.findByCityid(mediaInfoList.get(i).getCity().getCityid());
//            Travelimg travelimg = mediaImgRepository.findAllByTravelInfo_TidAndRepimYn(mediaInfoList.get(i).getTid(),"Y");
//            TravelInfoDTO dto = TravelInfoDTO.of(travelInfoList.get(i));
//            dto.setCountry(country);
//            dto.setCity(city);
//            dto.setImgUrl(travelimg.getImgUrl());
//            travelInfoDTOsearchList.add(i,dto);
//            System.out.println(travelInfoDTOsearchList.get(i));
//        }
//        return travelInfoDTOsearchList;
//    }
//
//    public List<TravelInfoDTO> getsearchcitylist(Long countryid){
//        List<TravelInfo> travelInfoList =  travelInfoRepository.findAllByCountry_Countryid(countryid);
//        List<TravelInfoDTO>  travelInfoDTOsearchcityList = new ArrayList<>();
//        for (int i=0; i<travelInfoList.size(); i++){
//            Country country = countryRepository.findByCountryid(travelInfoList.get(i).getCountry().getCountryid());
//            City city =  cityRepository.findByCityid(travelInfoList.get(i).getCity().getCityid());
//            Travelimg travelimg = travelImgRepository.findAllByTravelInfo_TidAndRepimYn(travelInfoList.get(i).getTid(),"Y");
//            TravelInfoDTO dto = TravelInfoDTO.of(travelInfoList.get(i));
//            dto.setCountry(country);
//            dto.setCity(city);
//            travelInfoDTOsearchcityList.add(i,dto);
//            dto.setImgUrl(travelimg.getImgUrl());
//            System.out.println(travelInfoDTOsearchcityList.get(i));
//        }
//        return travelInfoDTOsearchcityList;
//    }



}


