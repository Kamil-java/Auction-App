package pl.kamil.bak.project.auctionsite.product.dto;

import pl.kamil.bak.project.auctionsite.user.model.User;

import java.math.BigDecimal;

public class ProductDto {
    private String name;
    private String description;
    private User user;
    private BigDecimal price;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
