package med.ric.api.domain.doctor;

import jakarta.validation.constraints.NotNull;
import med.ric.api.domain.address.AddressData;

public record DoctorUpdateData(
        @NotNull
        Long id,
        String name,
        String phone,
        AddressData address) {
}
