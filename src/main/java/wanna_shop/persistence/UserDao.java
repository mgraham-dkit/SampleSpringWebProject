package wanna_shop.persistence;
import wanna_shop.model.User;

public interface UserDao {
    User login(String username, String password);
    boolean register(User user);
}
