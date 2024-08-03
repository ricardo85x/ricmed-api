package med.ric.api.doctor;

import jakarta.validation.constraints.NotNull;
import med.ric.api.address.AddressData;

public record DoctorUpdateData(
        @NotNull
        Long id,
        String name,
        String phone,
        AddressData address) {
}
