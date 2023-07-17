package com.trInfo.entity;

import com.trInfo.constant.Role;
import com.trInfo.dto.MemberFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "members")
@Getter
@Setter
@ToString
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mid;		                        //pk
    private String email;		                    //이메일
    private String password;		                //비밀번호
    private String name;		                    //이름
    private String nickname;		                //닉네임
    private String tel;		                        //전화번호
    @Enumerated(EnumType.STRING)
    private Role role;                              //USER,ADMIN

    public static Member createMember(MemberFormDto memberFormDto,
                                      PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setEmail(memberFormDto.getEmail());
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setName(memberFormDto.getName());
        member.setNickname(memberFormDto.getNickname());
        member.setTel(memberFormDto.getTel());
        member.setRole(Role.USER);

        return member;
    }
}
