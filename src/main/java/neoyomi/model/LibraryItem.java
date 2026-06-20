package neoyomi.model;

public class LibraryItem {
    private String mangaId;
    private String judul;
    private String cover;
    private String contentType;

    public LibraryItem() {}

    public LibraryItem(String mangaId, String judul, String cover, String contentType) {
        this.mangaId     = mangaId;
        this.judul       = judul;
        this.cover       = cover;
        this.contentType = contentType;
    }

    public String getMangaId()     { return mangaId;     }
    public String getJudul()       { return judul;       }
    public String getCover()       { return cover;       }
    public String getContentType() { return contentType; }

    public void setMangaId(String mangaId)         { this.mangaId     = mangaId;     }
    public void setJudul(String judul)             { this.judul       = judul;       }
    public void setCover(String cover)             { this.cover       = cover;       }
    public void setContentType(String contentType) { this.contentType = contentType; }
}
