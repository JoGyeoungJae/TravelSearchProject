package com.trInfo.Controller;

import com.trInfo.dto.BackDTO;
import com.trInfo.dto.MediaFormDTO;
import com.trInfo.entity.MediaInfo;
import com.trInfo.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/media")
@Log4j2
@RequiredArgsConstructor
public class MediaInfoController {

    private final MediaService mediaService;

    @Autowired
    MediaInfoService mediaInfoService;

    @Autowired
    CountryService countryService;

    @Autowired
    MemberService memberService;

    @Autowired
    BackService backService;

    @Autowired
    MediaImgService mediaImgService;

    @GetMapping("/medialist")
    public void medialist(@PageableDefault(size = 8) Pageable pageable, Model model) {
        Page<MediaInfo> medialist = mediaInfoService.medialist(pageable);
        List<MediaInfo> ml = medialist.getContent();
        List<String> urls = new ArrayList<>();

        Map<MediaInfo, String> totalList = new HashMap<>();
        for(MediaInfo m : ml) {
            String fileURL = mediaImgService.getURL(m.getEid());
            totalList.put(m, fileURL);
//            totalList.add(temptotal);
        }

        model.addAttribute("mediaAll", totalList);

        model.addAttribute("urls", urls);
        model.addAttribute("medialist", medialist);

    }


    @GetMapping("/mediaRegister")
    public String mediaRegister(Model model) {
        model.addAttribute("countryList", countryService.getcountrylist());
        model.addAttribute("mediaFormDTO", new MediaFormDTO());
        return "admin/media/mediaRegister";
    }

    @PostMapping("/mediaRegister")
    public String MediaNew(@ModelAttribute("mediaFormDTO") @Valid MediaFormDTO MediaFormDTO,
                           BindingResult bindingResult, Model model, @RequestParam("mediaImgFile") List<MultipartFile>
                                   mediaImgFileList) {
        if (bindingResult.hasErrors()) {
            return "admin/media/mediaRegister";
        }

        if (mediaImgFileList.get(0).isEmpty() && MediaFormDTO.getEid() == null) {
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "admin/media/mediaRegister";
        }

        try {
            mediaService.saveMedia(MediaFormDTO, mediaImgFileList);

        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
            return "admin/media/mediaRegister";
        }

        return "redirect:/";
    }

    @GetMapping("/mediaRegister/{eid}")
    public String Modify(@PathVariable("eid") Long eid, Model model) {
        try {
            model.addAttribute("countryList", countryService.getcountrylist());
            MediaFormDTO mediaFormDTO = mediaService.getModify(eid);
            model.addAttribute("mediaFormDTO", mediaFormDTO);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "존재하지 않는 상품 입니다.");
            model.addAttribute("mediaFormDTO", new MediaFormDTO());
            return "admin/media/mediaRegister";
        }

        return "admin/media/mediaRegister";
    }

    @PostMapping("/mediaRegister/{eid}")
    public String mediaUpdate(@Valid MediaFormDTO mediaFormDTO, BindingResult bindingResult,
                              @RequestParam("mediaImgFile") List<MultipartFile>
                                      mediaImgFileList, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/media/mediaRegister";
        }

        if (mediaImgFileList.get(0).isEmpty() && mediaFormDTO.getEid() == null) {
            model.addAttribute("errorMessage", "첫번째 사진 이미지는 필수 입력 값 입니다.");
            return "admin/media/mediaRegister";
        }

        try {
            mediaService.updateMedia(mediaFormDTO, mediaImgFileList);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "사진 수정 중 에러가 발생하였습니다.");
            return "admin/media/mediaRegister";
        }

        return "redirect:/";
    }

    @GetMapping("/modify")
    public String Modify(Model model, @RequestParam("eid") Long eid) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+principal);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+principal.equals("anonymousUser"));
        if(!principal.equals("anonymousUser")){
            UserDetails userDetails = (UserDetails)principal;
            String username = ((UserDetails) principal).getUsername();
            model.addAttribute("member",memberService.getMember(username));
        }
        model.addAttribute("backdto", new BackDTO());
        MediaFormDTO mediaFormDTO = mediaService.getModify(eid);
        model.addAttribute("media", mediaFormDTO);

        return "admin/media/Modify";
    }

    @PostMapping("/modify")
    public String ModifyPOST(@ModelAttribute("backdto") @Valid BackDTO backdto) {
        System.out.println("ModifyTest@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + backdto);
        backService.saveMF(backdto);
        return "media/medialist";
    }

    @PostMapping("/del")
    public String deleteMedia(Long eid) {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~" + eid);
        mediaService.deleteMedia(eid);
        return "redirect:/media/medialist";
    }

//    @PostMapping("/remove/{eid}")
//    public String deletePost(@PathVariable("eid") Long eid){
//        log.info("delete " + eid);
//        mediaService.delete(eid);
//        return "redirect:/media/medialist";
//    }


}


