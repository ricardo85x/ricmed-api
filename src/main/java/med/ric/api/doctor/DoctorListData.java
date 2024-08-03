package med.ric.api.doctor;

public record DoctorListData(Long id, String name, String email, String crm, Speciality speciality) {
    public DoctorListData(Doctor doctor) {
        this(doctor.getId(), doctor.getName(), doctor.getEmail(), doctor.getCrm(), doctor.getSpeciality());
    }
}
