package com.shop.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.boot.model.source.internal.hbm.XmlElementMetadata;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.net.UnknownServiceException;
import java.util.Collection;


@Getter
@Setter
@ToString
public class MemberSecurityDTO extends User {

    private String mid;
    private String mpw;
    private String email;
    private boolean del;

    public MemberSecurityDTO(String username, String password, String email, boolean del, Collection<? extends GrantedAuthority> authorities){
        super(username, password, authorities);

        this.mid = username;
        this.mpw = password;
        this.email = email;
        this.del = del;
    }
}
