package data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@Builder
@Alias("mainPortfolioDto")
@NoArgsConstructor
@AllArgsConstructor
public class mainPortfolioDto {
    private int portfolio_id;
    private String title;
    private String created_at;
    private String file_name;
    private String name;
}
