package data.service;

import data.mapper.VacationMapperInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VacationService {

    @Autowired
    private VacationMapperInter vacationMapper;
}
