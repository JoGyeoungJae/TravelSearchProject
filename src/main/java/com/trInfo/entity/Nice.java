package com.trInfo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "nice")
@Getter
@Setter
public class Nice {
    /*bid FK*/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nid;       		//pk
    private String email;	    	//이메일

    @ManyToOne
    @JoinColumn(name = "bid")
    private Board board;
}
