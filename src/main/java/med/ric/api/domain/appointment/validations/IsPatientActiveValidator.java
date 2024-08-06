package med.ric.api.domain.appointment.validations;

import med.ric.api.domain.CustomValidationException;
import med.ric.api.domain.appointment.ScheduleAppointmentData;
import med.ric.api.domain.patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class IsPatientActiveValidator implements ScheduleAppointmentValidatorInterface {

    @Autowired
    private PatientRepository patientRepository;

    public void validate(ScheduleAppointmentData data) {
        if(data.patientId() == null) {
            return;
        }
        var activePatient = patientRepository.findActiveById(data.patientId());
        if(!activePatient) {
            throw new CustomValidationException("Patient not found or not active");
        }

    }
}
