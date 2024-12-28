package wanna_shop.persistence;

import lombok.extern.slf4j.Slf4j;
import wanna_shop.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ProductDaoImpl extends MySQLDao implements ProductDao{
    public ProductDaoImpl(){
        super();
    }

    public ProductDaoImpl(Connection conn){
        super(conn);
    }

    public ProductDaoImpl(String propertiesFilename){
        super(propertiesFilename);
    }

    @Override
    public Product getById(String productCode) {
        if(productCode == null){
            throw new IllegalArgumentException("productCode cannot be null");
        }

        Product product = null;
        Connection conn = super.getConnection();
        String sql = "SELECT * from products WHERE productCode = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, productCode);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    product = mapRow(rs);
                }
            }
        }catch(SQLException e){
            log.error("SQLException occurred when retrieving product matching productCode: " + productCode, e);
        }
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        Connection conn = super.getConnection();
        String sql = "SELECT * from products";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    Product product = mapRow(rs);
                    products.add(product);
                }
            }
        }catch(SQLException e){
            log.error("SQLException occurred when retrieving all products from database", e);
        }
        return products;
    }

    @Override
    public List<Product> search(String term) {
        if(term == null){
            throw new IllegalArgumentException("Search term cannot be null");
        }

        List<Product> products = new ArrayList<>();
        Connection conn = super.getConnection();
        String sql = "SELECT * from products WHERE productName LIKE ? OR productDescription LIKE ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, "%" + term + "%");
            ps.setString(2, "%" + term + "%");
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    Product product = mapRow(rs);
                    products.add(product);
                }
            }
        }catch(SQLException e){
            log.error("SQLException occurred when retrieving all products containing search term: " + term, e);
        }
        return products;
    }

    private Product mapRow(ResultSet rs) throws SQLException {
        Product product =
                Product.builder()
                        .productCode(rs.getString("productCode"))
                        .productName(rs.getString(
                        "productName"))
                        .productDescription(rs.getString("productDescription"))
                        .quantityInStock(rs.getInt("quantityInStock"))
                        .retailPrice(rs.getDouble("retailPrice"))
                        .costPrice(rs.getDouble("costPrice"))
                        .build();
        return product;
    }

    public static void main(String[] args) {
        ProductDao productDao = new ProductDaoImpl("database.properties");
        Product product = productDao.getById("b1212");
        //System.out.println(product);

        List<Product> matches = productDao.getAllProducts();
        for(Product p: matches){
            System.out.println(p);
        }
    }
}
