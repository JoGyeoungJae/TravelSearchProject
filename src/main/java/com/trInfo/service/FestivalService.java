package com.trInfo.service;

import com.trInfo.dto.FestivalFormDto;
import com.trInfo.dto.FestivalImgDto;
import com.trInfo.entity.City;
import com.trInfo.entity.Country;
import com.trInfo.entity.FestivalImg;
import com.trInfo.entity.FestivalInfo;
import com.trInfo.repository.CityRepository;
import com.trInfo.repository.CountryRepository;
import com.trInfo.repository.FestivalImgRepository;
import com.trInfo.repository.FestivalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class FestivalService {

    private final FestivalRepository festivalRepository;
    private final FestivalImgService festivalImgService;
    private final FestivalImgRepository festivalImgRepository;
    private final CountryRepository countryRepository;
    private final CityRepository cityRepository;

    public Long saveFestival(FestivalFormDto festivalFormDto,
                             List<MultipartFile> festivalImgFileList) throws Exception {
        Country country = countryRepository.findByCountryid(festivalFormDto.getCountryid());
        City city = cityRepository.findByCityid(festivalFormDto.getCityid());
        festivalFormDto.setCountry(country);
        festivalFormDto.setCity(city);
        // 상품 등록
        FestivalInfo festivalinfo = festivalFormDto.createFestivalinfo();

        festivalRepository.save(festivalinfo);
        // 이미지 등록
        for (int i = 0; i < festivalImgFileList.size(); i++) {
            if(festivalImgFileList.get(i).getOriginalFilename() !=null){
                FestivalImg festivalImg = new FestivalImg();
                festivalImg.setFestivalinfo(festivalinfo);
                if (i == 0)
                    festivalImg.setRepImgYn("Y");
                else
                    festivalImg.setRepImgYn("N");
                festivalImgService.saveFestivalImg(festivalImg, festivalImgFileList.get(i));
            }
        }

        return festivalinfo.getFid();
    }

    @Transactional(readOnly = true)
    public FestivalFormDto getFestivalDtl(Long fid) {

        List<FestivalImg> festivalImgList =
                festivalImgRepository.findAllByFestivalinfo_FidOrderByFiidAsc(fid);
        List<FestivalImgDto> festivalImgDtoList = new ArrayList<>();
        for (FestivalImg festivalImg : festivalImgList) {
            FestivalImgDto festivalImgDto = FestivalImgDto.of(festivalImg);
            festivalImgDtoList.add(festivalImgDto);
        }
        FestivalInfo festival_info = festivalRepository.findById(fid).orElseThrow(EntityNotFoundException::new);
        FestivalFormDto festivalFormDto = FestivalFormDto.of(festival_info);

        festivalFormDto.setCountryid(festival_info.getCountry().getCountryid());
        festivalFormDto.setCityid(festival_info.getCity().getCityid());

        festivalFormDto.setCity(festival_info.getCity());
        festivalFormDto.setCountry(festival_info.getCountry());


        festivalFormDto.setFestivalImgDtoList(festivalImgDtoList);
        return festivalFormDto;
    }

    public Long updateFestival(FestivalFormDto festivalFormDto, List<MultipartFile> festivalImgFileList) throws Exception {

        Country country = countryRepository.findByCountryid(festivalFormDto.getCountryid());
        City city = cityRepository.findByCityid(festivalFormDto.getCityid());
        festivalFormDto.setCountry(country);
        festivalFormDto.setCity(city);

        // 상품 수정
        FestivalInfo festivalInfo = festivalRepository.findById(festivalFormDto.getFid())
                .orElseThrow(EntityNotFoundException::new);
        festivalInfo.updateFestival(festivalFormDto);
        List<Long> festivalImgIds = festivalFormDto.getFestivalImgIds();

        // 이미지 등록
        for (int i = 0; i < festivalImgFileList.size(); i++) {
            festivalImgService.updateFestivalImg(festivalImgIds.get(i), festivalImgFileList.get(i));
        }

        return festivalInfo.getFid();
    }

//    public void deleteFestival(Long fid){
//        FestivalInfo festivalInfo = festivalRepository.findById(fid)
//                .orElseThrow(EntityNotFoundException::new);
//        festivalRepository.delete(festivalInfo);
//    }

    public void deleteFestival(Long fid) {
        if (festivalRepository.findById(fid).orElse(null) != null) {
            festivalRepository.deleteById(fid);
        }
    }

}
