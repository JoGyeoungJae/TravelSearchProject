package com.trInfo.dto;

import com.trInfo.entity.Board;
import com.trInfo.entity.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class BoardRegDto {

    private Long bid;
    @NotBlank(message = "제목을 입력해주세요")
    private String btitle;
    @NotBlank(message = "내용을 입력해주세요")
    @Size(min=3, max=100)
    private String bcontent;

    //private Long mid;
    @ManyToOne
    @JoinColumn(name = "mid")
    private Member member;

    private List<BoardRegDto> boardRegDtoList = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Board createBoard(){
        return modelMapper.map(this, Board.class);
    }

    public static BoardRegDto of(Board board){
        return modelMapper.map(board, BoardRegDto.class);
    }

}
