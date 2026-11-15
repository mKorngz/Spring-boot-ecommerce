const registerForm = document.getElementById("registerForm");
const errorBox = document.getElementById("registerError");

registerForm.addEventListener("submit", async (event) => {

    event.preventDefault();

    const fullName = document.getElementById("fullName").value.trim();
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirmPassword").value;

    errorBox.classList.add("d-none");
    errorBox.textContent = "";

    // Client-side validation
    if (!fullName) {
        showError("Full name is required.");
        return;
    }

    if (!email) {
        showError("Email is required.");
        return;
    }

    if (password.length < 4) {
        showError("Password must be at least 4 characters.");
        return;
    }

    if (password !== confirmPassword) {
        showError("Passwords do not match.");
        return;
    }

    try {

        const response = await fetch("/api/v1/auth/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                fullName,
                email,
                password,
                enabled: true,
                accountNonLocked: true
            })
        });

        const result = await response.json();

        if (result.success) {

            alert(
                result?.status?.message ||
                "Registration successful."
            );

            window.location.href = "/login";

        } else {

            showError(
                result?.status?.message ||
                "Registration failed."
            );
        }

    } catch (error) {

        console.error(error);

        showError(
            "Unable to connect to server. Please try again."
        );
    }
});

function showError(message) {
    errorBox.textContent = message;
    errorBox.classList.remove("d-none");
}