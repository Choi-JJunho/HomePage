package com.junho.homepage.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mbr_id")
    private Long id;

    @Column(name = "acnt", unique = true)
    private String account;

    @Column(name = "pwrd")
    private String password;

    @Column(name = "nck_nm")
    private String nickname;

    @Column(name = "nm")
    private String name;

    @Column(name = "eml", unique = true)
    private String email;

    @Column(name = "rfsh_tkn")
    private String refreshToken;

    @Column(name = "enbl_yn")
    @Builder.Default
    private boolean enabled = true;

    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Authority> roles = new ArrayList<>();

    public void setRoles(List<Authority> role) {
        this.roles = role;
        role.forEach(o -> o.setMember(this));
    }
}
