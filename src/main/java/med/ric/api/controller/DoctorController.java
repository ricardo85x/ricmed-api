package med.ric.api.controller;

import jakarta.validation.Valid;
import med.ric.api.doctor.Doctor;
import med.ric.api.doctor.DoctorListData;
import med.ric.api.doctor.DoctorRegisterData;
import med.ric.api.doctor.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("doctors")
public class DoctorController {

    @Autowired
    private DoctorRepository repository;

    @PostMapping
    @Transactional
    public void register(@RequestBody @Valid DoctorRegisterData data) {
        repository.save(new Doctor(data));
    }

    @GetMapping
    public List<DoctorListData> list() {
        return repository.findAll().stream().map(DoctorListData::new).toList();
    }
}
