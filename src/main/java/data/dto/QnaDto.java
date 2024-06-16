package data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Alias("QnaDto")
@Builder
@AllArgsConstructor
public class QnaDto {
    private int qna_id;
    private String title;
    private String content;
    private Timestamp created_at;
    private Timestamp updated_at;
}
