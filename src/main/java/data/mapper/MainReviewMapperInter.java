package data.mapper;

import data.dto.mainReviewDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MainReviewMapperInter {
    public List<mainReviewDto> getReviewDataMain();
}
