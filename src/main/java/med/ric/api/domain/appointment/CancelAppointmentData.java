package med.ric.api.domain.appointment;

public record CancelAppointmentData(Long appointmentId, AppointmentCancelReason reason) { }
