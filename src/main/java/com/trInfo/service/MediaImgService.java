package com.trInfo.service;

import com.trInfo.entity.MediaImg;
import com.trInfo.repository.MediaImgRepository;
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
public class MediaImgService {

    @Value("${ImgLocation}")
    private String itemImgLocation;
    private final MediaImgRepository mediaImgRepository;
    private final FileService fileService;

    public void saveMediaImg(MediaImg mediaImg, MultipartFile mediaImgFile)
            throws Exception{
        String oriImgName = mediaImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        // 파일 업로드
        if (!StringUtils.isEmpty(oriImgName)){
            imgName = fileService.uploadFile(itemImgLocation, oriImgName,
                    mediaImgFile.getBytes());
            imgUrl = "/images/photo/" + imgName;
        }


        // 상품 이미지 정보 저장
        mediaImg.updateMediaImg(oriImgName, imgName, imgUrl);
        mediaImgRepository.save(mediaImg);



    }

    public String getURL(Long eid) {
        return mediaImgRepository.findRepUrl(eid);
    }

    public void updateMediaImg(Long mediaImgId, MultipartFile mediaImgFile)
            throws Exception {
        if (!mediaImgFile.isEmpty()) {
            MediaImg savedMediaImg = mediaImgRepository.findById(mediaImgId)
                    .orElseThrow(EntityNotFoundException :: new);

            // 기존 이미지 파일 삭제
            if (!StringUtils.isEmpty(savedMediaImg.getImgName())) {
                fileService.deleteFile(itemImgLocation + "/" + savedMediaImg.getImgName());
            }

            String oriImgName = mediaImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(itemImgLocation, oriImgName, mediaImgFile.getBytes());
            String imgUrl = "/images/photo/" + imgName;
            savedMediaImg.updateMediaImg(oriImgName, imgName, imgUrl);
        }
    }

}
