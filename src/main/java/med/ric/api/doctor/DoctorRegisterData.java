package med.ric.api.doctor;

import med.ric.api.address.AddressData;

public record DoctorRegisterData(String name, String email, String crm, Speciality speciality, AddressData address ) {
}
