package med.ric.api.controller;


import jakarta.validation.Valid;
import med.ric.api.domain.appointment.DetailsAppointmentData;
import med.ric.api.domain.appointment.ScheduleAppointmentService;
import med.ric.api.domain.appointment.ScheduleAppointmentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private ScheduleAppointmentService scheduleService;

    @PostMapping
    @Transactional
    public ResponseEntity schedule(@RequestBody @Valid ScheduleAppointmentData data) {
        var dto = scheduleService.schedule(data);
        return ResponseEntity.ok(dto);
    }


}
