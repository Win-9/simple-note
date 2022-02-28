package simple.simplenote.controller;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import simple.simplenote.controller.form.AddForm;
import simple.simplenote.controller.form.statusform.StatusForm;
import simple.simplenote.controller.form.UpdateForm;
import simple.simplenote.domain.contents.Card;
import simple.simplenote.domain.contents.Text;
import simple.simplenote.service.CardService;

import javax.servlet.http.HttpServlet;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@Controller
@RequestMapping("/contents")
@RequiredArgsConstructor
@Transactional(readOnly = true)
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class CardController extends HttpServlet {

    private final CardService cardService;
    private ObjectMapper objectMapper = new ObjectMapper();


    @Transactional(readOnly = false)
    @GetMapping("/{id}")
    @ResponseBody
    public String showCard(@PathVariable Long id) throws JsonProcessingException {
        Card findCard = cardService.findById(id);
        objectMapper.registerModule(new JavaTimeModule());
        String findResult = objectMapper.writeValueAsString(findCard);

        return findResult;
    }

    @ResponseBody
    @GetMapping("/max_contents")
    public String showMaxContent() {
        List<Card> cardList = cardService.findAll();
        if (cardList.size() == 0){
            return 1+"";
        }

        Card max = Collections.max(cardList, new Comparator<Card>() {
            @Override
            public int compare(Card o1, Card o2) {
                return o1.getId() > o2.getId() ? 1 : -1;
            }
        });

        return max.getId() + "";
    }

    /**
     * contentToc[
     * {id:1,
     * title:aaaaa},
     * ]
     *
     * @return
     */
    @ResponseBody
    @GetMapping("/toc")
    public String showArray() throws JsonProcessingException {
        List<Card> result = cardService.findAll();
        StringBuilder sb = new StringBuilder();
        sb.append("{\"contentsTOC\":[ ");

        for (Card card : result) {
            String idResult = objectMapper.writeValueAsString(card.getId());
            sb.append("{\"id\":" + idResult + ",");
            String titleResult = objectMapper.writeValueAsString(card.getTitle());
            sb.append("\"title\":" + titleResult + "},");
        }

        sb.delete(sb.length() - 1, sb.length());
        sb.append("]}");

        return sb.toString();
    }


    private Text createText(int id, String title, String des) {
        Text text = new Text();
        text.setId(Long.valueOf(id));
        text.setTitle(title);
        text.setDescription(des);
        text.setLastModifiedTime(LocalDateTime.now());
        return text;
    }

    @PostMapping("")
    @Transactional(readOnly = false)
    @ResponseBody
    public String addCard(@RequestBody AddForm addForm) throws JsonProcessingException {
        Text text = createText(addForm.getId(), addForm.getTitle(), addForm.getDescription());

        cardService.add(text);
        log.info("text.title={},text.des={}",text.getTitle(), text.getDescription());
        return objectMapper.writeValueAsString(new StatusForm("Good Received"));
    }


    @PatchMapping("/{id}")
    @ResponseBody
    @Transactional(readOnly = false)
    public String updateDescription(@PathVariable int id,
            @RequestBody UpdateForm updateForm) throws JsonProcessingException {
        Text text = (Text)cardService.findById((long) id);

        text.setTitle(updateForm.getTitle());
        text.setDescription(updateForm.getDescription());
        text.setLastModifiedTime(LocalDateTime.now());

        log.info("text.getTitle={}", text.getTitle());
        cardService.updateCard(text);

        return objectMapper.writeValueAsString(new StatusForm("Good Received"));
    }


    @DeleteMapping("{id}")
    @Transactional(readOnly = false)
    @ResponseBody
    public String deleteCard(@PathVariable int id) throws JsonProcessingException {
        Card findCard = cardService.findById((long) id);
        cardService.removeCard(findCard);

        return objectMapper.writeValueAsString(new StatusForm("Good Received"));
    }

}
