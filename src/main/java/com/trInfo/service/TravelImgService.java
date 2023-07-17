package com.trInfo.service;

import com.trInfo.dto.TravelImgDTO;
import com.trInfo.dto.TravelInfoDTO;
import com.trInfo.entity.City;
import com.trInfo.entity.Country;
import com.trInfo.entity.TravelInfo;
import com.trInfo.entity.Travelimg;
import com.trInfo.repository.TravelImgRepository;
import com.trInfo.repository.TravelImglistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TravelImgService {

    @Value("${ImgLocation}")
    private String travelImgLocation;

    private final TravelImgRepository travelImgRepository;

    private final FileService fileService;

    private final TravelImglistRepository travelImglistRepository;

    public void saveTravel_img(Travelimg travelImg, MultipartFile travelImgFile) throws Exception{
        String oriImgName = travelImgFile.getOriginalFilename();
        String imgName="";
        String imgUrl="";

        //파일업로드
        if(!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(travelImgLocation, oriImgName, travelImgFile.getBytes());
            imgUrl = "/images/photo/" + imgName;
        }

        //여행 이미지 정보 저장
        travelImg.updateTravelimg(oriImgName, imgName, imgUrl);
        travelImgRepository.save(travelImg);
    }

    public void updateTravelimg(Long travelImgId, MultipartFile travelImgFile) throws Exception{
        if(!travelImgFile.isEmpty()){
            Travelimg savedTravel_img = travelImgRepository.findById(travelImgId).orElseThrow(EntityNotFoundException::new);

            //기존 이미지 파일 삭제
            if (!StringUtils.isEmpty(savedTravel_img.getImgName())){
                fileService.deleteFile(travelImgLocation + "/" + savedTravel_img.getImgName());
            }

            String oriImgName = travelImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(travelImgLocation, oriImgName, travelImgFile.getBytes());
            String imgUrl = "/images/photo/" + imgName;
            savedTravel_img.updateTravelimg(oriImgName, imgName, imgUrl);
        }
    }

    public List<TravelImgDTO> getsearchImglist(Long tid, String repimYn){
        List<Travelimg> travelimgList =  travelImglistRepository.findAllByTravelInfo_TidAndRepimYn(tid,repimYn);
        List<TravelImgDTO> travelImgDTOList = new ArrayList<>();
        for (int i=0; i<travelimgList.size(); i++){
            TravelImgDTO dto = TravelImgDTO.of(travelimgList.get(i));
            dto.setOriImgName(dto.getOriImgName().substring(0,dto.getOriImgName().length()-4));
            travelImgDTOList.add(i,dto);
        }
        return travelImgDTOList;
    }
}
