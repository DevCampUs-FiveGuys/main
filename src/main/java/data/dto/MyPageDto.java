package data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Alias("MyPageDto")
@Builder
@AllArgsConstructor
public class MyPageDto {
    private int attendanceId;
    private Timestamp checkIn;
    private Timestamp checkOut;
    private int absent;
    private int vacation;
    private int hospital;
    private int late;
    private int memberId;

    private int vacationId;
    private Timestamp date;
    private String reason;
    private int confirm;
}
