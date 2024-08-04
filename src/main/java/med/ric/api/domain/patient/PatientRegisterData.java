package med.ric.api.domain.patient;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.ric.api.domain.address.AddressData;

public record PatientRegisterData(
    @NotBlank
    String name,
    @NotBlank
    @Email
    String email,
    @NotBlank
    String phone,
    @NotBlank
    @Pattern(regexp = "\\d{11}")
    String cpf,
    @NotNull @Valid
    AddressData address
) {
}
