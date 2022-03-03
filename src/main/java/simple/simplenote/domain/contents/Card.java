package simple.simplenote.domain.contents;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import simple.simplenote.domain.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Card {


    @Id
    @Column(name = "card_id")
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private CardStatus cardStatus;

    private LocalDateTime lastModifiedTime;

    @ManyToOne
    @JsonIgnoreProperties({"cards"})
    @JoinColumn(name = "member_id")
    private Member member;
}
