package pl.kamil.bak.project.auctionsite.product.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kamil.bak.project.auctionsite.product.model.Product;
import pl.kamil.bak.project.auctionsite.user.model.User;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findProductByUser(User user);
}
