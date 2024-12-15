package wanna_shop.model;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
public class Product {
    @EqualsAndHashCode.Include
    private String productCode;
    private String productName;
    private String productDescription;
    private int quantityInStock;
    private double costPrice;
    private double retailPrice;
}
