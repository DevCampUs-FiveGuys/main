package data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@Builder
@Alias("mainDto")
@NoArgsConstructor
@AllArgsConstructor
public class mainDto {
    private int portfolio_id;
    private String title;
    private String created_at;
    private String file_name;
    private String name;
}
