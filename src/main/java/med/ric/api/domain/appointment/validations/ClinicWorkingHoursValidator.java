package med.ric.api.domain.appointment.validations;

import med.ric.api.domain.CustomValidationException;
import med.ric.api.domain.appointment.ScheduleAppointmentData;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ClinicWorkingHoursValidator implements ScheduleAppointmentValidatorInterface {

    public void validate(ScheduleAppointmentData data) {
        var scheduleDate = data.date();
        var sunday = scheduleDate.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        var beforeOpenHour = scheduleDate.getHour() < 7;
        var afterCloseHour = scheduleDate.getHour() > 18;

        if (sunday || beforeOpenHour || afterCloseHour) {
            throw new CustomValidationException("Clinic is not open at this time");
        }
    }
}
