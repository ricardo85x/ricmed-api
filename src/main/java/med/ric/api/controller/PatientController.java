package med.ric.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.ric.api.domain.patient.Patient;
import med.ric.api.domain.patient.PatientDetailsData;
import med.ric.api.domain.patient.PatientListData;
import med.ric.api.domain.patient.PatientRepository;
import med.ric.api.domain.patient.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("patients")
@SecurityRequirement(name = "bearer-key")
public class PatientController {

    @Autowired
    private PatientRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<PatientDetailsData> register(@RequestBody @Valid PatientRegisterData data, UriComponentsBuilder uriBuilder) {
        var patient = new Patient(data);
        repository.save(patient);

        var uri = uriBuilder.path("/patients/{id}").buildAndExpand(patient.getId()).toUri();
        var dto = new PatientDetailsData(patient);

        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping
    public ResponseEntity<Page<PatientListData>> list(@PageableDefault(size = 10, sort = { "name" }) Pageable pagination) {
        var page = repository.findAllByActiveTrue(pagination).map(PatientListData::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<PatientDetailsData> update(@RequestBody @Valid PatientUpdateData data) {
        var patient = repository.getReferenceById(data.id());
        patient.updateInfo(data);
        return ResponseEntity.ok(new PatientDetailsData(patient));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        var patient = repository.getReferenceById(id);
        patient.deactivate();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDetailsData> details(@PathVariable Long id) {
        var patient = repository.getReferenceById(id);
        return ResponseEntity.ok(new PatientDetailsData(patient));
    }
}
