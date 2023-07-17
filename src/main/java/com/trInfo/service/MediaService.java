package com.trInfo.service;

import com.trInfo.dto.MediaFormDTO;
import com.trInfo.dto.MediaImgDTO;
import com.trInfo.entity.City;
import com.trInfo.entity.Country;
import com.trInfo.entity.MediaImg;
import com.trInfo.entity.MediaInfo;
import com.trInfo.repository.CityRepository;
import com.trInfo.repository.CountryRepository;
import com.trInfo.repository.MediaImgRepository;
import com.trInfo.repository.MediaRepository;
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
public class MediaService {

    private final MediaImgRepository mediaImgRepository;
    private final MediaImgService mediaImgService;
    private final MediaRepository mediaRepository;
    private final CityRepository cityRepository;
    private final CountryRepository countryRepository;

    public Long saveMedia(MediaFormDTO mediaFormDTO,
                          List<MultipartFile> mediaImgFileList) throws Exception {
        Country country = countryRepository.findByCountryid(mediaFormDTO.getCountryid());
        City city = cityRepository.findByCityid(mediaFormDTO.getCityid());
        mediaFormDTO.setCountry(country);
        mediaFormDTO.setCity(city);
        // 상품 등록
        MediaInfo mediainfo = mediaFormDTO.createMediaInfo();
        mediaRepository.save(mediainfo);
        // 이미지 등록
        for (int i = 0; i < mediaImgFileList.size(); i++) {
            if(mediaImgFileList.get(i).getOriginalFilename() !=null){
                MediaImg mediaImg = new MediaImg();
                mediaImg.setMediainfo(mediainfo);
                if (i == 0)
                    mediaImg.setRepImgYn("Y");
                else
                    mediaImg.setRepImgYn("N");
                mediaImgService.saveMediaImg(mediaImg, mediaImgFileList.get(i));
            }
        }

        return mediainfo.getEid();
    }

        @Transactional(readOnly = true)
        public MediaFormDTO getModify(Long eid) {

            List<MediaImg> mediaImgList =
                    mediaImgRepository.findAllByMediainfo_EidOrderByEiidAsc(eid);
            List<MediaImgDTO> mediaImgDTOList = new ArrayList<>();
            for (MediaImg mediaImg : mediaImgList) {
                MediaImgDTO mediaImgDTO = MediaImgDTO.of(mediaImg);
                mediaImgDTOList.add(mediaImgDTO);
            }
            MediaInfo media_info = mediaRepository.findById(eid).orElseThrow(EntityNotFoundException::new);
            MediaFormDTO mediaFormDTO = MediaFormDTO.of(media_info);

            mediaFormDTO.setCountryid(media_info.getCountry().getCountryid());
            mediaFormDTO.setCityid(media_info.getCity().getCityid());

            mediaFormDTO.setCity(media_info.getCity());
            mediaFormDTO.setCountry(media_info.getCountry());


            mediaFormDTO.setMediaImgDTOList(mediaImgDTOList);
            return mediaFormDTO;
        }

        public Long updateMedia(MediaFormDTO mediaFormDTO, List<MultipartFile> mediaImgFileList) throws Exception {

            Country country = countryRepository.findByCountryid(mediaFormDTO.getCountryid());
            City city = cityRepository.findByCityid(mediaFormDTO.getCityid());
            mediaFormDTO.setCountry(country);
            mediaFormDTO.setCity(city);

            // 상품 수정
            MediaInfo mediaInfo = mediaRepository.findById(mediaFormDTO.getEid())
                    .orElseThrow(EntityNotFoundException::new);
            mediaInfo.updateMedia(mediaFormDTO);
            List<Long> mediaImgIds = mediaFormDTO.getMediaImgIds();

            // 이미지 등록
            for (int i = 0; i < mediaImgFileList.size(); i++) {
                mediaImgService.updateMediaImg(mediaImgIds.get(i), mediaImgFileList.get(i));
            }

            return mediaInfo.getEid();
        }

        public void deleteMedia(Long eid) {
        //삭제
            if (mediaRepository.findById(eid).orElse(null) != null) {
                mediaRepository.deleteById(eid);
            }
        }



}
