package med.ric.api.doctor;

import med.ric.api.address.Address;

public record DoctorDetailsData(Long id, String name, String email, String crm, String phone, Speciality speciality, Address address) {

    public DoctorDetailsData(Doctor doctor) {
       this(doctor.getId(), doctor.getName(), doctor.getEmail(), doctor.getCrm(), doctor.getPhone(), doctor.getSpeciality(), doctor.getAddress());
    }
}
