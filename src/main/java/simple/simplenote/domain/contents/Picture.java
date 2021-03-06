package simple.simplenote.domain.contents;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter @Setter
@DiscriminatorValue("Picture")
public class Picture extends Card {
    private String copyRight;
    private String pictureName;
}
