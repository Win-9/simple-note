package simple.simplenote.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;
import simple.simplenote.domain.contents.Card;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {


    @Id @Column(name = "member_id")
    private String nickName;

    private String passWord;

    @OneToMany(mappedBy = "member")
    @JsonIgnoreProperties({"member"})
    private List<Card> cards = new ArrayList<>();
}
