package data.service;


import data.dto.MemberDto;
import data.mapper.AdminMapperInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminMapperInter adminMapperInter;

    public List<MemberDto> getAllMemberList(){
        return adminMapperInter.getAllMemberList();
    }
}
