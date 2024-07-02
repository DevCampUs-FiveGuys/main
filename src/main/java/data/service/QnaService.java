package data.service;

import data.dto.QnaDto;
import data.mapper.QnaMapperInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QnaService {

    @Autowired
    private QnaMapperInter qnaInter;

    //후기 작성 기능 : title(제목)과 content(내용) 입력 하면 created_at(작성 시간)과 함께 qna DB에 저장
    public void insertQna(QnaDto qnadto)
    {
        qnaInter.insertQna(qnadto);
    }

    //전체 후기 개수 가져오기 : qna DB에 있는 전체 값의 개수를 count(숫자 세기)를 통해 후기 개수 불러옴
    public int getQnaTotalCount()
    {
        return qnaInter.getQnaTotalCount();
    }

    //전체 후기 가져오기 : qna DB에 있는 전체 후기를 가져오는데 qna_id의 오름차순으로 데이터 가져옴
    public List<QnaDto> getAllQnaList()
    {
        return qnaInter.getAllQnaList();
    }

    //선택한 후기 가져오기 : 후기 선택 시 선택한 후기의 qna_id와 같은 qna_id를 qna DB에서 찾아서 데이터 가져옴
    public QnaDto getQnaData(int qna_id)
    {
        return qnaInter.getQnaData(qna_id);
    }

    //후기 수정 기능 : 수정할 후기 선택 후 수정 페이지에서 내용(title,content)를 수정 한 후 선택한 qna_id와 같은 qna_id를
    //qna DB에서 찾아서 데이터 수정함
    public void updateQna(QnaDto qnadto)
    {
        qnaInter.updateQna(qnadto);
    }

    //후기 삭제 기능 : 메인 페이지에서 선택한 후기의 삭제 버튼 클릭 하면 선택한 후기의 qna_id와 같은 qna_id를 qna DB에서 찾아서 데이터 삭제함
    public void deleteQna(int qna_id)
    {
        qnaInter.deleteQna(qna_id);
    }
}
