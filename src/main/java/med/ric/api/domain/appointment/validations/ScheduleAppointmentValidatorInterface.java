package med.ric.api.domain.appointment.validations;

import med.ric.api.domain.appointment.ScheduleAppointmentData;

public interface ScheduleAppointmentValidatorInterface {
    void validate(ScheduleAppointmentData data);
}
