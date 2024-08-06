package med.ric.api.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.ric.api.domain.appointment.CancelAppointmentData;
import med.ric.api.domain.appointment.DetailsAppointmentData;
import med.ric.api.domain.appointment.ScheduleAppointmentService;
import med.ric.api.domain.appointment.ScheduleAppointmentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CancellationException;

@RestController
@RequestMapping("/appointments")
@SecurityRequirement(name = "bearer-key")
public class AppointmentController {

    @Autowired
    private ScheduleAppointmentService scheduleService;

    @PostMapping
    @Transactional
    public ResponseEntity<DetailsAppointmentData> schedule(@RequestBody @Valid ScheduleAppointmentData data) {
        var dto = scheduleService.schedule(data);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<Void> cancel(@RequestBody @Valid CancelAppointmentData data) {
        scheduleService.cancel(data);
        return ResponseEntity.noContent().build();
    }
}
