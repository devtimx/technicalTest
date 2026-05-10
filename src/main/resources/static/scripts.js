const API_BASE_URL = "http://localhost:8080/api/pet";

async function getPet() {

    const petId = document.getElementById("petId").value;

    try {

        const response = await fetch(
            `${API_BASE_URL}/${petId}`
        );

        const data = await response.json();

        printResponse(data);

    } catch (error) {

        printResponse(error);

    }
}

async function createPet() {

    const request = {

        id: Number(
            document.getElementById("createId").value
        ),

        name: document.getElementById("createName").value,

        status: document.getElementById("createStatus").value
    };

    try {

        const response = await fetch(API_BASE_URL, {

            method: "POST",

            headers: {
                "Content-Type": "application/json"
            },

            body: JSON.stringify(request)
        });

        const data = await response.json();

        printResponse(data);

    } catch (error) {

        printResponse(error);

    }
}

function printResponse(data) {

    document.getElementById("response")
        .textContent =
            JSON.stringify(data, null, 2);
}