package com.trInfo.service;

import com.trInfo.entity.FestivalImg;
import com.trInfo.repository.FestivalImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class FestivalImgService {

    @Value("${ImgLocation}")
    private String itemImgLocation;

    private final FestivalImgRepository festivalImgRepository;

    private final FileService fileService;

    public void saveFestivalImg(FestivalImg festivalImg, MultipartFile festivalImgFile)
            throws Exception{
        String oriImgName = festivalImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        // 파일 업로드
        if (!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(itemImgLocation, oriImgName,
                    festivalImgFile.getBytes());
            imgUrl = "/images/photo/" + imgName;
        }

        // 상품 이미지 정보 저장
        festivalImg.updateFestivalImg(oriImgName, imgName, imgUrl);
        festivalImgRepository.save(festivalImg);
    }

    public void updateFestivalImg(Long festivalImgId, MultipartFile festivalImgFile)
            throws Exception{
        if (!festivalImgFile.isEmpty()){
            FestivalImg savedFestivalImg = festivalImgRepository.findById(festivalImgId)
                    .orElseThrow(EntityNotFoundException :: new);
            // 기존 이미지 파일 삭제
            if (!StringUtils.isEmpty(savedFestivalImg.getImgName())){
                fileService.deleteFile(itemImgLocation+"/"+savedFestivalImg.getImgName());
            }

            String oriImgName = festivalImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(itemImgLocation, oriImgName, festivalImgFile.getBytes());
            String imgUrl = "/images/photo/" + imgName;
            savedFestivalImg.updateFestivalImg(oriImgName, imgName, imgUrl);
        }
    }
}
