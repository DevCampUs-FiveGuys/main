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
@RequestMapping("/qna/list")
public class QnaListController {

    @NonNull
    private QnaService qnaService;

    //후기 메인 페이지 이동 : 메인 페이지에 필요한 후기 총개수와 후기 데이터들 가지고 이동
    @GetMapping("")
    public String qnalist(
            Model model
    ) {
       int qnatotalCount=qnaService.getQnaTotalCount();
        List<QnaDto> qnalist=qnaService.getAllQnaList();

        model.addAttribute("qnalist", qnalist);
        model.addAttribute("qnatotalCount", qnatotalCount);
        model.addAttribute("page", "qna");

        return "thymeleaf/qna/qnaList";
    }

    //후기 메인 페이지에서 작성 페이지 이동 : 지금 보아하니 model필요 없이 바로 return 값으로 작성페이지로 이동하면 끝났을 거 같네요
    @GetMapping("/write")
    public String qnawrite(Model model) {

        QnaDto qnadto=new QnaDto();
        model.addAttribute("qnadto",qnadto);
        model.addAttribute("page", "/write");

        return "thymeleaf/qna/qnaWrite";
    }

    //후기 작성 기능과 작성 후 메인 페이지로 이동
    @PostMapping("/insert")
    public String qnainsert(
            @ModelAttribute QnaDto qnadto
    )
    {
        qnaService.insertQna(qnadto);

        return "redirect:/qna/list";
    }

    //후기 메인 페이지에서 수정 페이지로 이동 : 이동 할 때 선택 한 후기의 아이디를 가지고 후기의 내용과 함께 페이지로 이동
    @GetMapping("/updateform")
    public String qnaupdateform(
            @RequestParam int qna_id,
            Model model
    )
    {
        QnaDto qnadto=qnaService.getQnaData(qna_id);
        model.addAttribute("qnadto", qnadto);

        return "thymeleaf/qna/qnaUpdate";
    }

    //후기 수정 기능 과 수정 후 메인 페이지 이동
    @PostMapping("/update")
    public String qnaupdate(
            @ModelAttribute QnaDto qnadto
    ){
        qnaService.updateQna(qnadto);
        return "redirect:/qna/list";
    }

    //후기 메인 페이지에서 선택한 후기 삭제 기능
    @GetMapping("/delete")
    public String qnadelete(
            @RequestParam int qna_id
    ){
        qnaService.deleteQna(qna_id);

        return "redirect:/qna/list";
    }

}
