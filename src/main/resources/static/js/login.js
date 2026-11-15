const loginForm = document.getElementById("loginForm");
const errorBox = document.getElementById("errorMessage");

loginForm.addEventListener("submit", async (event) => {

    event.preventDefault();

    const email =
        document.getElementById("email").value.trim();

    const password =
        document.getElementById("password").value;

    errorBox.classList.add("d-none");
    errorBox.textContent = "";

    try {

        const response = await fetch(
            "/api/v1/auth/login",
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    email,
                    password
                })
            }
        );

        const result = await response.json();

        if (result.success) {

            localStorage.setItem(
                "token",
                result.data.accessToken
            );

            localStorage.setItem(
                "email",
                result.data.email
            );

            localStorage.setItem(
                "fullName",
                result.data.fullName
            );

            await loadCurrentUser();

        } else {

            showError(
                result?.status?.message ||
                "Invalid credentials."
            );
        }

    } catch (error) {

        console.error(error);

        showError(
            "Unable to connect to server."
        );
    }
});

function showError(message) {

    errorBox.textContent = message;

    errorBox.classList.remove("d-none");
}
async function loadCurrentUser() {

    const token =
        localStorage.getItem("token");

    const response =
        await fetch(
            "/api/v1/auth/me",
            {
                headers: {
                    Authorization:
                        `Bearer ${token}`
                }
            }
        );

    const result =
        await response.json();

    const role =
        result.data.roles[0];
    console.log("ROLE =", role);

    localStorage.setItem(
        "role",
        role
    );

    if (role === "ADMIN") {

        window.location.href =
            "/admin/dashboard";

    } else {

        window.location.href =
            "/shop";
    }
}