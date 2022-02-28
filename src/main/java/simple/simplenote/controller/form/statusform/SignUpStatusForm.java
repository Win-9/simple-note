package simple.simplenote.controller.form.statusform;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignUpStatusForm {
    private boolean isRegistered;
    private String errorMessage;
}
