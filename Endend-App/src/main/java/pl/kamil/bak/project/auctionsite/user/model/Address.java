package pl.kamil.bak.project.auctionsite.user.model;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "adres")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 30, min = 3)
    private String street;
    @Max(300)
    @Min(1)
    @PositiveOrZero
    private int houseNumber;
    @NotBlank(message = "Zip code have to from 5 to 6 sign")
    @Size(min = 5, max = 6)
    private String zipCode;


    public Address() {
    }

    public Long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
