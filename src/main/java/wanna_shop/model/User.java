package wanna_shop.model;

import lombok.*;

@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @EqualsAndHashCode.Include
    private String username;
    @ToString.Exclude
    private String password;
    private String firstName;
    private String lastName;
    private boolean isAdmin;
    private String email;
}
