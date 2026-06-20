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
    // AMBIL DATA KOMIK (dengan filter genre & type)
    // genre: nama genre atau null/kosong untuk semua
    // type: "manga" | "manhwa" | "manhua" | "comics" | null untuk semua
    // ==========================
    public List<Komik> getDaftarKomik(String keyword) {
        return getDaftarKomikFilter(keyword, null, null);
    }

    public List<Komik> getDaftarKomikFilter(String keyword, String genre, String type) {

        List<Komik> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
            "SELECT k.*, GROUP_CONCAT(g.nama_genre ORDER BY g.nama_genre SEPARATOR ',') AS genre_list " +
            "FROM komik k " +
            "LEFT JOIN komik_genres kg ON k.id = kg.komik_id " +
            "LEFT JOIN genres g ON kg.genre_id = g.id "
        );

        List<String> conditions = new ArrayList<>();
        List<Object> params     = new ArrayList<>();

        // Filter keyword (nama ekstensi)
        if (keyword != null && !keyword.trim().isEmpty()) {
            conditions.add("k.name LIKE ?");
            params.add("%" + keyword.trim() + "%");
        }

        // Filter tipe komik (manga / manhwa / manhua / comics)
        if (type != null && !type.trim().isEmpty() && !type.equals("all")) {
            conditions.add("k.type = ?");
            params.add(type.trim());
        }

        // Filter genre — pakai subquery agar tidak bentrok dengan GROUP BY
        if (genre != null && !genre.trim().isEmpty() && !genre.equals("all")) {
            conditions.add(
                "k.id IN (" +
                "  SELECT kg2.komik_id FROM komik_genres kg2 " +
                "  JOIN genres g2 ON kg2.genre_id = g2.id " +
                "  WHERE g2.nama_genre = ?" +
                ")"
            );
            params.add(genre.trim());
        }

        if (!conditions.isEmpty()) {
            sql.append("WHERE ").append(String.join(" AND ", conditions)).append(" ");
        }

        sql.append("GROUP BY k.id ORDER BY k.name");

        System.out.println("========== DAO KOMIK FILTER ==========");
        System.out.println("SQL: " + sql);
        System.out.println("Params: " + params);

        try (Connection conn = DatabaseConnection.getConnection()) {

            if (conn == null) {
                System.out.println("ERROR: Koneksi database NULL!");
                return list;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

                for (int i = 0; i < params.size(); i++) {
                    stmt.setObject(i + 1, params.get(i));
                }

                try (ResultSet rs = stmt.executeQuery()) {

                    while (rs.next()) {

                        String genreStr  = rs.getString("genre_list");
                        List<String> genreList = new ArrayList<>();

                        if (genreStr != null && !genreStr.trim().isEmpty()) {
                            genreList = Arrays.asList(genreStr.split(","));
                        }

                        // Baca kolom type (default "manga" jika null)
                        String komikType = rs.getString("type");
                        if (komikType == null) komikType = "manga";

                        Komik komik = new Komik(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("lang"),
                            rs.getString("version"),
                            rs.getString("url"),
                            genreList,
                            komikType
                        );

                        list.add(komik);
                    }

                    System.out.println("Total data = " + list.size());
                }
            }

        } catch (Exception e) {
            System.out.println("===== ERROR DAO KOMIK =====");
            e.printStackTrace();
        }

        return list;
    }

    // ==========================
    // AMBIL DAFTAR GENRE (untuk dropdown / chip filter)
    // ==========================
    public List<String> getDaftarGenre() {

        List<String> list = new ArrayList<>();

        String sql = "SELECT nama_genre FROM genres ORDER BY nama_genre";

        try (Connection conn = DatabaseConnection.getConnection()) {

            if (conn == null) return list;

            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    list.add(rs.getString("nama_genre"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // ==========================
    // AMBIL GENRE YANG DIPAKAI KOMIK (lebih efisien untuk filter)
    // ==========================
    public List<String> getGenreKomikAktif() {

        List<String> list = new ArrayList<>();

        String sql =
            "SELECT DISTINCT g.nama_genre " +
            "FROM genres g " +
            "JOIN komik_genres kg ON g.id = kg.genre_id " +
            "ORDER BY g.nama_genre";

        try (Connection conn = DatabaseConnection.getConnection()) {

            if (conn == null) return list;

            try (PreparedStatement stmt = conn.prepareStatement(sql);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    list.add(rs.getString("nama_genre"));
                }
            }

        } catch (Exception e) {
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

        sql += " GROUP BY n.id ORDER BY n.name";

        try (Connection conn = DatabaseConnection.getConnection()) {

            if (conn == null) {
                System.out.println("ERROR: Koneksi database NULL!");
                return list;
            }

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {

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
    public boolean saveToLibrary(String id, String judul,
                                 String contentType, List<String> tags) {

        String insertContent =
            "INSERT IGNORE INTO contents(id, judul, content_type) VALUES(?,?,?)";
        String insertTag =
            "INSERT IGNORE INTO tags(content_id, tag) VALUES(?,?)";

        try (Connection conn = DatabaseConnection.getConnection()) {

            if (conn == null) return false;

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
