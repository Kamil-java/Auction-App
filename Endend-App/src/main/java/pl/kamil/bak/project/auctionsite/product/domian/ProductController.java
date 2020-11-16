package pl.kamil.bak.project.auctionsite.product.domian;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.kamil.bak.project.auctionsite.product.domian.service.ProductService;
import pl.kamil.bak.project.auctionsite.product.dto.ProductDto;
import pl.kamil.bak.project.auctionsite.product.model.Product;
import pl.kamil.bak.project.auctionsite.user.domian.provider.UserSessionProvider;

import java.util.List;


@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;
    private final UserSessionProvider getPrincipal;

    public ProductController(ProductService productService, UserSessionProvider getPrincipal) {
        this.productService = productService;
        this.getPrincipal = getPrincipal;
    }

    @GetMapping("/own/products")
    public List<Product> getAllOwnProducts() {
        return productService.getUserByIdProduct(getPrincipal.getPrincipal());
    }

    @GetMapping("/all")
    public List<Product> getAll() {
        if (productService.getAll().isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return productService.getAll();
    }

    @GetMapping("/owner")
    public String getNameOwner() {
        return getPrincipal.getPrincipal().getUserName();
    }


    @PostMapping("/add/product")
    public Product addProduct(@RequestBody @Validated ProductDto productDto) {
        productDto.setUser(getPrincipal.getPrincipal());
        return productService.addNewProduct(productDto);
    }

    @PutMapping("/edit/product/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody @Validated ProductDto productDto, @PathVariable("id") long id) {
        Product product = productService.getProductById(id).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        });
        productService.getProductById(id).ifPresent(product1 -> {
            if (product1.getUser().getId().equals(getPrincipal.getPrincipal().getId())) {
                productDto.setUser(getPrincipal.getPrincipal());
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        });
        return ResponseEntity.ok(productService.update(productDto, product));
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable("id") long id) {
        productService.getProductById(id).ifPresent(product -> {
            if (product.getUser().getId().equals(getPrincipal.getPrincipal().getId())) {
                productService.deleteById(id);
            }
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        });
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }


}
