package data;

import static data.DB.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import logic.User;

/**
 *
 * @author thomas
 */
public class DataMapper {
    
    private static final String GET_ALL_USERS = "SELECT id, username, password FROM users";
    private static final String GET_USER_BY_ID = "SELECT username, password FROM users WHERE id = ?";
    private static final String VALIDATE_USER = "SELECT username, password FROM users WHERE username = ? AND password = ?";
    private static final String UPDATE_USER = "UPDATE wpss2018e.users SET password = ? WHERE id = ?";
    private static final String REGISTER_USER = "INSERT INTO wpss2018e.users (username, password) VALUES (?,?)";
    private static final String DELETE_USER = " DELETE FROM wpss2018e.users WHERE id = ?";
    
   


    
    public List<User> getAllUsers() {
        List<User> users = new ArrayList();
        User user = null;
        try {
            
            Connection conn = getConnection(); //create the connections to the database
            PreparedStatement pstmt = conn.prepareStatement(GET_ALL_USERS); //Create the statement to be run on the database
            ResultSet rs = pstmt.executeQuery(); //Actually execute the statement on the database.
            while (rs.next()) { //loop through each row in the dataset
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                System.out.println("username: " + username);
                user = new User(id, username, password);
                users.add(user); //add each new user to the list
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return users;
    }
    
    public User getUser(int id){
        try{
            PreparedStatement pstmt = getConnection().prepareStatement(GET_USER_BY_ID); //Her bruges en konstant der er defineret øverst på siden.
            pstmt.setInt(1, id); //Dette sql statement har en placeholder (?) som får sat en variabel ind på plads 1 i vores PreparedStatement
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){ //Hvis next() returner true betyder det at der var en user at hente på det givne id
                String username = rs.getString("username");
                String password = rs.getString("password");
                User user = new User(id, username, password);
                return user;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }
    
//    TODO: implementér de to metoder: registeruser og updateUser ved at bruge sql statements øverst på siden (lagt i konstanter)
    
//    public void registerUser(User user){}
//    public void updateUser(User user){}
    public void deleteUser(int id){
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(DELETE_USER);
            pstmt.setInt(1, id);
            pstmt.executeUpdate(); // her bruges executeUpdate istedet for executeQuery (det gælder ved update, create og delete operationer).
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean validateUser(String username, String password){
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(VALIDATE_USER);
            pstmt.setString(1, username); 
            pstmt.setString(2, password); //her er 2 placeholders der får sat værdierne username og password fra methodens input parametre.
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) //hvis der er en next row her betyder det at login var succesfuld.
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    //Allways test all methods you create:
    public static void main(String[] args) {
//        new DataMapper().getAllUsers().forEach((user)->{System.out.println(user);});
        boolean isValid = new DataMapper().validateUser("carlos", "passwor456");
        System.out.println(isValid);
    }
}
