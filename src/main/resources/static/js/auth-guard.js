function requireAdmin() {

    const token =
        localStorage.getItem("token");

    const role =
        localStorage.getItem("role");

    if (!token) {

        window.location.href =
            "/login";

        return;
    }

    if (role !== "ADMIN") {

        window.location.href =
            "/shop";
    }
}