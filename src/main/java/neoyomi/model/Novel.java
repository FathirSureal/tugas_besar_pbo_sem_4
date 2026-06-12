package neoyomi.model;

import java.util.List;

public class Novel {
    private String id;
    private String name;
    private String site;
    private String lang;
    private String url;
    private List<String> genres; // Tambahan wadah genre

    public Novel(String id, String name, String site, String lang, String url, List<String> genres) {
        this.id = id;
        this.name = name;
        this.site = site;
        this.lang = lang;
        this.url = url;
        this.genres = genres;
    }
}