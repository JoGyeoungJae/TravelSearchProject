package com.trInfo.entity;

import com.trInfo.dto.BoardRegDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "board")
@Getter
@Setter
public class Board {
    /*mid FK*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bid;		    //pk
    private String btitle;		//제목
    private String bcontent;	//내용

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime binsertdate;  //등록 날짜

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mid")
    private Member member;

    public void updateBoard(BoardRegDto boardRegDto){
        this.btitle = boardRegDto.getBtitle();
        this.bcontent = boardRegDto.getBcontent();
    }
}
