package com.trInfo.Controller;

import com.trInfo.dto.MemberFormDto;
import com.trInfo.entity.Member;
import com.trInfo.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/members")
public class MemberController {

    @Autowired
    MemberService memberService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/new")
    public String memberForm(Model model) {
        model.addAttribute("memberFormDto", new MemberFormDto());

        return "member/memberForm";
    }
    /*@Valid  -  유효성 검사에 사용*/
    @PostMapping("/new")
    public String memberForm(@Valid MemberFormDto memberFormDto,
                             BindingResult bindingResult, Model model) {
        // 에러가 있으면 회원가입 페이지로 이동
        if (bindingResult.hasErrors()) {
            return "member/memberForm";
        }

        try {
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            // 회원정보 DB에 저장
            memberService.saveMember(member);
        } catch (IllegalStateException e) {
            // 중복 회원일 경우 처리
            System.out.println(e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";
        }

        // 문제 없으면 main페이지로 이동
        return "redirect:/";
    }
    @GetMapping("/memberMod")
    public String memberMod(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        model.addAttribute("member",memberService.getMember(username));
        return "member/memberMod";
    }
    @PostMapping("/memberMod")
    public String memberModPost(Member member) {
        memberService.updateMember(member);
        return "redirect:/";
    }
    @GetMapping("/memberPasswordMod")
    public String memberPasswordMod(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails)principal;
        String username = ((UserDetails) principal).getUsername();
        model.addAttribute("member",memberService.getMember(username));
        return "member/memberPasswordMod";
    }
    @PostMapping("/memberPasswordMod")
    public String memberPasswordModPost(Member member) {
        System.out.println(member.getPassword());
        String password = passwordEncoder.encode(member.getPassword());
        member.setPassword(password);
        memberService.updateMember(member);
        return "redirect:/";
    }
    @GetMapping("/login")
    public String loginMember() {

        return "member/memberLoginForm";
    }

    @GetMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginErrorMsg",
                "아이디 또는 비밀번호를 확인해주세요");
        return "member/memberLoginForm";
    }
    @PostMapping("/remove")
    public String memberRemovePost(Long mid){
        System.out.println("111111"+mid);
        memberService.memberRemove(mid);
        SecurityContextHolder.clearContext();
        return "redirect:/";
    }
    @GetMapping("/membermanager")
    public String membermanager(@PageableDefault Pageable pageable, Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        model.addAttribute("mymember",memberService.getMember(username));


        Page<Member> memberAlllist = memberService.memberAllList(pageable);
        System.out.println(memberAlllist.getSize());
        model.addAttribute("memberList",memberAlllist);

        return "member/memberManager";
    }
    @PostMapping("/del")
    public void memberdelPost(Long mid){
        memberService.memberRemove(mid);
    }
}








