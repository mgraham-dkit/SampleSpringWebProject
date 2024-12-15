package wanna_shop.model;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
public class OrderLine {
    @EqualsAndHashCode.Include
    private int orderNumber;
    @EqualsAndHashCode.Include
    private String productCode;
    private int quantityOrdered;
    private double priceEach;
    private int orderLineNumber;
}
