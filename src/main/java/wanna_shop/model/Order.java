package wanna_shop.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
public class Order {
    @EqualsAndHashCode.Include
    private int orderNumber;
    private LocalDateTime orderDate;
    private String username;
}
