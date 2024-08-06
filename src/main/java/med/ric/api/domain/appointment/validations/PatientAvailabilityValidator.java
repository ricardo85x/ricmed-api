package med.ric.api.domain.appointment.validations;

import med.ric.api.domain.CustomValidationException;
import med.ric.api.domain.appointment.ScheduleAppointmentData;
import med.ric.api.domain.appointment.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientAvailabilityValidator implements ScheduleAppointmentValidatorInterface {

    @Autowired
    private AppointmentRepository appointmentRepository;
    public void validate(ScheduleAppointmentData data) {

        var firstHour = data.date().withHour(7);
        var lastHour = data.date().withHour(18);
        var hasAppointment = appointmentRepository.existsByPatientIdAndDateBetween(data.patientId(), firstHour, lastHour);

        if (hasAppointment) {
            throw new CustomValidationException("Patient already has an appointment at this day");
        }
    }
}
