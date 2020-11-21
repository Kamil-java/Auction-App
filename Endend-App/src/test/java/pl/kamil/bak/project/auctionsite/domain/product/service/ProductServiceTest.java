package pl.kamil.bak.project.auctionsite.domain.product.service;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.server.ResponseStatusException;
import pl.kamil.bak.project.auctionsite.domain.product.dao.ProductRepository;
import pl.kamil.bak.project.auctionsite.domain.product.dto.ProductDto;
import pl.kamil.bak.project.auctionsite.model.productEntity.Product;
import pl.kamil.bak.project.auctionsite.model.userEntity.User;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ProductService productService;


    @BeforeEach
    public void init() {
        given(productRepository.findAll()).willReturn(prepareData());
        given(productRepository.findProductByUserId(2L)).willReturn(prepareData());
        given(productRepository.findById(2L)).willReturn(java.util.Optional.ofNullable(prepareData().get(2)));
        given(productRepository.save(any())).willReturn(convertDto());

    }


    @Test
    @DisplayName("should be return product by Id for User")
    void getUserByIdProduct() {
        User user = new User();
        user.setId(2L);
        List<Product> userByIdProduct = productService.getUserByIdProduct(user);
        Assert.assertThat(userByIdProduct, hasSize(5));

    }

    @Test
    @DisplayName("should be return all Array")
    void getAll() {
        List<Product> products = productService.getAll();
        Assert.assertThat(products, hasSize(5));
    }

    @Test
    @DisplayName("should be return product by id")
    void getProductById() {
        Product productById = productService.getProductById(2L);
        Assert.assertEquals(productById.getName(), "Skuter");
        Assert.assertEquals(productById.getDescription(), "Brak");
        Assert.assertEquals(productById.getPrice(), BigDecimal.valueOf(4000.00));
    }

    @Test
    @DisplayName("should be return throw")
    void getProductWithThrow() {
        User user = new User();
        user.setId(1L);
        assertThrows(ResponseStatusException.class, () -> productService.getProductById(1L));
        assertThrows(ResponseStatusException.class, () -> productService.getUserByIdProduct(user));
    }

    @Test
    @DisplayName("should be save product, don't have to be null")
    void addNewProduct() {
        Product product = productService.addNewProduct(prepareProductDto());
        Assertions.assertNotNull(product);
        assertEquals(product.getName(), prepareProductDto().getName());
        assertEquals(product.getDescription(), prepareProductDto().getDescription());
        assertEquals(product.getPrice(), prepareProductDto().getPrice());
    }

    @Test
    @DisplayName("should be update product, don't have to be null")
    void update() {
        Product update = productService.update(prepareProductDto(), convertDto());
        Assertions.assertNotNull(update);
        assertEquals(update.getName(), prepareProductDto().getName());
        assertEquals(update.getDescription(), prepareProductDto().getDescription());
        assertEquals(update.getPrice(), prepareProductDto().getPrice());
    }

    @Test
    void deleteById() {

    }

    private Product convertDto() {
        Product product = new Product();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.map(prepareProductDto(), product);
        return product;
    }

    private User prepareUser() {
        User user = new User();
        user.setEmail("abc@gamil.com");
        user.setUserName("abc");
        user.setPassword("123");
        return user;
    }

    private ProductDto prepareProductDto() {
        ProductDto productDto = new ProductDto();
        productDto.setName("wąż");
        productDto.setDescription("zwierze");
        productDto.setUser(prepareUser());
        productDto.setPrice(BigDecimal.valueOf(200.00));
        return productDto;
    }

    private List<Product> prepareData() {
        return Arrays.asList(
                new Product("Buty", "Nowe", new User(), BigDecimal.valueOf(200.00)),
                new Product("Auto", "Stare", new User(), BigDecimal.valueOf(10000.00)),
                new Product("Skuter", "Brak", new User(), BigDecimal.valueOf(4000.00)),
                new Product("Laptop", "Używany", new User(), BigDecimal.valueOf(5000.00)),
                new Product("Telefon", "Nowy", new User(), BigDecimal.valueOf(2300.00))
        );
    }
}