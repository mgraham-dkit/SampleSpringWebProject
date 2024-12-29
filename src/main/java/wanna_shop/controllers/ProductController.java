package wanna_shop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wanna_shop.model.Product;
import wanna_shop.persistence.ProductDao;
import wanna_shop.persistence.ProductDaoImpl;

import java.util.List;

@Controller
public class ProductController {
    @GetMapping("/viewProducts")
    public String viewProducts(Model model){
        ProductDao prodDao = new ProductDaoImpl("database.properties");
        List<Product> products = prodDao.getAllProducts();

        model.addAttribute("products", products);
        return "productList";
    }

    @GetMapping("/getProduct")
    public String getProduct(@RequestParam(name="productCode") String productCode, Model model){
        if(productCode == null || productCode.isBlank()){
            String errorMessage = "Valid product code must be provided.";
            model.addAttribute("errMsg", errorMessage);
            return "error";
        }

        ProductDao productDao = new ProductDaoImpl("database.properties");
        Product product = productDao.getById(productCode);

        if(product != null) {
            model.addAttribute("product", product);
        }else{
            model.addAttribute("prodCode", productCode);
        }

        return "viewProduct";
    }
}
