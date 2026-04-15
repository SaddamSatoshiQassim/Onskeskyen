package com.example.Onskeskyen.repositorys;

import com.example.Onskeskyen.models.Bruger;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class BrugerRepository {

    private String dbUrl = System.getenv("DB_URL");
    private String username = System.getenv("DB_USER");
    private String password = System.getenv("DB_PASSWORD");

    public List<Bruger> findAll() {

        System.out.println("DB_URL: " + dbUrl);
        System.out.println("DB_USER: " + username);
        System.out.println("DB_PASSWORD: " + password);

        List<Bruger> brugere = new ArrayList<>();
        String sql = "SELECT * FROM bruger";

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Timestamp ts = resultSet.getTimestamp("oprettet_dato");
                LocalDateTime dato = null;

                if (ts != null) {
                    dato = ts.toLocalDateTime();
                }

                Bruger bruger = new Bruger(
                        resultSet.getInt("bruger_id"),
                        resultSet.getString("navn"),
                        resultSet.getString("email"),
                        resultSet.getString("kodeord"),
                        dato
                );

                brugere.add(bruger);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return brugere;
    }

    public Optional<Bruger> findByEmail(String email) {
        String sql = "SELECT * FROM bruger WHERE email = ?";

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Timestamp ts = resultSet.getTimestamp("oprettet_dato");
                LocalDateTime dato = null;

                if (ts != null) {
                    dato = ts.toLocalDateTime();
                }

                Bruger bruger = new Bruger(
                        resultSet.getInt("bruger_id"),
                        resultSet.getString("navn"),
                        resultSet.getString("email"),
                        resultSet.getString("kodeord"),
                        dato
                );

                return Optional.of(bruger);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public void save(Bruger bruger) {
        String sql = "INSERT INTO bruger (navn, email, kodeord, oprettet_dato) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, bruger.getNavn());
            statement.setString(2, bruger.getEmail());
            statement.setString(3, bruger.getKodeord());
            statement.setTimestamp(4, Timestamp.valueOf(bruger.getOprettetDato()));

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}