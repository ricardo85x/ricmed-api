package med.ric.api.domain.appointment.validations.cancel;

import med.ric.api.domain.CustomValidationException;
import med.ric.api.domain.appointment.Appointment;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class MinimumTimeCancellationValidator implements CancelAppointmentValidatorInterface {

    public void validate(Appointment appointment) {
        var scheduleDate = appointment.getDate();
        if (scheduleDate.minusHours(24).isBefore(LocalDateTime.now())) {
            throw new CustomValidationException("Minimum time to cancel an appointment is 24 hours");
        }
    }
}
