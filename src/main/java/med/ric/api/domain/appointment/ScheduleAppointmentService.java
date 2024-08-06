package med.ric.api.domain.appointment;

import med.ric.api.domain.CustomValidationException;
import med.ric.api.domain.appointment.validations.cancel.CancelAppointmentValidatorInterface;
import med.ric.api.domain.appointment.validations.schedule.ScheduleAppointmentValidatorInterface;
import med.ric.api.domain.doctor.Doctor;
import med.ric.api.domain.doctor.DoctorRepository;
import med.ric.api.domain.patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    private List<ScheduleAppointmentValidatorInterface> scheduleValidators;

    @Autowired
    private List<CancelAppointmentValidatorInterface> cancelValidators;

    public DetailsAppointmentData schedule(ScheduleAppointmentData data) {

        if(!patientRepository.existsById(data.patientId())){
            throw new CustomValidationException("Patient not found");
        }

        if (data.doctorId() != null && !doctorRepository.existsById(data.doctorId())) {
            throw new CustomValidationException("Doctor not found");
        }

        scheduleValidators.forEach(validator -> validator.validate(data));

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

    public void cancel(CancelAppointmentData data) {
        var foundAppointment = appointmentRepository.findById(data.appointmentId());

        if(foundAppointment.isEmpty()){
            throw new CustomValidationException("Appointment not found");
        }

        var appointment = foundAppointment.get();

        cancelValidators.forEach(validator -> {
            System.out.println("E ai?");
            System.out.println(validator.getClass().getName());
            validator.validate(appointment);
        });

        if (data.reason() == null) {
            throw new CustomValidationException("Reason is required to cancel appointment");
        }

        appointment.setCanceled(true);
        appointment.setCanceledAt(LocalDateTime.now());
        appointment.setCanceledReason(data.reason());
        appointmentRepository.save(appointment);

    }
}
