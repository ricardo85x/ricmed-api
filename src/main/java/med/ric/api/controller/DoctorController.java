package med.ric.api.controller;

import med.ric.api.doctor.DoctorRegisterData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("doctors")
public class DoctorController {

    @PostMapping
    public void register(@RequestBody DoctorRegisterData data) {
        System.out.println(data);
    }
}
