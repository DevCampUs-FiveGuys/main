package data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Alias("HeartDto")
public class HeartDto {
    private int heart_id;
    private int member_id;
    private int portfolio_id;
}
