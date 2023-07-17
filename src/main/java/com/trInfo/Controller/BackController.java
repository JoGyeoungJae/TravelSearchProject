package com.trInfo.Controller;

import com.trInfo.dto.BackDTO;
import com.trInfo.entity.Back;
import com.trInfo.entity.Member;
import com.trInfo.service.BackService;
import com.trInfo.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/back")
@Log4j2
@RequiredArgsConstructor
public class BackController {
    @Autowired
    private BackService backService;
    @Autowired
    private MemberService memberService;

    @GetMapping("/backlist")
    public String backlist(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        model.addAttribute("member",memberService.getMember(username));

        Long mid = backService.getbackmemberlist(username).getMid();

        List<BackDTO> backfestivalList = backService.getFestivalbacklist(mid);
        System.out.println("backfestivalList :"+ backfestivalList.size());
        List<BackDTO> backtravelList = backService.gettravelbacklist(mid);
        System.out.println("backtravelList :"+ backtravelList.size());
        model.addAttribute("backfestivalList",backfestivalList);
        model.addAttribute("backtravelList",backtravelList);

        return "/back/backlist";

    }
    @PostMapping("/del")
    public void backRemovePost(Long bkid){
        backService.del(bkid);
    }

}
