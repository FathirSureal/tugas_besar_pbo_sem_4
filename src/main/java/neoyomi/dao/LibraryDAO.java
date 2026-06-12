package neoyomi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import neoyomi.model.Komik;
import neoyomi.model.Novel;
import neoyomi.util.DatabaseConnection;

public class LibraryDAO {

    // ==========================
    // AMBIL DATA KOMIK
    // ==========================
    public List<Komik> getDaftarKomik(String keyword) {

        List<Komik> list = new ArrayList<>();

        String sql =
                "SELECT k.*, GROUP_CONCAT(g.nama_genre SEPARATOR ',') AS genre_list " +
                "FROM komik k " +
                "LEFT JOIN komik_genres kg ON k.id = kg.komik_id " +
                "LEFT JOIN genres g ON kg.genre_id = g.id ";

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql += " WHERE k.name LIKE ? ";
        }

        sql += " GROUP BY k.id";

        System.out.println("========== DAO KOMIK ==========");
        System.out.println("1. Mulai koneksi database");

        try (
                Connection conn = DatabaseConnection.getConnection()
        ) {

            if (conn == null) {
                System.out.println("ERROR: Koneksi database NULL!");
                return list;
            }

            System.out.println("2. Koneksi berhasil");

            try (
                    PreparedStatement stmt = conn.prepareStatement(sql)
            ) {

                System.out.println("3. Query berhasil dipersiapkan");

                if (keyword != null && !keyword.trim().isEmpty()) {
                    stmt.setString(1, "%" + keyword + "%");
                }

                System.out.println("4. Menjalankan query...");

                try (ResultSet rs = stmt.executeQuery()) {

                    System.out.println("5. Query selesai");

                    while (rs.next()) {

                        String genreStr = rs.getString("genre_list");

                        List<String> genreList = new ArrayList<>();

                        if (genreStr != null && !genreStr.trim().isEmpty()) {
                            genreList = Arrays.asList(genreStr.split(","));
                        }

                        Komik komik = new Komik(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("lang"),
                                rs.getString("version"),
                                rs.getString("url"),
                                genreList
                        );

                        list.add(komik);
                    }

                    System.out.println("6. Total data = " + list.size());

                }

            }

        } catch (Exception e) {
            System.out.println("===== ERROR DAO KOMIK =====");
            e.printStackTrace();
        }

        return list;
    }

    // ==========================
    // AMBIL DATA NOVEL
    // ==========================
    public List<Novel> getDaftarNovel(String keyword) {

        List<Novel> list = new ArrayList<>();

        String sql =
                "SELECT n.*, GROUP_CONCAT(g.nama_genre SEPARATOR ',') AS genre_list " +
                "FROM novel n " +
                "LEFT JOIN novel_genres ng ON n.id = ng.novel_id " +
                "LEFT JOIN genres g ON ng.genre_id = g.id ";

        if (keyword != null && !keyword.trim().isEmpty()) {
            sql += " WHERE n.name LIKE ? ";
        }

        sql += " GROUP BY n.id";

        try (
                Connection conn = DatabaseConnection.getConnection()
        ) {

            if (conn == null) {
                System.out.println("ERROR: Koneksi database NULL!");
                return list;
            }

            try (
                    PreparedStatement stmt = conn.prepareStatement(sql)
            ) {

                if (keyword != null && !keyword.trim().isEmpty()) {
                    stmt.setString(1, "%" + keyword + "%");
                }

                try (ResultSet rs = stmt.executeQuery()) {

                    while (rs.next()) {

                        String genreStr = rs.getString("genre_list");

                        List<String> genreList = new ArrayList<>();

                        if (genreStr != null && !genreStr.trim().isEmpty()) {
                            genreList = Arrays.asList(genreStr.split(","));
                        }

                        list.add(new Novel(
                                rs.getString("id"),
                                rs.getString("name"),
                                rs.getString("site"),
                                rs.getString("lang"),
                                rs.getString("url"),
                                genreList
                        ));
                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ==========================
    // SIMPAN KE MY LIBRARY
    // ==========================
    public boolean saveToLibrary(String id,
                                 String judul,
                                 String contentType,
                                 List<String> tags) {

        String insertContent =
                "INSERT IGNORE INTO contents(id, judul, content_type) VALUES(?,?,?)";

        String insertTag =
                "INSERT IGNORE INTO tags(content_id, tag) VALUES(?,?)";

        try (
                Connection conn = DatabaseConnection.getConnection()
        ) {

            if (conn == null) {
                return false;
            }

            try (PreparedStatement stmt = conn.prepareStatement(insertContent)) {

                stmt.setString(1, id);
                stmt.setString(2, judul);
                stmt.setString(3, contentType);

                stmt.executeUpdate();
            }

            if (tags != null && !tags.isEmpty()) {

                try (PreparedStatement stmt = conn.prepareStatement(insertTag)) {

                    for (String tag : tags) {
                        stmt.setString(1, id);
                        stmt.setString(2, tag.trim());
                        stmt.addBatch();
                    }

                    stmt.executeBatch();
                }

            }

            return true;

        } catch (Exception e) {

            e.printStackTrace();
            return false;

        }
    }

}