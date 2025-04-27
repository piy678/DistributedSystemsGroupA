package org.example.dsproject.restapi.repository;

import org.example.dsproject.restapi.model.UsageData;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UsageDataRepository {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/energydb";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "deinpasswort"; // ändere auf dein echtes Passwort

    public List<UsageData> findByHourBetween(LocalDateTime start, LocalDateTime end) {
        List<UsageData> usageDataList = new ArrayList<>();
        String sql = "SELECT * FROM usage WHERE hour BETWEEN ? AND ? ORDER BY hour";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, Timestamp.valueOf(start));
            stmt.setTimestamp(2, Timestamp.valueOf(end));

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                UsageData usageData = new UsageData(
                        rs.getLong("id"),
                        rs.getDouble("community_produced"),
                        rs.getDouble("community_used"),
                        rs.getDouble("grid_used"),
                        rs.getTimestamp("hour").toLocalDateTime()
                );
                usageDataList.add(usageData);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usageDataList;
    }
}
