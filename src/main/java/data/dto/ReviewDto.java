package data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;
@NoArgsConstructor
@Data
@Alias("ReviewDto")
@Builder
@AllArgsConstructor
public class ReviewDto {
    private String content;
    private Timestamp created_at;
    private double star;
    private int like_count;
    private String name;
    private int gender;
}
