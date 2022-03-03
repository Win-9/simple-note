package simple.simplenote.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import simple.simplenote.domain.Member;
import simple.simplenote.domain.contents.Card;
import simple.simplenote.domain.contents.CardStatus;
import simple.simplenote.domain.contents.Text;

import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
public class ShowCardDto {

    private Long id;

    private String title;

    private CardStatus cardStatus;

    private LocalDateTime lastModifiedTime;

    private String description;

    private String author;

    public void setDescription(Card card){
        description = ((Text)card).getDescription();
    }

}
