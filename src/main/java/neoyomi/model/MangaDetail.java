package neoyomi.model;

import java.util.ArrayList;
import java.util.List;

public class MangaDetail {

    private int malId;
    private String judul;
    private String gambarSampul;
    private String synopsis;
    private List<String> genres;
    private List<Chapter> chapters;

    // Constructor kosong
    public MangaDetail() {
        this.genres = new ArrayList<>();
        this.chapters = new ArrayList<>();
    }

    // Constructor lengkap
    public MangaDetail(int malId,
                       String judul,
                       String gambarSampul,
                       String synopsis,
                       List<String> genres,
                       List<Chapter> chapters) {

        this.malId = malId;
        this.judul = judul;
        this.gambarSampul = gambarSampul;
        this.synopsis = synopsis;
        this.genres = genres;
        this.chapters = chapters;
    }

    // =========================
    // Getter
    // =========================

    public int getMalId() {
        return malId;
    }

    public String getJudul() {
        return judul;
    }

    public String getGambarSampul() {
        return gambarSampul;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public List<String> getGenres() {
        return genres;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }

    // =========================
    // Setter
    // =========================

    public void setMalId(int malId) {
        this.malId = malId;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public void setGambarSampul(String gambarSampul) {
        this.gambarSampul = gambarSampul;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public void setChapters(List<Chapter> chapters) {
        this.chapters = chapters;
    }

    @Override
    public String toString() {
        return "MangaDetail{" +
                "malId=" + malId +
                ", judul='" + judul + '\'' +
                ", gambarSampul='" + gambarSampul + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", genres=" + genres +
                ", chapters=" + chapters +
                '}';
    }
}