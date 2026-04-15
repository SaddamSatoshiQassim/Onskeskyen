package com.example.Onskeskyen.repositorys;

import com.example.Onskeskyen.models.Onske;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OnskeRepository {

    private String dbUrl = System.getenv("DB_URL");
    private String username = System.getenv("DB_USER");
    private String password = System.getenv("DB_PASSWORD");

    public List<Onske> hentOnsker(int brugerId) {
        List<Onske> onsker = new ArrayList<>();

        String sql = """
            SELECT oi.ønskeliste_item_id, ø.ejer_bruger_id, p.navn, p.produkt_link, p.pris, p.billede_link, oi.købt
            FROM ønskeliste_item oi
            JOIN ønskeliste ø ON oi.ønskeliste_id = ø.ønskeliste_id
            JOIN produkt p ON oi.produkt_id = p.produkt_id
            WHERE ø.ejer_bruger_id = ?
        """;

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, brugerId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Onske onske = new Onske(
                        rs.getInt("ønskeliste_item_id"),
                        rs.getInt("ejer_bruger_id"),
                        rs.getString("navn"),
                        rs.getString("produkt_link"),
                        rs.getDouble("pris"),
                        rs.getString("billede_link"),
                        rs.getBoolean("købt")
                );

                onsker.add(onske);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return onsker;
    }

    public void opretOnske(int brugerId, String navn, String link, double pris, String billede) {
        String findListeSql = "SELECT ønskeliste_id FROM ønskeliste WHERE ejer_bruger_id = ? LIMIT 1";
        String opretListeSql = "INSERT INTO ønskeliste (ejer_bruger_id, titel, offentlig, dato) VALUES (?, ?, ?, CURDATE())";
        String opretProduktSql = "INSERT INTO produkt (navn, pris, produkt_link, billede_link) VALUES (?, ?, ?, ?)";
        String opretItemSql = "INSERT INTO ønskeliste_item (ønskeliste_id, produkt_id, købt, dato) VALUES (?, ?, false, CURDATE())";

        try (Connection connection = DriverManager.getConnection(dbUrl, username, password)) {

            int ønskelisteId = -1;

            try (PreparedStatement statement = connection.prepareStatement(findListeSql)) {
                statement.setInt(1, brugerId);
                ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    ønskelisteId = rs.getInt("ønskeliste_id");
                }
            }

            if (ønskelisteId == -1) {
                try (PreparedStatement statement = connection.prepareStatement(opretListeSql, Statement.RETURN_GENERATED_KEYS)) {
                    statement.setInt(1, brugerId);
                    statement.setString(2, "Min ønskeliste");
                    statement.setBoolean(3, true);
                    statement.executeUpdate();

                    ResultSet keys = statement.getGeneratedKeys();
                    if (keys.next()) {
                        ønskelisteId = keys.getInt(1);
                    }
                }
            }

            int produktId = -1;

            try (PreparedStatement statement = connection.prepareStatement(opretProduktSql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, navn);
                statement.setDouble(2, pris);
                statement.setString(3, link);
                statement.setString(4, billede);
                statement.executeUpdate();

                ResultSet keys = statement.getGeneratedKeys();
                if (keys.next()) {
                    produktId = keys.getInt(1);
                }
            }

            try (PreparedStatement statement = connection.prepareStatement(opretItemSql)) {
                statement.setInt(1, ønskelisteId);
                statement.setInt(2, produktId);
                statement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}