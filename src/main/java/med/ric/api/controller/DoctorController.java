package med.ric.api.controller;

import med.ric.api.doctor.Doctor;
import med.ric.api.doctor.DoctorRegisterData;
import med.ric.api.doctor.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("doctors")
public class DoctorController {

    @Autowired
    private DoctorRepository repository;

    @PostMapping
    public void register(@RequestBody DoctorRegisterData data) {
        repository.save(new Doctor(data));
    }
}
