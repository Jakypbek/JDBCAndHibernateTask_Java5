package peaksoft.dao;

import peaksoft.model.User;
import peaksoft.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJdbcImpl implements UserDao {

    public UserDaoJdbcImpl() {

    }

    public void createUsersTable() {
        String sql = """
                    CREATE TABLE users(
                    id SERIAL PRIMARY KEY,
                    name VARCHAR NOT NULL,
                    last_name VARCHAR NOT NULL,
                    age SMALLINT NOT NULL);
                        """;

        try (Connection conn = Util.getConnection();
            Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void dropUsersTable() {
        String sql = """
                    DROP TABLE users;
                        """;

        try (Connection conn = Util.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = """
                    insert into users(name, last_name, age) values (?,?,?);
                        """;

        try (PreparedStatement conn = Util.getConnection().prepareStatement(sql)) {
             conn.setString(1, name);
             conn.setString(2, lastName);
             conn.setByte(3, age);
             conn.executeUpdate();

            System.out.println("User: " + name + " successfully added to the Base!");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String sql = """
                DELETE FROM users WHERE id = ?;
                """;
        try (PreparedStatement conn = Util.getConnection().prepareStatement(sql)) {
            conn.setLong(1, id);
            conn.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }


    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        String sql = """
                        SELECT * FROM users;
                    """;

        try (Connection connection = Util.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)
        ) {

            while (rs.next()) {
                User user = new User(rs.getLong("id"), rs.getString("name"),rs.getString("last_name"), rs.getByte("age"));
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return users;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM users *";

        try (PreparedStatement ps = Util.getConnection().prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean existsByFirstName(String firstName) {
        String sql = """
                    SELECT name FROM users 
                    WHERE name LIKE ?;
                    """;
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(sql)) {

            preparedStatement.setString(1,  "%" + firstName + "%");
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}