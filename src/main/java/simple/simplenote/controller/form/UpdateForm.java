package simple.simplenote.controller.form;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter @Setter
public class UpdateForm {
    private String title;
    private String description;
}
