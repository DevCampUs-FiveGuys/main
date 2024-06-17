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

    public void insertQna(QnaDto qnadto)
    {
        qnaInter.insertQna(qnadto);
    }

    public int getQnaTotalCount()
    {
        return qnaInter.getQnaTotalCount();
    }

    public List<QnaDto> getAllQnaList()
    {
        return qnaInter.getAllQnaList();
    }

    public QnaDto getQnaData(int qna_id)
    {
        return qnaInter.getQnaData(qna_id);
    }

    public void updateQna(QnaDto qnadto)
    {
        qnaInter.updateQna(qnadto);
    }

    public void deleteQna(int qna_id)
    {
        qnaInter.deleteQna(qna_id);
    }
}
