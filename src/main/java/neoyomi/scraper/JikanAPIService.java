package neoyomi.scraper;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import neoyomi.model.Chapter;
import neoyomi.model.MangaDetail;
import neoyomi.model.ScrapedManga;

public class JikanAPIService {

    private static final String API_URL = "https://api.jikan.moe/v4/manga";
    private static final String DETAIL_URL = "https://api.jikan.moe/v4/manga/%d/full";

    // =========================================================
    // SEARCH DEFAULT
    // =========================================================
    public List<ScrapedManga> cariKomik(String keyword) {
        return cariKomik(keyword, "all", "all");
    }

    // =========================================================
    // SEARCH DENGAN FILTER
    // =========================================================
    public List<ScrapedManga> cariKomik(String keyword,
                                        String type,
                                        String genre) {

        List<ScrapedManga> hasil = new ArrayList<>();

        try {

            StringBuilder urlBuilder = new StringBuilder(API_URL);
            urlBuilder.append("?q=");
            urlBuilder.append(
                    URLEncoder.encode(keyword, StandardCharsets.UTF_8.toString())
            );

            urlBuilder.append("&limit=25");

            // ==========================
            // FILTER TYPE
            // ==========================
            if (type != null && !type.equalsIgnoreCase("all")) {
                urlBuilder.append("&type=");
                urlBuilder.append(type);
            }

            URL url = new URL(urlBuilder.toString());

            System.out.println("Jikan URL : " + url);

            HttpURLConnection conn = bukaKoneksi(url);

            if (conn.getResponseCode() != 200)
                return hasil;

            JsonObject root = JsonParser.parseReader(
                    new InputStreamReader(conn.getInputStream()))
                    .getAsJsonObject();

            JsonArray data = root.getAsJsonArray("data");

            for (JsonElement element : data) {

                JsonObject obj = element.getAsJsonObject();

                int malId = obj.get("mal_id").getAsInt();

                String judul = obj.get("title").getAsString();

                String urlTarget = obj.get("url").getAsString();

                String gambar =
                        obj.getAsJsonObject("images")
                           .getAsJsonObject("jpg")
                           .get("image_url")
                           .getAsString();

                String synopsis = "";

                if (obj.has("synopsis") && !obj.get("synopsis").isJsonNull()) {

                    synopsis = obj.get("synopsis").getAsString();

                    if (synopsis.length() > 300) {
                        synopsis = synopsis.substring(0, 300) + "...";
                    }
                }

                List<String> genres = new ArrayList<>();

                if (obj.has("genres")) {

                    for (JsonElement g : obj.getAsJsonArray("genres")) {

                        genres.add(
                                g.getAsJsonObject()
                                 .get("name")
                                 .getAsString()
                        );

                    }

                }

                // ==========================
                // FILTER GENRE
                // ==========================
                if (genre != null && !genre.equalsIgnoreCase("all")) {

                    boolean cocok = false;

                    for (String g : genres) {

                        if (g.equalsIgnoreCase(genre)) {
                            cocok = true;
                            break;
                        }

                    }

                    if (!cocok) {
                        continue;
                    }
                }

                hasil.add(
                        new ScrapedManga(
                                malId,
                                judul,
                                urlTarget,
                                gambar,
                                genres,
                                synopsis,
                                new ArrayList<>()
                        )
                );

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return hasil;
    }

    // =========================================================
    // DETAIL MANGA
    // =========================================================
    public MangaDetail ambilDetailManga(int malId) {
        return ambilDetailManga(malId, "all");
    }

    // =========================================================
    // DETAIL MANGA DENGAN FILTER BAHASA
    // =========================================================
    public MangaDetail ambilDetailManga(int malId, String lang) {

        try {

            URL url = new URL(String.format(DETAIL_URL, malId));

            HttpURLConnection conn = bukaKoneksi(url);

            if (conn.getResponseCode() != 200)
                return null;

            JsonObject root = JsonParser.parseReader(
                    new InputStreamReader(conn.getInputStream()))
                    .getAsJsonObject();

            JsonObject data = root.getAsJsonObject("data");

            String judul =
                    data.get("title").getAsString();

            String gambar =
                    data.getAsJsonObject("images")
                        .getAsJsonObject("jpg")
                        .get("large_image_url")
                        .getAsString();

            String synopsis = "";

            if (data.has("synopsis") &&
                    !data.get("synopsis").isJsonNull()) {

                synopsis = data.get("synopsis").getAsString();
            }

            List<String> genres = new ArrayList<>();

            if (data.has("genres")) {

                for (JsonElement g : data.getAsJsonArray("genres")) {

                    genres.add(
                            g.getAsJsonObject()
                             .get("name")
                             .getAsString()
                    );

                }

            }

            MangaDexService mangaDex = new MangaDexService();

            System.out.println("Judul dari Jikan = " + judul);

            String mangaDexId = mangaDex.cariMangaId(judul, malId);

            System.out.println("MangaDex ID = " + mangaDexId);

            List<Chapter> chapters = new ArrayList<>();

            if (mangaDexId != null) {
                chapters = mangaDex.ambilDaftarChapter(mangaDexId, lang);
            }

            System.out.println("Total Chapter (" + lang + ") = " + chapters.size());

            return new MangaDetail(
                    malId,
                    judul,
                    gambar,
                    synopsis,
                    genres,
                    chapters
            );

        } catch (Exception e) {

            e.printStackTrace();

        }

        return null;
    }

    // =========================================================
    // HTTP CONNECTION
    // =========================================================
    private HttpURLConnection bukaKoneksi(URL url) throws Exception {

        System.out.println("HTTP GET : " + url);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");

        conn.setRequestProperty("User-Agent", "NeoYomiApp/1.0");

        conn.setConnectTimeout(7000);

        conn.setReadTimeout(7000);

        return conn;
    }
}
