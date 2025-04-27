package org.example.dsproject.restapi.repository;

import org.example.dsproject.restapi.model.CurrentPercentage;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class CurrentPercentageRepository {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/energydb";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "password";

    public CurrentPercentage findTopByOrderByHourDesc() {
        String sql = "SELECT * FROM current_percentage ORDER BY hour DESC LIMIT 1";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return new CurrentPercentage(
                        rs.getLong("id"),
                        rs.getDouble("community_depleted"),
                        rs.getDouble("grid_portion"),
                        rs.getTimestamp("hour").toLocalDateTime()
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
