package com.trInfo.Controller;

import com.trInfo.dto.BackDTO;
import com.trInfo.dto.TravelInfoDTO;
import com.trInfo.entity.Board;
import com.trInfo.entity.Member;
import com.trInfo.entity.TravelInfo;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/travel")
public class TravelInfoController {

    @Autowired
    TravelService travelService;

    @Autowired
    private CountryService countryService;

    @Autowired
    TravelInfoService travelInfoService;

    @Autowired
    TravelImgService travelImgService;

    @Autowired
    MemberService memberService;

    @Autowired
    BackService backService;
    @GetMapping("/travellist")
    public void travellist(@PageableDefault(size = 8) Pageable pageable, Model model, Long countryid, Long cityid){

        model.addAttribute("countryList", countryService.getcountrylist());
        if(countryid ==null && cityid ==null){
            Page<TravelInfoDTO> travellist = travelInfoService.getAllList(pageable);
            model.addAttribute("travellist",travellist);
        }else if(countryid ==0 && cityid ==0){
            Page<TravelInfoDTO> travellist = travelInfoService.getAllList(pageable);
            model.addAttribute("travellist",travellist);
        }else if(countryid ==0 && cityid ==null){
            Page<TravelInfoDTO> travellist = travelInfoService.getAllList(pageable);
            model.addAttribute("travellist",travellist);
        }else if(countryid !=0 && cityid ==0){
            Page<TravelInfoDTO> travellist = travelInfoService.getsearchcitylist(pageable,countryid);
            model.addAttribute("travellist",travellist);
        }else{
            Page<TravelInfoDTO> travellist = travelInfoService.getsearchlist(pageable,countryid,cityid);
            model.addAttribute("travellist",travellist);
        }
//        model.addAttribute("travellist", travelInfoService.getList());
    }

    @GetMapping("/travelDtl")
    public void about(Long tid, Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+principal);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+principal.equals("anonymousUser"));
        if(!principal.equals("anonymousUser")){
            UserDetails userDetails = (UserDetails)principal;
            String username = ((UserDetails) principal).getUsername();
            model.addAttribute("member",memberService.getMember(username));
        }
        String repimYny="Y";
        String repimYnn="N";
        model.addAttribute("travelrepimYny",travelInfoService.getRepimYn(tid,repimYny));
        model.addAttribute("travelInfo", travelService.getTravelInfoDtl(tid));
        model.addAttribute("travelDtllist",travelImgService.getsearchImglist(tid,repimYnn));
    }
    @PostMapping("/travelDtl")
    public String travelDtlPOST(@ModelAttribute("backdto")  @Valid BackDTO backdto, Long tid) {
        System.out.println("festivalDtlPOST@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+backdto);
        backService.saveMT(backdto);

        return "redirect:/travel/travelDtl?tid="+tid;
    }

    @GetMapping("/travelInfoForm")
    public String TravelInfoForm(Model model){
        //나라 목록
        model.addAttribute("countryList", countryService.getcountrylist());
        model.addAttribute("travelInfoDTO", new TravelInfoDTO());
        return "travel/travelInfoForm";
    }

    @PostMapping("/travelInfoForm")
    public String TravelInfoForm(@Valid TravelInfoDTO travelInfoDTO, BindingResult bindingResult, Model model,
                                 @RequestParam("travelImgFile")List<MultipartFile> travelImgFileList){

        if(bindingResult.hasErrors()){
            return "travel/travelInfoForm";
        }
        if (travelImgFileList.get(0).isEmpty() && travelInfoDTO.getTid()==null){
            model.addAttribute("errorMessage", "첫번째 여행 이미지는 필수 입력 값 입니다.");
            return "travel/travelInfoForm";
        }
        try{
            travelService.saveTravelInfo(travelInfoDTO, travelImgFileList);
        }catch (Exception e){
            model.addAttribute("errorMessage", "여행이미지 등록 중 에러가 발생하였습니다.");
            return "travel/travelInfoForm";
        }

        return "redirect:/";
    }


    @GetMapping("/updateTravelInfo/{travelInfoId}")
    public String travelInfoDtl(@PathVariable("travelInfoId") Long travelInfoId, Model model){
        model.addAttribute("countryList", countryService.getcountrylist());

        try{
            TravelInfoDTO travelInfoDTO = travelService.getTravelInfoDtl(travelInfoId);
            model.addAttribute("travelInfoDTO", travelInfoDTO);
        } catch (EntityNotFoundException e){
            model.addAttribute("errorMessage", "존재하지 않는 여행지입니다.");
            model.addAttribute("travelInfoDto", new TravelInfoDTO());
            return "travel/travelInfoForm";
        }
        return "travel/travelInfoForm";
    }


    @PostMapping("/updateTravelInfo/{travelInfoId}")
    public String updateTravelInfo(@Valid TravelInfoDTO travelInfoDTO, BindingResult bindingResult,
                                   @RequestParam("travelImgFile") List<MultipartFile> travelImgFileList, Model model){

        if (bindingResult.hasErrors()){
            return "travel/travelInfoForm";
        }
        if (travelImgFileList.get(0).isEmpty() && travelInfoDTO.getTid() == null){
            model.addAttribute("errorMessage", "첫번째 여행이미지는 필수 입력 값 입니다.");
            return "travel/travelInfoForm";
        }

        try {
            travelService.updateTravelInfo(travelInfoDTO, travelImgFileList);
        } catch (Exception e){
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
            return "travel/travelInfoForm";
        }

        return "redirect:/";
    }

    @PostMapping({"/deleteTravelInfo/{travelInfoId}", "/del"})
    public String deleteTravelInfo(Long tid){
        travelService.deleteTravelInfo(tid);

        return "redirect:/travel/travellist";
    }



//    @GetMapping("/{tid}")
//    public String travelinfoManage(TravelSearchDTO travelSearchDTO, @PathVariable("page")Optional<Integer> page, Model model){
//        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0,3);
//        Page<TravelInfo> travelInfos = travelService.getAdminTravelInfoPage(travelSearchDTO, pageable);
//        model.addAttribute("travelInfos", travelInfos);
//        model.addAttribute("travelSearchDTO", travelSearchDTO);
//        model.addAttribute("maxPage", 5);
//        return "/TravelInfoForm";
//    }
}
