package pl.kamil.bak.project.auctionsite.user.dto;

public class LocationDto {
    private String province;
    private String city;
    private AddressDto addressDto;

    public LocationDto(String province, String city, AddressDto addressDto) {
        this.province = province;
        this.city = city;
        this.addressDto = addressDto;
    }

    public LocationDto() {
    }

    public AddressDto getAddressDto() {
        return addressDto;
    }

    public void setAddressDto(AddressDto addressDto) {
        this.addressDto = addressDto;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
