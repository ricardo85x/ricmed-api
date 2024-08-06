package med.ric.api.domain.appointment.validations.cancel;

import med.ric.api.domain.CustomValidationException;
import med.ric.api.domain.appointment.Appointment;
import org.springframework.stereotype.Component;

@Component
public class AlreadyCanceledValidator implements  CancelAppointmentValidatorInterface {
    public void validate(Appointment appointment) {
        if (appointment.getCanceled()) {
            throw new CustomValidationException("Appointment is already canceled");
        }
    }
}
