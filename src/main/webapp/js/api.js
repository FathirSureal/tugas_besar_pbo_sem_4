/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
// =========================
// API Configuration
// =========================

const API = {

    scrape(keyword) {

        return fetch(
            "api/scrape?search=" +
            encodeURIComponent(keyword)
        ).then(res => res.json());

    },

    detail(malId) {

        return fetch(
            "api/detail?malId=" + malId
        ).then(res => res.json());

    },

    history() {

        return fetch(
            "api/history"
        ).then(res => res.json());

    },

    saveHistory(data) {

        return fetch("api/history", {

            method: "POST",

            headers: {
                "Content-Type":
                    "application/x-www-form-urlencoded"
            },

            body: new URLSearchParams(data)

        });

    }

};
