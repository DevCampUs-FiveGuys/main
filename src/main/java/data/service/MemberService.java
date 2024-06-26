package data.service;

import data.dto.MemberDto;
import data.mapper.MemberMapperInter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberMapperInter memberMapperInter;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    // 회원가입
    public void insertMember(MemberDto memberDto) {
        //password 를 bCryptPasswordEncoder 를 통해 암호화 하여 바꿔 저장.
        String password = bCryptPasswordEncoder.encode(memberDto.getPassword());
        memberDto.setPassword(password);
        memberMapperInter.insertMember(memberDto);
    }
    public int checkID(String email) {
        return memberMapperInter.checkID(email);
    }
    public MemberDto findByUsername(String email) {
        return memberMapperInter.findByUsername(email);
    }
    // 사용자 정보 업데이트
    public void updateMember(MemberDto memberDto) {
        memberMapperInter.updateMember(memberDto);
    }
}
