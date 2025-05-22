$(function () {
    const tabel = $("#tabel");
    tabel.empty();

    const values = [1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8];
    const imgString = [
        "88ff5600-d979-11ef-a5c8-1da73bd59591.jpg",
        "3408.jpg.webp",
        "extraordinary-dog.webp",
        "images.jpeg",
        "images-2.jpeg",
        "images-3.jpeg",
        "iStock-1052880600.jpg",
        "maltese-portrait.jpg"
    ];

    // Shuffle
    for (let i = values.length - 1; i > 0; i--) {
        const j = Math.floor(Math.random() * (i + 1));
        [values[i], values[j]] = [values[j], values[i]];
    }

    const dim = Math.sqrt(values.length);
    for (let i = 0; i < dim; i++) {
        const row = $("<tr></tr>");
        for (let j = 0; j < dim; j++) {
            const item = $("<td></td>");
            item.text(values[dim * i + j]);
            item.attr("data-revealed", "false");
            item.attr("data-index", values[dim * i + j]);
            item.click(() => checkItems(item));
            row.append(item);
        }
        tabel.append(row);
    }

    let firstItem = null;
    let secondItem = null;
    let isChecking = false;

    function checkItems(item) {
        if (isChecking) return;
        if (item.attr("data-revealed") === "true") return;
        if (item.is(firstItem)) return;

        const index = parseInt(item.attr("data-index"), 10);
        item.css("background-color", "white");
        item.css("background-image", `url(${imgString[index - 1]})`);

        if (firstItem === null) {
            firstItem = item;
        } else {
            secondItem = item;
            isChecking = true;

            if (firstItem.attr("data-index") === secondItem.attr("data-index")) {
                firstItem.attr("data-revealed", "true");
                secondItem.attr("data-revealed", "true");
                resetSelection();
            } else {
                setTimeout(() => {
                    firstItem.css("background-color", "black");
                    firstItem.css("background-image", "");
                    secondItem.css("background-color", "black");
                    secondItem.css("background-image", "");
                    resetSelection();
                }, 1000);
            }
        }
    }

    function resetSelection() {
        firstItem = null;
        secondItem = null;
        isChecking = false;
    }
});
