package pl.kamil.bak.project.auctionsite.product.domian.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.kamil.bak.project.auctionsite.product.dao.ProductRepository;
import pl.kamil.bak.project.auctionsite.product.dto.ProductDto;
import pl.kamil.bak.project.auctionsite.product.model.Product;
import pl.kamil.bak.project.auctionsite.user.model.User;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ProductService(ProductRepository productRepository, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    public List<Product> getUserByIdProduct(User user){
            return productRepository.findProductByUser(user);
    }

    public List<Product> getAll(){
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(long id){
        return productRepository.findById(id);
    }

    public Product addNewProduct(ProductDto productDto){
        Product product = new Product();
        modelMapper.map(productDto,product);
        return productRepository.save(product);
    }

    public Product update(ProductDto productDto, Product product){
        modelMapper.map(productDto, product);
        return productRepository.save(product);
    }

    public void deleteById(long id){
        productRepository.deleteById(id);
    }

}
