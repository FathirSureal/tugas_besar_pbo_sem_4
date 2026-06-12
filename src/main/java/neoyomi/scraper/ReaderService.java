/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package neoyomi.scraper;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import neoyomi.model.ReaderPage;

public class ReaderService {

    private static final String AT_HOME_API =
            "https://api.mangadex.org/at-home/server/";

    /**
     * Mengambil seluruh halaman gambar dari sebuah chapter MangaDex.
     */
    public List<ReaderPage> ambilHalaman(String chapterId) {

        List<ReaderPage> pages = new ArrayList<>();

        try {

            URL url = new URL(AT_HOME_API + chapterId);

            HttpURLConnection conn =
                    (HttpURLConnection) url.openConnection();
            System.out.println(url);
            conn.setRequestMethod("GET");

            conn.setRequestProperty(
                    "User-Agent",
                    "NeoYomiApp/1.0"
            );

            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            if (conn.getResponseCode() != 200) {

                System.out.println("Gagal mengambil halaman manga.");

                return pages;

            }

            JsonObject root =
                    JsonParser.parseReader(
                            new InputStreamReader(
                                    conn.getInputStream()))
                            .getAsJsonObject();

            String baseUrl =
                    root.get("baseUrl").getAsString();

            JsonObject chapter =
                    root.getAsJsonObject("chapter");

            String hash =
                    chapter.get("hash").getAsString();

            JsonArray data =
                    chapter.getAsJsonArray("data");

            for (int i = 0; i < data.size(); i++) {

                String fileName =
                        data.get(i).getAsString();

                String imageUrl =
                        baseUrl
                        + "/data/"
                        + hash
                        + "/"
                        + fileName;

                pages.add(
                        new ReaderPage(imageUrl)
                );

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return pages;

    }

}