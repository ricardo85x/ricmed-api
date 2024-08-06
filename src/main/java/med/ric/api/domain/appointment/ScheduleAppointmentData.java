package med.ric.api.domain.appointment;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.ric.api.domain.doctor.Speciality;

import java.time.LocalDateTime;

public record ScheduleAppointmentData(
        Long doctorId,

        @NotNull
        Long patientId,

        @NotNull
        @Future
        LocalDateTime date,

        Speciality speciality) {
}
