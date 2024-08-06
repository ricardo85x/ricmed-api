package med.ric.api.domain.appointment;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Boolean existsByCanceledFalseAndDoctorIdAndDate(Long doctorId, LocalDateTime date);

    Page<Appointment> findAllByCanceledFalse(Pageable pagination);

    Boolean existsByCanceledFalseAndPatientIdAndDateBetween(Long patientId, LocalDateTime firstHour, LocalDateTime lastHour);
}
