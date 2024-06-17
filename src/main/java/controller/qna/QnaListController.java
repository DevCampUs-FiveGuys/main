package controller.qna;

import data.dto.QnaDto;
import data.service.QnaService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class QnaListController {

    @NonNull
    private QnaService qnaService;

    @GetMapping("/qna/list")
    public String qnalist(
            Model model
    ) {
       int qnatotalCount=qnaService.getQnaTotalCount();
        List<QnaDto> qnalist=qnaService.getAllQnaList();

        model.addAttribute("qnalist", qnalist);
        model.addAttribute("qnatotalCount", qnatotalCount);

        return "thymeleaf/qnalist";
    }

    @GetMapping("/qna/write")
    public String qnawrite(Model model) {

        QnaDto qnadto=new QnaDto();
        model.addAttribute("qnadto",qnadto);

        return "thymeleaf/qnawrite";
    }

    @GetMapping("/qna/insert")
    public String qnainsert(
            @ModelAttribute QnaDto qnadto
    )
    {
        qnaService.insertQna(qnadto);

        return "redirect:/qna/list";
    }

    @GetMapping("/qna/updateform")
    public String qnaupdateform(
            @RequestParam int qna_id,
            Model model
    )
    {
        QnaDto qnadto=qnaService.getQnaData(qna_id);
        model.addAttribute("qnadto", qnadto);

        return "thymeleaf/qnaupdateform";
    }

    @PostMapping("/qna/update")
    public String qnaupdate(
            @ModelAttribute QnaDto qnadto
    ){
        qnaService.updateQna(qnadto);
        return "qnalist";
    }

    @GetMapping("/qna/delete")
    public String qnadelete(
            @RequestParam int qna_id
    ){
        qnaService.deleteQna(qna_id);

        return "redirect:/qna/list";
    }

}
