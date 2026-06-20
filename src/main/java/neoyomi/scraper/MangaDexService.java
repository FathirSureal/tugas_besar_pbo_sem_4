    package neoyomi.scraper;

    import com.google.gson.JsonArray;
    import com.google.gson.JsonElement;
    import com.google.gson.JsonObject;
    import com.google.gson.JsonParser;
    import java.net.InetAddress;
    import java.io.InputStreamReader;
    import java.net.HttpURLConnection;
    import java.net.URL;
    import java.net.URLEncoder;
    import java.net.UnknownHostException;
    import java.nio.charset.StandardCharsets;
    import java.util.ArrayList;
    import java.util.List;
    import java.net.InetAddress;
    import neoyomi.model.Chapter;

    public class MangaDexService {

        private static final String BASE_URL = "https://api.mangadex.org";

        // ==============================
        // Cari MangaDex ID dari judul
        // ==============================
        
        
        public String cariMangaId(String judul, int malId) {

    try {

        System.out.println("=================================");
        System.out.println("JAVA VERSION = " + System.getProperty("java.version"));
        System.out.println("JAVA HOME = " + System.getProperty("java.home"));
        System.out.println("JAVA VENDOR = " + System.getProperty("java.vendor"));
        System.out.println("=================================");

        InetAddress[] ips = InetAddress.getAllByName("api.mangadex.org");

        for (InetAddress ip : ips) {
            System.out.println("Resolved IP = " + ip.getHostAddress());
        }

        String query = URLEncoder.encode(judul, StandardCharsets.UTF_8.toString());

        URL url = new URL(BASE_URL + "/manga?title=" + query + "&limit=100");

        HttpURLConnection conn = bukaKoneksi(url);

        if (conn.getResponseCode() != 200)
            return null;

        JsonObject root = JsonParser.parseReader(
                new InputStreamReader(conn.getInputStream()))
                .getAsJsonObject();

        JsonArray data = root.getAsJsonArray("data");

        System.out.println("Total kandidat = " + data.size());

        if (data.size() == 0)
            return null;

        // =====================================================
        // PRIORITAS 1 : Cari yang MAL ID cocok DAN punya chapter
        // =====================================================

        for (JsonElement element : data) {

            JsonObject manga = element.getAsJsonObject();

            JsonObject attr = manga.getAsJsonObject("attributes");
            
            String title = "";

if (attr.has("title")) {

    JsonObject titles = attr.getAsJsonObject("title");

    if (titles.has("en"))
        title = titles.get("en").getAsString();
    else {
        for (String key : titles.keySet()) {
            title = titles.get(key).getAsString();
            break;
        }
    }
}

System.out.println("--------------------------------");
System.out.println("Title = " + title);

            if (!attr.has("links"))
                continue;

            JsonObject links = attr.getAsJsonObject("links");

            if (!links.has("mal"))
                continue;

            String mal = links.get("mal").getAsString();
            System.out.println("ID = " + manga.get("id").getAsString());
            System.out.println("MAL = " + mal);

            if (mal.equals(String.valueOf(malId))) {

                System.out.println("MATCH MAL ID");

                String mangaDexId = manga.get("id").getAsString();

                if (adaChapter(mangaDexId)) {

                    System.out.println("ADA CHAPTER");

                    return mangaDexId;

                }

                System.out.println("MATCH MAL tapi belum ada chapter");

            }

        }

        // =====================================================
        // PRIORITAS 2
        // Kalau tidak ada yang punya MAL ID,
        // pilih manga pertama yang punya chapter
        // =====================================================

        for (JsonElement element : data) {

            JsonObject manga = element.getAsJsonObject();

            String mangaDexId = manga.get("id").getAsString();

            if (adaChapter(mangaDexId)) {

                System.out.println("Fallback ke manga yang punya chapter");

                return mangaDexId;

            }

        }

    } catch (Exception e) {

        e.printStackTrace();

    }

    return null;

}
        
        private boolean adaChapter(String mangaDexId) {

    try {

        URL url = new URL(BASE_URL + "/manga/" + mangaDexId + "/feed?limit=1");

        HttpURLConnection conn = bukaKoneksi(url);

        if (conn.getResponseCode() != 200)
            return false;

        JsonObject root = JsonParser.parseReader(
                new InputStreamReader(conn.getInputStream()))
                .getAsJsonObject();

        JsonArray data = root.getAsJsonArray("data");

        System.out.println("Jumlah chapter = " + data.size());

        return data.size() > 0;

    } catch(Exception e) {

        e.printStackTrace();

    }

    return false;
}

        // ==============================
        // ambilDaftarChapter — overload lama (default: id + en)
        // Tetap ada agar kode lama tidak error compile.
        // ==============================
        public List<Chapter> ambilDaftarChapter(String mangaId) {
            return ambilDaftarChapter(mangaId, "all");
        }

        // ==============================
        // ambilDaftarChapter — dengan filter bahasa
        // lang: "id" | "en" | "all"
        // ==============================
        public List<Chapter> ambilDaftarChapter(String mangaId, String lang) {

            // Bangun query parameter bahasa sesuai pilihan
            String langParam;
            switch (lang) {
                case "id":
                    langParam = "&translatedLanguage[]=id";
                    break;
                case "en":
                    langParam = "&translatedLanguage[]=en";
                    break;
                default: // "all" — tampilkan keduanya
                    langParam = "&translatedLanguage[]=id&translatedLanguage[]=en";
                    break;
            }

            String apiUrl = BASE_URL
                    + "/manga/" + mangaId + "/feed"
                    + "?order[chapter]=asc"
                    + "&limit=500"
                    + langParam;

            return ambilChapterDariURL(apiUrl);
        }

        // ==============================
        // Ambil pages gambar sebuah chapter
        // ==============================
        public List<String> ambilHalamanChapter(String chapterId) {

            List<String> pages = new ArrayList<>();

            try {

                URL url = new URL(BASE_URL + "/at-home/server/" + chapterId);
                HttpURLConnection conn = bukaKoneksi(url);

                if (conn.getResponseCode() != 200) return pages;

                JsonObject root = JsonParser.parseReader(
                        new InputStreamReader(conn.getInputStream()))
                        .getAsJsonObject();

                String baseUrl = root.get("baseUrl").getAsString();
                JsonObject chapter = root.getAsJsonObject("chapter");
                String hash = chapter.get("hash").getAsString();
                JsonArray data = chapter.getAsJsonArray("data");

                for (JsonElement img : data) {
                    pages.add(baseUrl + "/data/" + hash + "/" + img.getAsString());
                }

                System.out.println("Total Halaman: " + pages.size());

            } catch (Exception e) {
                e.printStackTrace();
            }

            return pages;
        }

        // ==============================
        // Private: parse chapter dari URL MangaDex feed
        // ==============================
        private List<Chapter> ambilChapterDariURL(String apiUrl) {

            List<Chapter> daftar = new ArrayList<>();

            System.out.println("URL Chapter: " + apiUrl);

            try {

                URL url = new URL(apiUrl);
                HttpURLConnection conn = bukaKoneksi(url);

                System.out.println("Response Code Chapter = " + conn.getResponseCode());

                if (conn.getResponseCode() != 200) return daftar;

                JsonObject root = JsonParser.parseReader(
                        new InputStreamReader(conn.getInputStream()))
                        .getAsJsonObject();

                if (!root.has("data")) {
                    System.out.println("Field 'data' tidak ada di response chapter.");
                    return daftar;
                }

                JsonArray data = root.getAsJsonArray("data");
                System.out.println("Jumlah data dari API = " + data.size());

                for (JsonElement element : data) {
                    System.out.println("--------------------");
                    JsonObject obj  = element.getAsJsonObject();
                    String id       = obj.get("id").getAsString();
                    JsonObject attr = obj.getAsJsonObject("attributes");

                    String chapterNumber = ambilStringAttr(attr, "chapter");
                    String title         = ambilStringAttr(attr, "title");
                    String language      = ambilStringAttr(attr, "translatedLanguage");
                    String publishAt     = ambilStringAttr(attr, "publishAt");

                    System.out.println("Chapter " + chapterNumber
                            + " | " + title + " | " + language);

                    daftar.add(new Chapter(id, chapterNumber, title,
                                           language, publishAt, null));
                }

                System.out.println("Total Chapter Object: " + daftar.size());

            } catch (Exception e) {
                e.printStackTrace();
            }

            return daftar;
        }

        // ==============================
        // Helper: ambil field string dari JsonObject, kembalikan "" jika null
        // ==============================
        private String ambilStringAttr(JsonObject obj, String key) {
            if (obj.has(key) && !obj.get(key).isJsonNull()) {
                return obj.get(key).getAsString();
            }
            return "";
        }

        // ==============================
        // Helper: buka koneksi HTTP GET
        // ==============================
        private HttpURLConnection bukaKoneksi(URL url) throws Exception {
            System.out.println("HTTP GET: " + url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "NeoYomiApp/1.0");
            conn.setConnectTimeout(7000);
            conn.setReadTimeout(7000);
            return conn;
        }
    }   
