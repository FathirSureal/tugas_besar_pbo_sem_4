/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package neoyomi.model;

public class Library {

    private int id;
    private int userId;
    private int malId;

    private String judul;
    private String cover;
    private String genre;

    public Library() {
    }

    public Library(int userId,
                   int malId,
                   String judul,
                   String cover,
                   String genre) {

        this.userId = userId;
        this.malId = malId;
        this.judul = judul;
        this.cover = cover;
        this.genre = genre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public int getMalId() {
        return malId;
    }

    public String getJudul() {
        return judul;
    }

    public String getCover() {
        return cover;
    }

    public String getGenre() {
        return genre;
    }
}
