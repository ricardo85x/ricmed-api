package med.ric.api.domain.appointment;

import med.ric.api.domain.CustomValidationException;
import med.ric.api.domain.appointment.validations.ScheduleAppointmentValidatorInterface;
import med.ric.api.domain.doctor.Doctor;
import med.ric.api.domain.doctor.DoctorRepository;
import med.ric.api.domain.patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleAppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private List<ScheduleAppointmentValidatorInterface> validators;

    public DetailsAppointmentData schedule(ScheduleAppointmentData data) {

        if(!patientRepository.existsById(data.patientId())){
            throw new CustomValidationException("Patient not found");
        }

        if (data.doctorId() != null && !doctorRepository.existsById(data.doctorId())) {
            throw new CustomValidationException("Doctor not found");
        }

        validators.forEach(validator -> validator.validate(data));

        var patient = patientRepository.getReferenceById(data.patientId());
        var doctor = chooseDoctor(data);
        if(doctor == null){
            throw new CustomValidationException("Doctor with this specialty is not available at this time");
        }

        var appointment = new Appointment(null, doctor, patient, data.date());
        appointmentRepository.save(appointment);

        return new DetailsAppointmentData(appointment);
    }

    private Doctor chooseDoctor(ScheduleAppointmentData data) {
        if (data.doctorId() != null) {
            return doctorRepository.getReferenceById(data.doctorId());
        }

        if (data.speciality() == null) {
            throw new CustomValidationException("Speciality is required when doctor is not informed");
        }

        return doctorRepository.findRandomAvailableDoctorBySpecialty(data.date(), data.speciality());
    }
}
