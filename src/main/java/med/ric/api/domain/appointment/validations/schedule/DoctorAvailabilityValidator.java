package med.ric.api.domain.appointment.validations.schedule;

import med.ric.api.domain.CustomValidationException;
import med.ric.api.domain.appointment.ScheduleAppointmentData;
import med.ric.api.domain.appointment.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DoctorAvailabilityValidator implements ScheduleAppointmentValidatorInterface {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public void validate(ScheduleAppointmentData data) {
        var hasAppointment = appointmentRepository.existsByCanceledFalseAndDoctorIdAndDate(data.doctorId(), data.date());

        if (hasAppointment) {
            throw new CustomValidationException("Doctor already has an appointment at this time");
        }
    }
}
