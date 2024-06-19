// UserDAO.java
import java.sql.*;

public class UserDAO {
    private Connection connect() {
        String url = "jdbc:sqlite:face_authentication.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void insertUser(User user) {
        String sql = "INSERT INTO Users(username, password) VALUES(?, ?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM Users WHERE username = ?";
        User user = null;

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    // Implement other CRUD methods as needed...
}

// FaceDataDAO.java
import java.sql.*;

public class FaceDataDAO {
    private Connection connect() {
        String url = "jdbc:sqlite:face_authentication.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void insertFaceData(FaceData faceData) {
        String sql = "INSERT INTO FaceData(userId, faceTemplate) VALUES(?, ?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, faceData.getUserId());
            pstmt.setBytes(2, faceData.getFaceTemplate());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public FaceData getFaceDataByUserId(int userId) {
        String sql = "SELECT * FROM FaceData WHERE userId = ?";
        FaceData faceData = null;

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                faceData = new FaceData();
                faceData.setUserId(rs.getInt("userId"));
                faceData.setFaceTemplate(rs.getBytes("faceTemplate"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return faceData;
    }

    // Implement other CRUD methods as needed...
}

