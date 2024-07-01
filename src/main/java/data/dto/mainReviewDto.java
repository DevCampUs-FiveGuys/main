package data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@Builder
@Alias("mainReviewDto")
@NoArgsConstructor
@AllArgsConstructor
public class mainReviewDto {
    private int review_id;
    private String content;
    private String created_at;
    private int like;
    private String name;
    private String course_name;
    private String course_num;
    private String photo;
}
