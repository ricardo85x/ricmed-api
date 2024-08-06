package med.ric.api.domain.appointment;


import jakarta.persistence.*;
import lombok.*;
import med.ric.api.domain.doctor.Doctor;
import med.ric.api.domain.patient.Patient;

import java.awt.desktop.UserSessionEvent;
import java.time.LocalDateTime;

@Table(name = "appointments")
@Entity(name = "Appointment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Enumerated(EnumType.STRING)
    private AppointmentCancelReason canceledReason;

    private Boolean canceled;

    private LocalDateTime canceledAt;

    private LocalDateTime date;

    public Appointment(Object o, Doctor doctor, Patient patient, LocalDateTime date) {
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
        this.canceled = false;
    }
}
