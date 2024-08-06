package med.ric.api.domain.appointment.validations.cancel;

import med.ric.api.domain.appointment.Appointment;

public interface CancelAppointmentValidatorInterface {
    void validate(Appointment appointment);
}
