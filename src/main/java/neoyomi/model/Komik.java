package neoyomi.model;

import java.util.List; // Wajib di-import

public class Komik {
    private int id;
    private String name;
    private String lang;
    private String version;
    private String url;
    private List<String> genres; // Tambahan wadah genre

    public Komik(int id, String name, String lang, String version, String url, List<String> genres) {
        this.id = id;
        this.name = name;
        this.lang = lang;
        this.version = version;
        this.url = url;
        this.genres = genres;
    }
}