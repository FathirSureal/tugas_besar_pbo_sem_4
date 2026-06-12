package neoyomi.model;

public class Chapter {

    private String id;
    private String number;
    private String title;
    private String translatedLanguage;
    private String publishAt;
    private String externalUrl;

    /**
     * Constructor kosong
     */
    public Chapter() {
    }

    /**
     * Constructor lengkap
     * Dipakai oleh MangaDexService
     */
    public Chapter(
            String id,
            String number,
            String title,
            String translatedLanguage,
            String publishAt,
            String externalUrl) {

        this.id = id;
        this.number = number;
        this.title = title;
        this.translatedLanguage = translatedLanguage;
        this.publishAt = publishAt;
        this.externalUrl = externalUrl;
    }

    /**
     * Constructor sederhana
     * Dipakai oleh JikanAPIService
     */
    public Chapter(
            int number,
            String title,
            String externalUrl) {

        this.number = String.valueOf(number);
        this.title = title;
        this.externalUrl = externalUrl;
    }

    // ===========================
    // Getter & Setter
    // ===========================

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return (number == null || number.isBlank())
                ? "-"
                : number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return (title == null || title.isBlank())
                ? "Tanpa Judul"
                : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTranslatedLanguage() {
        return translatedLanguage;
    }

    public void setTranslatedLanguage(String translatedLanguage) {
        this.translatedLanguage = translatedLanguage;
    }

    public String getPublishAt() {
        return publishAt;
    }

    public void setPublishAt(String publishAt) {
        this.publishAt = publishAt;
    }

    public String getExternalUrl() {
        return externalUrl;
    }

    public void setExternalUrl(String externalUrl) {
        this.externalUrl = externalUrl;
    }

    @Override
    public String toString() {

        return "Chapter{" +
                "id='" + id + '\'' +
                ", number='" + number + '\'' +
                ", title='" + title + '\'' +
                ", translatedLanguage='" + translatedLanguage + '\'' +
                ", publishAt='" + publishAt + '\'' +
                ", externalUrl='" + externalUrl + '\'' +
                '}';
    }
}