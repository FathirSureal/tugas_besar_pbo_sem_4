package neoyomi.model;

import java.util.List;

public class Komik {

    private int          id;
    private String       name;
    private String       lang;
    private String       version;
    private String       url;
    private List<String> genres;
    private String       type;   // "manga" | "manhwa" | "manhua" | "comics"

    // ── Constructor lama (tanpa type) — backward compat ──
    public Komik(int id, String name, String lang,
                 String version, String url, List<String> genres) {
        this(id, name, lang, version, url, genres, "manga");
    }

    // ── Constructor baru (dengan type) ──
    public Komik(int id, String name, String lang,
                 String version, String url,
                 List<String> genres, String type) {
        this.id      = id;
        this.name    = name;
        this.lang    = lang;
        this.version = version;
        this.url     = url;
        this.genres  = genres;
        this.type    = type;
    }

    public int          getId()      { return id;      }
    public String       getName()    { return name;    }
    public String       getLang()    { return lang;    }
    public String       getVersion() { return version; }
    public String       getUrl()     { return url;     }
    public List<String> getGenres()  { return genres;  }
    public String       getType()    { return type;    }
}
