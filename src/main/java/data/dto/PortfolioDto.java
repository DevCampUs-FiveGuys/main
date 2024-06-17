package data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;
@Data
@Alias("PortfolioDto")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PortfolioDto {
    private int portfolio_id;
    private int member_id;
    private String title;
    private String description;
    private Timestamp created_at;
    private Timestamp updated_at;
    private String file_name;
    private String readcount;
}
