$(function () {
    const tabel = $("#tabel")[0];
    tabel.innerHTML = "";
    let values = [1,1,2,2,3,3,4,4,5,5,6,6,7,7,8,8];

    for (let i = values.length - 1; i > 0; i--) {
        const j = Math.floor(Math.random() * (i + 1));
        [values[i], values[j]] = [values[j], values[i]];
    }

    const dim = Math.sqrt(values.length);
    for (let i = 0; i < dim; i++) {
        const row = $("<tr>");
        for (let j = 0; j < dim; j++) {
            const item = $("<td>");
            item.text(values[i * dim + j]);
            item.css("background-color", "black");
            item.data("revealed", false);
            item.click(() => checkItems(item));
            row.append(item);
        }
        $(tabel).append(row);
    }

    let firstItem = null;
    let secondItem = null;
    let isChecking = false;

    function checkItems(item) {
        if (isChecking) return;
        if (item.data("revealed")) return;
        if (item.is(firstItem)) return;

        item.css("background-color", "white");

        if (firstItem === null) {
            firstItem = item;
        } else {
            secondItem = item;
            isChecking = true;

            if (firstItem.text() === secondItem.text()) {
                firstItem.data("revealed", true);
                secondItem.data("revealed", true);
                firstItem = null;
                secondItem = null;
                isChecking = false;
            } else {
                setTimeout(() => {
                    firstItem.css("background-color", "black");
                    secondItem.css("background-color", "black");
                    firstItem = null;
                    secondItem = null;
                    isChecking = false;
                }, 1000);
            }
        }
    }
});
