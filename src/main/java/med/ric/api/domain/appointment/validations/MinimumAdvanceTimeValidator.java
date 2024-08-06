package med.ric.api.domain.appointment.validations;

import med.ric.api.domain.CustomValidationException;
import med.ric.api.domain.appointment.ScheduleAppointmentData;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class MinimumAdvanceTimeValidator implements ScheduleAppointmentValidatorInterface {
    public void validate(ScheduleAppointmentData data) {
        var now = LocalDateTime.now();
        var scheduleDate = data.date();
        var diff = Duration.between(now, scheduleDate).toMinutes();

        if (diff < 30) {
            throw new CustomValidationException("Appointment must be scheduled at least 30 minutes in advance.");
        }
    }
}
