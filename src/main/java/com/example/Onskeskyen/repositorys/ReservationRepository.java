package com.example.Onskeskyen.repositorys;

import com.example.Onskeskyen.models.Reservation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ReservationRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Reservation reservation) {
        String sql = "INSERT INTO reservation (item_id, bruger_id, antal) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection("");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reservation.getItemId());
            stmt.setInt(2, reservation.getBrugerId());
            stmt.setInt(3, reservation.getAntal());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Reservation> findByItem(int item_id) {
        List<Reservation> liste = new ArrayList<>();
        String sql = "SELECT * FROM reservation WHERE item_id = ?";

        try (Connection conn = DriverManager.getConnection("");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, item_id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                liste.add(new Reservation(
                        rs.getInt("item_id"),
                        rs.getInt("bruger_id"),
                        rs.getInt("antal")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return liste;
    }

    public void delete(int item_id, int bruger_id) {
        String sql = "DELETE FROM reservation WHERE item_id = ? AND bruger_id = ?";

        try (Connection conn = DriverManager.getConnection("");
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, item_id);
            stmt.setInt(2, bruger_id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}