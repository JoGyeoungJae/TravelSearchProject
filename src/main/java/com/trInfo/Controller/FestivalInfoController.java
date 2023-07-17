package com.trInfo.Controller;

import com.trInfo.dto.BackDTO;
import com.trInfo.dto.FestivalFormDto;
import com.trInfo.dto.TravelInfoDTO;
import com.trInfo.entity.FestivalInfo;
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
import java.util.List;

@Controller
@RequestMapping("/festival")
@Log4j2
@RequiredArgsConstructor
public class FestivalInfoController {

    private final FestivalService festivalService;

    @Autowired
    private CountryService countryService;
    @Autowired
    FestivalInfoService festivalInfoService;
    @Autowired
    MemberService memberService;
    @Autowired
    BackService backService;
    @Autowired
    FestivalImgService festivalImgService;


    @GetMapping("/festivallist")
    public void festivallist(@PageableDefault(size = 8) Pageable pageable, Model model, Long countryid, Long cityid) {
//        Page<FestivalFormDto> festivallist = festivalInfoService.getAllList(pageable);
//        model.addAttribute("festivallist", festivallist);

        model.addAttribute("countryList", countryService.getcountrylist());
        if(countryid ==null && cityid ==null){
            Page<FestivalFormDto> festivallist = festivalInfoService.getAllList(pageable);
            model.addAttribute("festivallist",festivallist);
        }else{
            Page<FestivalFormDto> festivallist = festivalInfoService.getsearchlist(pageable,countryid,cityid);
            model.addAttribute("festivallist",festivallist);
        }
//        else if(countryid ==0 && cityid ==0){
//            Page<TravelInfoDTO> travellist = travelInfoService.getAllList(pageable);
//            model.addAttribute("travellist",travellist);
//        }else if(countryid ==0 && cityid ==null){
//            Page<TravelInfoDTO> travellist = travelInfoService.getAllList(pageable);
//            model.addAttribute("travellist",travellist);
//        }else if(countryid !=0 && cityid ==0){
//            Page<TravelInfoDTO> travellist = travelInfoService.getsearchcitylist(pageable,countryid);
//            model.addAttribute("travellist",travellist);
//        }
    }

    @GetMapping("/festivalregister")
    public String FestivalForm(Model model) {
        model.addAttribute("countryList", countryService.getcountrylist());
        model.addAttribute("festivalFormDto", new FestivalFormDto());
        return "festival/festivalregister";
    }

    @PostMapping("/festivalregister")
    public String FestivalNew(@ModelAttribute("festivalFormDto")  @Valid FestivalFormDto festivalFormDto,
                              BindingResult bindingResult, Model model, @RequestParam("festivalImgFile") List<MultipartFile>
                                      festivalImgFileList) {
        if (bindingResult.hasErrors()) {
            return "festival/festivalregister";
        }

        if (festivalImgFileList.get(0).isEmpty() && festivalFormDto.getFid() == null) {
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "festival/festivalregister";
        }

        try {
            festivalService.saveFestival(festivalFormDto, festivalImgFileList);

        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
            return "festival/festivalregister";
        }

        return "redirect:/";
    }

//    @GetMapping("/festivaldetail")
//    public void festivaldetail(Model model) {
//        model.addAttribute("fetivalList", festivalInfoService.getList());
//    }

    @GetMapping("/festivalregister/{fid}")
    public String festivalDtl(@PathVariable("fid") Long fid, Model model) {
        try {
            model.addAttribute("countryList", countryService.getcountrylist());
            FestivalFormDto festivalFormDto = festivalService.getFestivalDtl(fid);
            model.addAttribute("festivalFormDto", festivalFormDto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "존재하지 않는 상품 입니다.");
            model.addAttribute("festivalFormDto", new FestivalFormDto());
            return "festival/festivalregister";
        }

        return "festival/festivalregister";
    }

    @PostMapping("/festivalregister/{fid}")
    public String festivalUpdate(@Valid FestivalFormDto festivalFormDto, BindingResult bindingResult,
                                 @RequestParam("festivalImgFile") List<MultipartFile>
                                         festivalImgFileList, Model model) {
        if (bindingResult.hasErrors()) {
            return "festival/festivalregister";
        }

        if (festivalImgFileList.get(0).isEmpty() && festivalFormDto.getFid() == null) {
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "festival/festivalregister";
        }

        try {
            festivalService.updateFestival(festivalFormDto, festivalImgFileList);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품 수정 중 에러가 발생하였습니다.");
            return "festival/festivalregister";
        }

        return "redirect:/";
    }

    @GetMapping("/festivalDtl")
    public String festivalDtl(Model model, @RequestParam("fid") Long fid){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+principal);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+principal.equals("anonymousUser"));
        if(!principal.equals("anonymousUser")){
        UserDetails userDetails = (UserDetails)principal;
        String username = ((UserDetails) principal).getUsername();
        model.addAttribute("member",memberService.getMember(username));
        }
        model.addAttribute("backdto", new BackDTO());

        FestivalFormDto festivalFormDto = festivalService.getFestivalDtl(fid);
        model.addAttribute("festival", festivalFormDto);

        return "festival/festivalDtl";
    }

    @PostMapping("/festivalDtl")
    public String festivalDtlPOST(@ModelAttribute("backdto")  @Valid BackDTO backdto,Long fid) {
        System.out.println("festivalDtlPOST@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+backdto);
        backService.saveMF(backdto);

        return "redirect:/festival/festivalDtl?fid="+fid;
    }
    @PostMapping("/del")
    public String deleteFestival(Long fid){

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~"+fid);
        festivalService.deleteFestival(fid);
        return "redirect:/festival/festivallist";
    }

//    @DeleteMapping("/festivalDtl/{fid}")
//    public @ResponseBody ResponseEntity deleteFestival
//            (@PathVariable("fid") Long fid, Principal principal){
//
//        festivalService.deleteFestival(fid);
//        return new ResponseEntity<Long>(fid, HttpStatus.OK);
//    }

}
