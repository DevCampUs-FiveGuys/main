package data.service;

import data.dto.CustomUserDetails;
import data.dto.MemberDto;
import data.mapper.MemberMapperInter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final MemberMapperInter memberMapperInter;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberDto memberDto = memberMapperInter.findByUsername(username);
        if (memberDto != null) {
            return new CustomUserDetails(memberDto);
        }
        return null;
    }
}
