package wanna_shop.persistence;

import lombok.extern.slf4j.Slf4j;
import wanna_shop.model.User;

import java.sql.*;
import java.time.LocalDateTime;

@Slf4j
public class UserDaoImpl extends MySQLDao implements UserDao{
    public UserDaoImpl(){
        super();
    }

    public UserDaoImpl(Connection conn){
        super(conn);
    }

    public UserDaoImpl(String propertiesFilename){
        super(propertiesFilename);
    }

    @Override
    public User login(String username, String password) {
        if(username == null || username.isBlank()){
            throw new IllegalArgumentException("Username required to log in");
        }
        if(password == null || password.isBlank()){
            throw new IllegalArgumentException("Password required to log in");
        }

        Connection conn = super.getConnection();
        User result = null;
        // Collation here changes the comparison of the password from case insensitive to case sensitive!
        String sql = "SELECT * FROM users where username = ? AND password COLLATE utf8mb4_bin = ?";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, username);
            ps.setString(2, password);

            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    result = User.builder()
                            .username(rs.getString("username"))
                            .email(rs.getString("email"))
                            .firstName(rs.getString("firstName"))
                            .lastName(rs.getString("lastName"))
                            .isAdmin(rs.getBoolean("isAdmin"))
                            .build();
                }
            }
        }catch(SQLException e){
            log.error("An error occurred when logging in user with username: " + username, e);
        }

        super.freeConnection(conn);
        return result;
    }

    @Override
    public boolean register(User user) {
        if(!validateUser(user)){
            throw new IllegalArgumentException("Username, password and email must be supplied for registration.");
        }

        Connection conn = super.getConnection();
        boolean added = false;
        String sql = "INSERT INTO users (username, password, firstName, lastName, email, isAdmin) VALUES(?, ?, ?, ?, " +
                "?, ?)";
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFirstName());
            ps.setString(4, user.getLastName());
            ps.setString(5, user.getEmail());
            ps.setBoolean(6, user.isAdmin());

            int rowsAffected = ps.executeUpdate();
            if(rowsAffected > 0){
                added = true;
            }
        }catch(SQLIntegrityConstraintViolationException e){
            log.error("A foreign key constraint failed when registering new user with username: " + user.getUsername(), e);
        }catch(SQLException e){
            log.error("An error occurred when registering new user with username: " + user.getUsername(), e);
        }

        super.freeConnection(conn);
        return added;
    }

    private boolean validateUser(User u){
        if(u == null){
            return false;
        }

        if(u.getUsername() == null || u.getUsername().isBlank()){
            return false;
        }

        if(u.getPassword() == null || u.getPassword().isBlank()){
            return false;
        }

        if(u.getEmail() == null || u.getEmail().isBlank()){
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        User newUser = User.builder()
                .username("MGraham")
                .password("password")
                .firstName("Michelle")
                .lastName("Graham")
                .email("mgraham@dkit.ie")
                .isAdmin(false)
                .build();

        UserDao userDao = new UserDaoImpl("database.properties");
        boolean registered = userDao.register(newUser);
        if(registered) {
            System.out.println("Success! :D");
        }else{
            System.out.println("Failed :(");
        }

        System.out.println("Login attempted: " + userDao.login("mgraham", "password1"));
    }
}
