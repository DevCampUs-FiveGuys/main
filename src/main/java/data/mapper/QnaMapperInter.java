package data.mapper;

import data.dto.QnaDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QnaMapperInter {


    @Insert("""
            insert into qna (title, content, created_at)
            VALUES (#{title},#{content},now())
            """)
    public void insertQna(QnaDto qnadto);

    @Select("select count(*) from qna")
    public int getQnaTotalCount();

    @Select("select * from qna order by qna_id asc")
    public List<QnaDto> getAllQnaList();

    @Select("select * from qna where qna_id=#{qna_id}")
    public QnaDto getQnaData(int qna_id);

    @Update("update qna set title=#{title},content=#{content},update_at=#{now()} where qna_id=#{qna_id}")
    public void updateQna(QnaDto qnadto);

    @Delete("delete from qna where qna_id=#{qna_id}")
    public int deleteQna(int qna_id);

}
