requireAdmin();
document.addEventListener(
    "DOMContentLoaded",
    async () => {

        const token =
            localStorage.getItem("token");

        if (!token) {

            window.location.href =
                "/login";

            return;
        }

        // Display logged-in admin name
        const fullName =
            localStorage.getItem("fullName");

        const adminName =
            document.getElementById("adminName");

        if (adminName) {
            adminName.textContent =
                fullName || "Admin";
        }

        await loadRecentOrders();
        await loadDashboardStats();
    }
);

async function loadDashboardStats() {

    const token =
        localStorage.getItem("token");

    try {

        const response =
            await fetch(
                "/api/v1/admin/dashboard",
                {
                    method: "GET",
                    headers: {
                        Authorization:
                            `Bearer ${token}`
                    }
                }
            );

        const result =
            await response.json();

        if (!result.success) {

            throw new Error(
                result?.status?.message ||
                "Unable to load dashboard statistics."
            );
        }

        const stats =
            result.data;

        document.getElementById(
            "totalUsers"
        ).textContent =
            stats.totalUsers ?? 0;

        document.getElementById(
            "totalProducts"
        ).textContent =
            stats.totalProducts ?? 0;

        document.getElementById(
            "totalOrders"
        ).textContent =
            stats.totalOrders ?? 0;

        document.getElementById(
            "pendingOrders"
        ).textContent =
            stats.pendingOrders ?? 0;

        document.getElementById(
            "totalRevenue"
        ).textContent =
            "$" + (stats.totalRevenue ?? 0);

    } catch (error) {

        console.error(
            "Dashboard Error:",
            error
        );

        alert(
            "Unable to load dashboard statistics."
        );
    }
}

// Logout Function
function logout() {

    localStorage.removeItem("token");
    localStorage.removeItem("email");
    localStorage.removeItem("fullName");

    window.location.href =
        "/login";
}
async function loadRecentOrders() {

    const token =
        localStorage.getItem("token");

    try {

        const response =
            await fetch(
                "/api/v1/admin/orders",
                {
                    headers: {
                        Authorization:
                            `Bearer ${token}`
                    }
                }
            );

        const result =
            await response.json();

        if (!result.success) {
            return;
        }

        const orders =
            result.data
                .sort(
                    (a, b) =>
                        new Date(b.createdAt)
                        - new Date(a.createdAt)
                )
                .slice(0, 5);

        const tbody =
            document.getElementById(
                "recentOrdersBody"
            );

        tbody.innerHTML = "";

        orders.forEach(order => {

            tbody.innerHTML += `
                <tr>

                    <td>
                        ${order.order_number}
                    </td>

                    <td>
                        ${order.customerName}
                    </td>

                    <td>
                        $${Number(
                order.total_amount
            ).toLocaleString()}
                    </td>

                    <td>
                        <span class="status ${order.status.toLowerCase()}">
                            ${order.status}
                        </span>
                    </td>

                </tr>
            `;
        });

    } catch (error) {

        console.error(
            "Recent Orders Error:",
            error
        );
    }
}