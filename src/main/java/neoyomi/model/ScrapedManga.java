package neoyomi.model;

import java.util.List;

public class ScrapedManga {

    private int malId;                  // BARU
    private String judul;
    private String urlTarget;
    private String gambarSampul;
    private List<String> genres;
    private String synopsis;
    private List<String> chapters;

    public ScrapedManga(
            int malId,
            String judul,
            String urlTarget,
            String gambarSampul,
            List<String> genres,
            String synopsis,
            List<String> chapters) {

        this.malId = malId;
        this.judul = judul;
        this.urlTarget = urlTarget;
        this.gambarSampul = gambarSampul;
        this.genres = genres;
        this.synopsis = synopsis;
        this.chapters = chapters;
    }

    public int getMalId() {
        return malId;
    }

    public String getJudul() {
        return judul;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getUrlTarget() {
        return urlTarget;
    }

    public String getGambarSampul() {
        return gambarSampul;
    }

    public List<String> getGenres() {
        return genres;
    }

    public List<String> getChapters() {
        return chapters;
    }
}