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
        // eger databasede parametrine kelgen firstnamege okshosh adam bar bolso
        // anda true kaitarsyn
        // jok bolso anda false kaitarsyn.

        String sql1 = """
                SELECT * FROM users
                WHERE name LIKE '?%';
                """ ;
        String sql2 = """
                SELECT * FROM users
                WHERE name LIKE '%?%';
                """;
        String sql3 = """
                SELECT * FROM users
                WHERE name LIKE '?_';
                """;
        String sql4 = """
                SELECT * FROM users
                WHERE name LIKE '_?';
                """;

        String sql5 = """       
                SELECT * FROM users
                WHERE name LIKE '_?_';
                """;

            try (PreparedStatement ps = Util.getConnection().prepareStatement(sql1)) {
                ps.setString(1, firstName);
                ResultSet rs = ps.executeQuery(sql1);
                rs.next();
                if (rs.getString("name").toLowerCase().contains(firstName)) {
                    return true;
                } else {
                    try (PreparedStatement psa = Util.getConnection().prepareStatement(sql2)) {
                        psa.setString(1, firstName);
                        ResultSet rss = psa.executeQuery(sql2);
                        rss.next();
                        if (rss.getString("name").toLowerCase().contains(firstName)) {
                            return true;
                        } else {
                            try (PreparedStatement psq = Util.getConnection().prepareStatement(sql3)) {
                                psq.setString(1, firstName);
                                ResultSet rse = psq.executeQuery(sql3);
                                rse.next();
                                if (rse.getString("name").toLowerCase().contains(firstName)) {
                                    return true;
                                } else {
                                    try (PreparedStatement pst = Util.getConnection().prepareStatement(sql4)) {
                                        pst.setString(1, firstName);
                                        ResultSet rsy = psa.executeQuery(sql4);
                                        rsy.next();
                                        if (rsy.getString("name").toLowerCase().contains(firstName)) {
                                            return true;
                                        } else {
                                            try (PreparedStatement psi = Util.getConnection().prepareStatement(sql5)) {
                                                psi.setString(1, firstName);
                                                ResultSet rso = psi.executeQuery(sql5);
                                                rso.next();
                                                if (rso.getString("name").toLowerCase().contains(firstName)) {
                                                    return true;
                                                }
                                            } catch (SQLException er) {
                                                er.printStackTrace();
                                            }
                                        }
                                    } catch (SQLException er) {
                                        er.printStackTrace();
                                    }
                                }

                            } catch(SQLException e){
                                e.printStackTrace();
                            }
                        }
                    } catch (SQLException er) {
                        er.printStackTrace();
                    }
                }

                } catch(SQLException e){
                    e.printStackTrace();
            }


        return false;
    }
}