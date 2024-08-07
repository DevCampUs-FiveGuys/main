package data.mapper;

import data.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapperInter {
    public void insertMember(MemberDto memberDto);
    public int checkID(String email);
    public MemberDto findByUsername(String email);
    public void updateMember(MemberDto memberDto); // 사용자 정보 업데이트
}
