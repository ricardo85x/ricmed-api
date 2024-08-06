package med.ric.api.domain.doctor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Page<Doctor> findAllByActiveTrue(Pageable pagination);

    @Query("""
            SELECT d FROM Doctor d
            WHERE  d.active = true
            AND d.speciality = :speciality
            AND d.id not in (
                select a.doctor.id from Appointment a
                where a.date = :date
            )
            ORDER by rand()
            LIMIT 1
    """)
    Doctor findRandomAvailableDoctorBySpecialty(LocalDateTime date, Speciality speciality);

    @Query("SELECT d.active FROM Doctor d WHERE d.id = :id")
    Boolean findActiveById(Long id);
}
