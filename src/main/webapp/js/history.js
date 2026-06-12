/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
function catatKeHistory(mangaId, judul, gambarSampul, tags) {

    const formData = new URLSearchParams();

    formData.append("mangaId", mangaId);
    formData.append("judul", judul);
    formData.append("gambarSampul", gambarSampul);
    formData.append("tags", tags);

    fetch("api/history", {

        method: "POST",

        headers: {
            "Content-Type":
                "application/x-www-form-urlencoded"
        },

        body: formData

    })
    .then(res => res.json())
    .then(data => {

        if(data.status==="success"){

            console.log("History tersimpan");

        }

    })
    .catch(console.error);

}

