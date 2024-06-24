package data.mapper;

import data.dto.QnaDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QnaMapperInter {

    //후기 작성 기능 : title(제목)과 content(내용) 입력 하면 created_at(작성 시간)과 함께 qna DB에 저장
    @Insert("insert into qna (title, content, created_at) VALUES (#{title},#{content},now())")
    public void insertQna(QnaDto qnadto);
    
    //전체 후기 개수 가져오기 : qna DB에 있는 전체 값의 개수를 count(숫자 세기)를 통해 후기 개수 불러옴
    @Select("select count(*) from qna")
    public int getQnaTotalCount();

    //전체 후기 가져오기 : qna DB에 있는 전체 후기를 가져오는데 qna_id의 오름차순으로 데이터 가져옴
    @Select("select * from qna order by qna_id asc")
    public List<QnaDto> getAllQnaList();

    //선택한 후기 가져오기 : 후기 선택 시 선택한 후기의 qna_id와 같은 qna_id를 qna DB에서 찾아서 데이터 가져옴
    @Select("select * from qna where qna_id=#{qna_id}")
    public QnaDto getQnaData(int qna_id);

    //후기 수정 기능 : 수정할 후기 선택 후 수정 페이지에서 내용(title,content)를 수정 한 후 선택한 qna_id와 같은 qna_id를
    //qna DB에서 찾아서 데이터 수정함
    @Update("update qna set title=#{title},content=#{content},updated_at=NOW() where qna_id=#{qna_id}")
    public void updateQna(QnaDto qnadto);

    //후기 삭제 기능 : 메인 페이지에서 선택한 후기의 삭제 버튼 클릭 하면 선택한 후기의 qna_id와 같은 qna_id를 qna DB에서 찾아서 데이터 삭제함
    @Delete("delete from qna where qna_id=#{qna_id}")
    public void deleteQna(int qna_id);

}
