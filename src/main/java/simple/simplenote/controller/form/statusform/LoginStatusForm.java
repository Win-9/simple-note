package simple.simplenote.controller.form.statusform;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginStatusForm {
    private boolean isIdMatch;
    private boolean isPassWordMatch;
    private String errorMessage;
}
