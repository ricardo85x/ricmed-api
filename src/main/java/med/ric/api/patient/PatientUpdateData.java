package med.ric.api.patient;

import jakarta.validation.constraints.NotNull;
import med.ric.api.address.AddressData;

public record PatientUpdateData(
        @NotNull
        Long id,
        String name,
        String phone,
        AddressData address
) {
}
