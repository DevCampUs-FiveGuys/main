package data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Alias("HeartDto")
@Builder
@AllArgsConstructor
public class HeartDto {
    private int heart_id;
    private int member_id;
    private int portfolio_id;
    private String file_name;     // portfolio 로 부터 가져온 필드
    private String readcount;     // portfolio 로 부터 가져온 필드
    private Timestamp created_at; // portfolio 로 부터 가져온 필드
    private String title;         // portfolio 로 부터 가져온 필드
    private String name;          // member 로 부터 가져온 필드
}
