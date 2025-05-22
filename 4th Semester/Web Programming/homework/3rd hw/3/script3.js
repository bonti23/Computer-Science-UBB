const tabel = document.getElementById("tabel");
tabel.innerHTML = "";

let values = [1,1,2,2,3,3,4,4,5,5,6,6,7,7,8,8];
let imgString = ["1.jpg", "2.jpg", "3.jpg", "4.jpg", "5.jpg", "6.jpg", "7.jpg", "88.png"];

// Amestecarea valorilor
for (let i = values.length - 1; i > 0; i--) {
    const j = Math.floor(Math.random() * (i + 1));
    [values[i], values[j]] = [values[j], values[i]];
}

const dim = Math.sqrt(values.length);
for (let i = 0; i < dim; i++) {
    let row = tabel.insertRow(i);
    for (let j = 0; j < dim; j++) {
        const item = document.createElement("td");
        const value = values[dim * i + j];

        item.setAttribute("data-value", value);
        item.setAttribute("data-row", i);
        item.setAttribute("data-cell", j);
        item.textContent = "";
        item.style.backgroundColor = "black";

        row.insertCell(j).appendChild(item);
        item.addEventListener("click", () => checkItems(item));
    }
}

let firstItem = null;
let secondItem = null;
let lockBoard = false;

function checkItems(item) {
    if (lockBoard || item.style.backgroundColor === "white") return;

    const index = item.getAttribute("data-value");
    item.style.backgroundColor = "white";
    item.style.backgroundImage = `url(${imgString[index - 1]})`;

    if (firstItem === null) {
        firstItem = item;
    } else if (secondItem === null) {
        secondItem = item;

        if (firstItem.getAttribute("data-value") === secondItem.getAttribute("data-value")) {
            firstItem = null;
            secondItem = null;
        } else {
            lockBoard = true;
            setTimeout(() => {
                firstItem.style.backgroundColor = "black";
                secondItem.style.backgroundColor = "black";
                firstItem.style.backgroundImage = "";
                secondItem.style.backgroundImage = "";
                firstItem = null;
                secondItem = null;
                lockBoard = false;
            }, 2000);
        }
    }
}
