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
}