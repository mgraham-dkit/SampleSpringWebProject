package wanna_shop.persistence;

import wanna_shop.model.Product;

import java.util.List;

public interface ProductDao {
    Product getById(String productCode);
    List<Product> search(String term);
    List<Product> getAllProducts();
}
