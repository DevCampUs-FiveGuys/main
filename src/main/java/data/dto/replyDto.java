package data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Alias("ReplyDto")
public class replyDto {
    private int reply_id;
    private int member_id;
    private int portfolio_id;
    private String comment;
    private Timestamp created_at;
    private int readcount;
    private int regroup;
    private int restep;
    private int relevel;
    private int recount;
}
