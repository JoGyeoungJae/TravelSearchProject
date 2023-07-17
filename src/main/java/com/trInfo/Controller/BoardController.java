package com.trInfo.Controller;

import com.trInfo.dto.BoardRegDto;
import com.trInfo.entity.Board;
import com.trInfo.repository.BoardRepository;
import com.trInfo.service.BoardService;
import com.trInfo.service.MemberService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/board")
@Log4j2
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private BoardRepository boardRepository;

    //등록
    @GetMapping("/register")
    public String register(Model model){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        memberService.getMember(username);
        model.addAttribute("member",memberService.getMember(username));
        System.out.println(memberService.getMember(username));
        model.addAttribute("boardRegDto", new BoardRegDto());
        return "board/boardReg";
    }
    @PostMapping("/register")
    public String register_p(@Valid BoardRegDto boardRegDto,
                             BindingResult bindingResult, Model model){
        log.info("boardController ......." + boardRegDto);
        if(bindingResult.hasErrors()){
            return "board/boardReg";
        }

        try{
            boardService.saveBoard(boardRegDto);
        }catch (Exception e){
            model.addAttribute("errorMessage", e.getMessage());
            return "board/boardReg";
        }

        return "redirect:/board/lists";
    }

    //리스트페이지
    @GetMapping({"/lists", "/lists/{page}"})
    public String boardlist(BoardRegDto boardRegDto,
                            @PathVariable("page")Optional<Integer> page, Model model){
        log.info("lists Get Controller............");

      /* Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0,3);
        Page<Board> boards = boardService.getBoardPage(boardRegDto, pageable);

        model.addAttribute("boardList", boards);*/
        model.addAttribute("maxPage", 5);

        return "board/BoardList";
    }

    //상세보기
    @GetMapping("/view/{bid}")
    public String boardView(@PathVariable("bid") Long bid, Model model){
        try{
            BoardRegDto boardRegDto = boardService.getBoardDtl(bid);
            model.addAttribute("boardRegDto", boardRegDto);
        }catch (EntityNotFoundException e){
            model.addAttribute("errorMessage", "존재하지 않는 상품 입니다.");
            model.addAttribute("boardRegDto", new BoardRegDto());

            return "board/register";
        }
        return "board/boardView";
    }

    //수정하기
    @GetMapping("/modify/{bid}")
    public String boardModify(@PathVariable("bid") Long bid, Model model){
        try{
            BoardRegDto boardRegDto = boardService.getBoardDtl(bid);
            model.addAttribute("bid", bid);
            model.addAttribute("boardRegDto", boardRegDto);
        }catch (EntityNotFoundException e){
            model.addAttribute("errorMessage", "존재하지 않는 상품 입니다");
            model.addAttribute("boardRegDto", new BoardRegDto());
        }

        return "board/boardModify";
    }

    @PostMapping("/modify")
    public String boardUpdate(BoardRegDto boardRegDto, Model model, Long mid){
        Long bid = boardRegDto.getBid();
        log.info("boardControll / Post / ..................." + bid);
        log.info("boardControll / Post / ..................." + mid);

        try{
            boardService.updateBoard(boardRegDto);
        }catch (Exception e){
            model.addAttribute("errorMessage", "상품 수정중 에러가 발생했습니다.");
            return "board/boardModify";
        }
        return "redirect:/board/lists";
    }


}
