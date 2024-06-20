package data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@NoArgsConstructor
@Data
@Alias("CheckLikeDto")
@Builder
@AllArgsConstructor
public class CheckLikeDto {
    private int isClicked;
    private int mem_id;
    private int rev_id;
}
