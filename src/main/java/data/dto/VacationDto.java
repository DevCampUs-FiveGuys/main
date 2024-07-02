package data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Alias("VacationDto")
@Builder
@AllArgsConstructor
public class VacationDto {
    private int vacation_id;
    private Timestamp date;
    private String reason;
    private int confirm;
    private int member_id;
    private String name;
}
