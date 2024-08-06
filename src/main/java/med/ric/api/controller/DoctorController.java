package med.ric.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.ric.api.domain.doctor.*;
import med.ric.api.domain.doctor.Doctor;
import med.ric.api.domain.doctor.DoctorDetailsData;
import med.ric.api.domain.doctor.DoctorListData;
import med.ric.api.domain.doctor.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("doctors")
@SecurityRequirement(name = "bearer-key")
public class DoctorController {

    @Autowired
    private DoctorRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity register(@RequestBody @Valid DoctorRegisterData data, UriComponentsBuilder uriBuilder) {

        var doctor = new Doctor(data);
        repository.save(doctor);

        var uri = uriBuilder.path("/doctors/{id}").buildAndExpand(doctor.getId()).toUri();
        var dto = new DoctorDetailsData(doctor);

        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping
    public ResponseEntity<Page<DoctorListData>> list(@PageableDefault(size = 10, sort = { "name" }) Pageable pagination) {
        var page =  repository.findAllByActiveTrue(pagination).map(DoctorListData::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DoctorDetailsData> update(@RequestBody @Valid DoctorUpdateData data) {
        var doctor = repository.getReferenceById(data.id());
        doctor.updateInfo(data);
        return ResponseEntity.ok(new DoctorDetailsData(doctor));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        var doctor = repository.getReferenceById(id);
        doctor.deactivate();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDetailsData> details(@PathVariable Long id) {
        var doctor = repository.getReferenceById(id);
        return ResponseEntity.ok(new DoctorDetailsData(doctor));
    }
}
