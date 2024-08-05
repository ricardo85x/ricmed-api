package med.ric.api.domain.appointment;

import java.time.LocalDateTime;

public record DetailsAppointmentData(Long id, Long doctorId, Long patientId, LocalDateTime date) {
}
