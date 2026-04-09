const input = document.getElementById("search");
const results = document.getElementById("results");

input.addEventListener("input", async () => {
    const query = input.value;

    if (!query) {
        results.innerHTML = "";
        return;
    }

    try {
        const res = await fetch(`http://localhost:8080/search?q=${query}`);
        const data = await res.json();

        results.innerHTML = "";

        data.forEach(item => {
            const tr = document.createElement("tr");
            const td = document.createElement("td");

            td.textContent = item;

            tr.appendChild(td);
            results.appendChild(tr);
        });

    } catch (err) {
        console.log("Error:", err);
    }
});