package data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.sql.Timestamp;

@Data
@Builder
@Alias("MemberDto")
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    private int member_id;
    private String password;
    private String name;
    private int gender;
    private String email;
    private String tel;
    private String birth;
    private Timestamp created_at;
    private Timestamp updated_at;
    private String photo;
    private String roles;
    private String course_name;
    private String course_num;
}
