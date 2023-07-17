package com.trInfo.service;

import com.trInfo.dto.BoardRegDto;
import com.trInfo.entity.Board;
import com.trInfo.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class BoardService {

    private final BoardRepository boardRepository;

    public void saveBoard(BoardRegDto boardRegDto) throws Exception{
        log.info("boardService..........." + boardRegDto);

        //커뮤니티 등록
        Board board = boardRegDto.createBoard();
        boardRepository.save(board);
    }

   /* public Page<Board> getBoardPage(BoardRegDto boardRegDto, Pageable pageable){
        return boardRepository.getBoardPage(boardRegDto, pageable);
    }*/

    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public BoardRegDto getBoardDtl(Long bno){
        Board board = boardRepository.findById(bno).orElseThrow(EntityNotFoundException::new);
        BoardRegDto boardRegDto = BoardRegDto.of(board);

        return boardRegDto;
    }

    public Long updateBoard(BoardRegDto boardRegDto) throws Exception{
        Board board = boardRepository.findById(boardRegDto.getBid()).orElseThrow(EntityNotFoundException::new);
        board.updateBoard(boardRegDto);

        return board.getBid();
    }

}
