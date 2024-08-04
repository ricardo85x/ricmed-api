package med.ric.api.domain.patient;

import jakarta.validation.constraints.NotNull;
import med.ric.api.domain.address.AddressData;

public record PatientUpdateData(
        @NotNull
        Long id,
        String name,
        String phone,
        AddressData address
) {
}
