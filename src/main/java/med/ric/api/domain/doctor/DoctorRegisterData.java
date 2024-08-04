package med.ric.api.domain.doctor;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.ric.api.domain.address.AddressData;

public record DoctorRegisterData(
        @NotBlank(message = "Name is required")
        String name,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String phone,
        @NotBlank
        @Pattern(regexp = "\\d{4,6}")
        String crm,
        @NotNull
        Speciality speciality,
        @NotNull @Valid
        AddressData address ) {
}
