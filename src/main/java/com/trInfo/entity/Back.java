package com.trInfo.entity;

import com.trInfo.dto.BackDTO;
import com.trInfo.dto.FestivalFormDto;
import lombok.*;
import org.modelmapper.ModelMapper;

import javax.persistence.*;

@Entity
@Table(name = "back")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Back {

    /*mid FK
    tid FK
    fid FK*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bkid;	        	//pk


    @ManyToOne
    @JoinColumn(name = "mid")
    private Member member;
    @ManyToOne
    @JoinColumn(name = "tid")
    private TravelInfo travelInfo;
    @ManyToOne
    @JoinColumn(name = "fid")
    private FestivalInfo festivalInfo;


}
