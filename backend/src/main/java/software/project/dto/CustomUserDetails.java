package software.project.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import software.project.domain.Member;
import software.project.repository.MemberRepository;

import java.util.ArrayList;
import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private final Member member;
    public CustomUserDetails(Member member) {
        this.member = member;
    }

    // 현재 user의 role을 반환 (ex. "ROLE_ADMIN" / "ROLE_USER" 등)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                // 앞에 "ROLE_" 접두사 필수 !
                return "ROLE_" + member.getRole().name();
            }
        });

        return collection;
    }

    // user의 비밀번호 반환
    @Override
    public String getPassword() {
        return member.getPassword();
    }

    // user의 username 반환
    @Override
    public String getUsername() {
        return member.getLoginId();
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

    @Override
    public boolean isEnabled() {
        return true;
    }


}