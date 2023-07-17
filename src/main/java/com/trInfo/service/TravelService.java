package com.trInfo.service;

import com.trInfo.dto.TravelImgDTO;
import com.trInfo.dto.TravelInfoDTO;
import com.trInfo.dto.TravelSearchDTO;
import com.trInfo.entity.City;
import com.trInfo.entity.Country;
import com.trInfo.entity.TravelInfo;
import com.trInfo.entity.Travelimg;
import com.trInfo.repository.CityRepository;
import com.trInfo.repository.CountryRepository;
import com.trInfo.repository.TravelImgRepository;
import com.trInfo.repository.TravelInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TravelService {

    private final TravelInfoRepository travelInfoRepository;
    private final TravelImgService travelImgService;
    private final TravelImgRepository travelImgRepository;
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;

    public Long saveTravelInfo(TravelInfoDTO travelInfoDTO, List<MultipartFile> travelImgFileList) throws Exception {
        Country country = countryRepository.findByCountryid(travelInfoDTO.getCountryid());
        City city = cityRepository.findByCityid(travelInfoDTO.getCityid());
        travelInfoDTO.setCountry(country);
        travelInfoDTO.setCity(city);
        //상품등록
        TravelInfo travelInfo = travelInfoDTO.createTravelinfo();
        travelInfoRepository.save(travelInfo);
        //이미지등록
        for (int i = 0; i < travelImgFileList.size(); i++) {
            Travelimg travelImg = new Travelimg();
            travelImg.setTravelInfo(travelInfo);
            if (i == 0)
                travelImg.setRepimYn("Y");
            else
                travelImg.setRepimYn("N");
            travelImgService.saveTravel_img(travelImg, travelImgFileList.get(i));
        }
        return travelInfo.getTid();
    }

    @Transactional(readOnly = true)
    public TravelInfoDTO getTravelInfoDtl(Long travelInfoId){

        //여행 id를 기반으로 여행 이미지 엔티티 객체 가져옴
        List<Travelimg> travelImgList = travelImgRepository.findAllByTravelInfo_Tid(travelInfoId);
        System.out.println(travelInfoId);
        System.out.println(travelImgList.size());
        for (int i = 0; i < travelImgList.size(); i++) {

            System.out.println("@@@@@@@@@@"+travelImgList.get(i).getOriImgName());
        }
        //여행 이미지 DTO 객체를 담을 그릇 생성
        List<TravelImgDTO> travelImgDTOList = new ArrayList<>();

        //여행 이미지 엔티티 객체를 여행 이미지 DTO 객체로 변환
        for (Travelimg travelImg : travelImgList){
            TravelImgDTO travelImgDTO = TravelImgDTO.of(travelImg);
            travelImgDTOList.add(travelImgDTO);
        }

        //여행 id를 기반으로 여행 엔티티 객체 가져옴
        TravelInfo travelInfo = travelInfoRepository.findById(travelInfoId).orElseThrow(EntityNotFoundException::new);

        //여행 엔티티 객체를 여행 DTO 객체로 변환
        TravelInfoDTO travelInfoDTO = TravelInfoDTO.of(travelInfo);
        travelInfoDTO.setTravelImgDTOList(travelImgDTOList);

        return travelInfoDTO;
    }


    public Long updateTravelInfo(TravelInfoDTO travelInfoDTO, List<MultipartFile> travelImgFileList) throws Exception{

        //상품수정
        TravelInfo travelInfo = travelInfoRepository.findById(travelInfoDTO.getTid()).orElseThrow(EntityNotFoundException::new);
        travelInfo.updateTravelInfo(travelInfoDTO);

        List<Long> travelImgIds = travelInfoDTO.getTravelImgIds();

        //이미지 등록

        for(int i=0; i<travelImgFileList.size(); i++){
            travelImgService.updateTravelimg(travelImgIds.get(i), travelImgFileList.get(i));
        }
        return travelInfo.getTid();
    }

    public void deleteTravelInfo(Long tid){

        //상품삭제
        if(travelInfoRepository.findById(tid).orElse(null) != null){
            travelInfoRepository.deleteById(tid);
        }
    }

//    @Transactional(readOnly = true)
//    public Page<TravelInfo> getAdminTravelInfoPage(TravelSearchDTO travelSearchDTO, Pageable pageable){
//        return travelInfoRepository.getAdminTravelInfoPage(travelSearchDTO, pageable);
//    }

}
