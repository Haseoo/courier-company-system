package com.github.haseoo.courier.security;

import com.github.haseoo.courier.enums.UserType;
import com.github.haseoo.courier.models.UserModel;
import com.github.haseoo.courier.utilities.UserUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

import static lombok.AccessLevel.PRIVATE;

@Builder(access = PRIVATE)
@AllArgsConstructor
@Getter
public class UserDetailsImpl implements UserDetails {
    private Long id;
    private String username;
    private String password;
    private UserRole userRole;
    private UserType userType;
    private boolean enabled;

    public static UserDetailsImpl of(UserModel userModel) {
        return UserDetailsImpl
                .builder()
                .id(userModel.getId())
                .userType(UserUtils.getUserType(userModel))
                .username(userModel.getUserName())
                .password((userModel.getPassword() != null) ? new String(userModel.getPassword()) : null)
                .enabled(userModel.getActive())
                .userRole(UserUtils.getUserRole(userModel))
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(userRole.toString()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
