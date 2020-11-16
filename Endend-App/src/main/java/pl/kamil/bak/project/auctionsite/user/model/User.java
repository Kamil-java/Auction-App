package pl.kamil.bak.project.auctionsite.user.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.kamil.bak.project.auctionsite.product.model.Product;
import pl.kamil.bak.project.auctionsite.user.model.enums.Role;
import pl.kamil.bak.project.auctionsite.user.model.enums.Status;
import pl.kamil.bak.project.auctionsite.user.model.enums.Type;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Email
    @Size(max = 20, min = 8)
    private String email;
    @NotBlank
    @Size(max = 26, min = 3)
    private String userName;
    @NotBlank
    @Size(min = 4)
    private String password;
    @OneToOne
    @JoinColumn(name = "location_id", referencedColumnName = "id")
    private Location location;
    private LocalDateTime date;
    private String status;
    private String type;
    private String role;
    @JsonManagedReference
    @OneToMany(targetEntity = Product.class, mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> product = new ArrayList<>();



    public User() {
        this.date = LocalDateTime.now();
        this.status = Status.INACTIVE.name();
        this.type = Type.NORMAL.name();
        this.role = Role.USER.name();
    }

    public Long getId() {
        return id;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(role), new SimpleGrantedAuthority(type));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !Status.BLOCKED.name().equals(status);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return Status.ACTIVE.name().equals(status);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
