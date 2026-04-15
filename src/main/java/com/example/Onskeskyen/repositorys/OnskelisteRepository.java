package com.example.Onskeskyen.repositorys;

import com.example.Onskeskyen.models.Onskeliste;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OnskelisteRepository {

    private final String dbUrl = System.getenv("DB_URL");
    private final String username = System.getenv("DB_USER");
    private final String password = System.getenv("DB_PASSWORD");

    public List<Onskeliste> findByBrugerId(int brugerId) {
        List<Onskeliste> lister = new ArrayList<>();
        String sql = "SELECT * FROM ønskeliste WHERE ejer_bruger_id = ?";

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, brugerId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Timestamp ts = resultSet.getTimestamp("oprettet_dato");

                Onskeliste liste = new Onskeliste(
                        resultSet.getInt("ønskeliste_id"),
                        resultSet.getInt("ejer_bruger_id"),
                        resultSet.getString("titel"),
                        resultSet.getString("beskrivelse"),
                        resultSet.getBoolean("offentlig"),
                        resultSet.getString("delingslink"),
                        ts != null ? ts.toLocalDateTime() : null
                );

                lister.add(liste);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lister;
    }

    public void save(Onskeliste liste) {
        String sql = "INSERT INTO ønskeliste (ejer_bruger_id, titel, beskrivelse, offentlig, delingslink, oprettet_dato) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, liste.getBrugerId());
            statement.setString(2, liste.getTitel());
            statement.setString(3, liste.getBeskrivelse());
            statement.setBoolean(4, liste.isOffentlig());
            statement.setString(5, liste.getDelingslink());
            statement.setTimestamp(6, Timestamp.valueOf(liste.getOprettetDato()));

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateById(int id, String titel, String beskrivelse, boolean offentlig) {
        String sql = "UPDATE ønskeliste SET titel = ?, beskrivelse = ?, offentlig = ? WHERE ønskeliste_id = ?";

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, titel);
            statement.setString(2, beskrivelse);
            statement.setBoolean(3, offentlig);
            statement.setInt(4, id);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteById(int id) {
        String sql = "DELETE FROM ønskeliste WHERE ønskeliste_id = ?";

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}