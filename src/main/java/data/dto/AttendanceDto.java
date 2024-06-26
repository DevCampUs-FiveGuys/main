package data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Alias("AttendanceDto")
@Builder
@AllArgsConstructor
public class AttendanceDto {
    private int attendance_id;
    private Timestamp check_in;
    private Timestamp check_out;
    private int absent;
    private int vacation;
    private int hospital;
    private int late;
    private int member_id;
    private String membername;
}
