package com.trInfo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "reply")
@Getter
@Setter
public class Reply {
    /*mid FK*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rid;		        //pk
    private String rcontent;		//내용
    @ManyToOne
    @JoinColumn(name = "bid")
    private Board board;
    @ManyToOne
    @JoinColumn(name = "mid")
    private Member member;
}
