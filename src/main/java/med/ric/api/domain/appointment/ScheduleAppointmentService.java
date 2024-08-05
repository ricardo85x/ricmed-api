package med.ric.api.domain.appointment;

import med.ric.api.domain.CustomValidationException;
import med.ric.api.domain.doctor.Doctor;
import med.ric.api.domain.doctor.DoctorRepository;
import med.ric.api.domain.patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleAppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    public void schedule(ScheduleAppointmentData data) {

        if(!patientRepository.existsById(data.patientId())){
            throw new CustomValidationException("Patient not found");
        }

        if (data.doctorId() != null && !doctorRepository.existsById(data.doctorId())) {
            throw new CustomValidationException("Doctor not found");
        }

        var patient = patientRepository.getReferenceById(data.patientId());
        var doctor = chooseDoctor(data);
        var appointment = new Appointment(null, doctor, patient, data.date());
        appointmentRepository.save(appointment);
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
