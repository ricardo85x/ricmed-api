package med.ric.api.patient;

public record PatientListData(Long id, String name, String email, String phone, String cpf) {
    public PatientListData(Patient patient) {
        this(
            patient.getId(),
            patient.getName(),
            patient.getEmail(),
            patient.getPhone(),
            patient.getCpf()
        );
    }
}
