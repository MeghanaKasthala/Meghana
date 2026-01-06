async function fetchPostsandDisplay() {
    const country = document.getElementById("search").value.trim();

    if (country === "") {
        alert("Please enter a country name");
        return;
    }

    const url = `http://universities.hipolabs.com/search?country=${encodeURIComponent(country)}`;

    try {
        const response = await fetch(url);

        if (!response.ok) {
            throw new Error(`Response status: ${response.status}`);
        }

        const result = await response.json();

        //const tableBody = document.getElementById("tableBody");
        //tableBody.innerHTML = "";

        const tableRows = result.map(post => `
            <tr>
                <td>${post["state-province"] || "N/A"}</td>
                <td>${post.name}</td>
                <td>${post.country}</td>
            </tr>
        `).join("");

        tableBody.innerHTML = tableRows;

    } catch (error) {
        console.error("Error:", error.message);
    } finally {
        console.log("Reached Finally");
    }
}