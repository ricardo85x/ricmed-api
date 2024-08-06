package med.ric.api.domain.appointment.validations.schedule;

import med.ric.api.domain.CustomValidationException;
import med.ric.api.domain.appointment.ScheduleAppointmentData;
import org.springframework.stereotype.Component;

@Component
public class FullHourValidator implements ScheduleAppointmentValidatorInterface {

    public void validate(ScheduleAppointmentData data) {
        var scheduleDate = data.date();
        var notExactHour = scheduleDate.getMinute() != 0 || scheduleDate.getSecond() != 0 || scheduleDate.getNano() != 0;

        if (notExactHour) {
            throw new CustomValidationException("Appointment time must be on the hour.");
        }
    }
}
