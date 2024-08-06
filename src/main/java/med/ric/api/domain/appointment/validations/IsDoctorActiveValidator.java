package med.ric.api.domain.appointment.validations;

import med.ric.api.domain.CustomValidationException;
import med.ric.api.domain.appointment.ScheduleAppointmentData;
import med.ric.api.domain.doctor.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IsDoctorActiveValidator implements ScheduleAppointmentValidatorInterface {

    @Autowired
    private DoctorRepository doctorRepository;

    public void validate(ScheduleAppointmentData data) {
        if(data.doctorId() == null) {
            return;
        }
        var activeDoctor = doctorRepository.findActiveById(data.doctorId());
        if(!activeDoctor) {
            throw new CustomValidationException("Doctor is not found or not active");
        }
    }
}
