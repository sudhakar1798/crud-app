package io.sudhakar.student.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
public class User extends org.springframework.security.core.userdetails.User {

    private final long userId;

    public User(String userName, String password, long userId, Collection<? extends GrantedAuthority> authorities
    ) {
        super(userName, password, authorities);
        this.userId = userId;

    }

    public long getUserId() {
        return userId;
    }
}
