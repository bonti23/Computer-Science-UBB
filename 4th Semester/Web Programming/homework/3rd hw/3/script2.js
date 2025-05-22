const tabel = document.getElementById("tabel");
tabel.innerHTML = "";

let values = [1,1,2,2,3,3,4,4,5,5,6,6,7,7,8,8];

// Amestecarea valorilor
for (let i = values.length - 1; i > 0; i--) {
    const j = Math.floor(Math.random() * (i + 1));
    [values[i], values[j]] = [values[j], values[i]];
}

const dim = Math.sqrt(values.length);
for (let i = 0; i < dim; i++) {
    const row = tabel.insertRow(i);
    for (let j = 0; j < dim; j++) {
        const item = document.createElement("td");
        item.textContent = values[i * dim + j];
        item.dataset.value = values[i * dim + j];
        row.insertCell(j).appendChild(item);
        item.addEventListener("click", () => checkItems(item));
    }
}

let firstItem = null;
let secondItem = null;
let lockBoard = false;

function checkItems(item) {
    if (lockBoard || item.classList.contains("revealed")) return;

    item.classList.add("revealed");

    if (!firstItem) {
        firstItem = item;
    } else if (!secondItem) {
        secondItem = item;

        if (firstItem.dataset.value === secondItem.dataset.value) {
            // Se potrivesc
            firstItem = null;
            secondItem = null;
        } else {
            // Nu se potrivesc
            lockBoard = true;
            item.classList.add("unmatched");
            firstItem.classList.add("unmatched");

            setTimeout(() => {
                firstItem.classList.remove("revealed", "unmatched");
                secondItem.classList.remove("revealed", "unmatched");
                firstItem = null;
                secondItem = null;
                lockBoard = false;
            }, 1000);
        }
    }
}
