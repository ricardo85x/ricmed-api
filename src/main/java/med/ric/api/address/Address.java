package med.ric.api.address;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    private String street;
    private String neighborhood;
    private String zipCode;
    private String city;
    private String state;
    private String addressDetails;
    private String number;

    public Address(AddressData address) {
        this.street = address.street();
        this.neighborhood = address.neighborhood();
        this.zipCode = address.zipCode();
        this.city = address.city();
        this.state = address.state();
        this.addressDetails = address.addressDetails();
        this.number = address.number();
    }

    public void updateInfo(AddressData address) {
        if (address.street() != null) this.street = address.street();
        if (address.neighborhood() != null) this.neighborhood = address.neighborhood();
        if (address.zipCode() != null) this.zipCode = address.zipCode();
        if (address.city() != null) this.city = address.city();
        if (address.state() != null) this.state = address.state();
        if (address.addressDetails() != null) this.addressDetails = address.addressDetails();
        if (address.number() != null) this.number = address.number();
    }
}
