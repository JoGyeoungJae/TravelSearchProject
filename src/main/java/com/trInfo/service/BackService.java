package com.trInfo.service;

import com.trInfo.dto.BackDTO;
import com.trInfo.entity.*;
import com.trInfo.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class BackService {
    @Autowired
    private  BackRepository backRepository;
    @Autowired
    private  MemberRepository memberRepository;
    @Autowired
    private FestivalRepository festivalRepository;
    @Autowired
    private FestivalImgRepository festivalImgRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private  TravelInfoRepository travelInfoRepository;
    @Autowired
    private TravelImgRepository travelImgRepository;

    public void saveMF(BackDTO backDTO) {
        Member member = memberRepository.findByMid(backDTO.getMid());

        FestivalInfo festivalInfo = festivalRepository.findByFid(backDTO.getFid());
        backDTO.setMember(member);
        backDTO.setFestivalInfo(festivalInfo);
        Back back = backDTO.createBack();
        backRepository.save(back);
    }
    public void saveMT(BackDTO backDTO) {
        Member member = memberRepository.findByMid(backDTO.getMid());

        TravelInfo travelInfo = travelInfoRepository.findByTid(backDTO.getTid());
        backDTO.setMember(member);
        backDTO.setTravelInfo(travelInfo);
        Back back = backDTO.createBack();
        backRepository.save(back);
    }

    public Member getbackmemberlist(String username){


        return memberRepository.findByEmail(username);
    }
    public List<BackDTO> getFestivalbacklist(Long mid){
        List<Back> backList =backRepository.findAllByMember_Mid(mid);
        List<BackDTO> backDTOList = new ArrayList<>();

        for (int i = 0; i < backList.size(); i++) {
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@"+i+"@@@@@@@@"+backList.get(i).getFestivalInfo());
            System.out.println(backList.get(i).getFestivalInfo() !=null);
            if(backList.get(i).getFestivalInfo() !=null){
                BackDTO backDTO = BackDTO.of(backList.get(i));
                FestivalImg festivalImg =festivalImgRepository.findAllByFestivalinfo_FidAndRepImgYn(backList.get(i).getFestivalInfo().getFid(),"Y");
                Country country = countryRepository.findByCountryid(backList.get(i).getFestivalInfo().getCountry().getCountryid());
                City city = cityRepository.findByCityid(backList.get(i).getFestivalInfo().getCity().getCityid());

                backDTO.setMember(backList.get(i).getMember());
                backDTO.setFestivalInfo(backList.get(i).getFestivalInfo());
                backDTO.setImgUrl(festivalImg.getImgUrl());
                backDTO.setCountry(country);
                backDTO.setCity(city);
                backDTOList.add(backDTO);
            }
        }

        return backDTOList;
    }
    public List<BackDTO> gettravelbacklist(Long mid){
        List<Back> backList =backRepository.findAllByMember_Mid(mid);
        List<BackDTO> backDTOList = new ArrayList<>();
        for (int i = 0; i < backList.size(); i++) {
            if(backList.get(i).getTravelInfo() !=null){

                BackDTO backDTO = BackDTO.of(backList.get(i));
                Travelimg travelimg =travelImgRepository.findAllByTravelInfo_TidAndRepimYn(backList.get(i).getTravelInfo().getTid(),"Y");
                Country country = countryRepository.findByCountryid(backList.get(i).getTravelInfo().getCountry().getCountryid());
                City city = cityRepository.findByCityid(backList.get(i).getTravelInfo().getCity().getCityid());

                backDTO.setMember(backList.get(i).getMember());
                backDTO.setTravelInfo(backList.get(i).getTravelInfo());
                backDTO.setImgUrl(travelimg.getImgUrl());
                backDTO.setCountry(country);
                backDTO.setCity(city);
                backDTOList.add(backDTO);
            }
        }

        return backDTOList;
    }
    public void del(Long bkid){
        backRepository.deleteById(bkid);
    }
}
