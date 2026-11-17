let selectedOrderId = null;
requireAdmin();

document.addEventListener(
    "DOMContentLoaded",
    loadOrders
);

async function loadOrders() {

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

        const orders =
            result.data;

        const tableBody =
            document.getElementById(
                "orderTableBody"
            );

        tableBody.innerHTML = "";

        orders.forEach(order => {

            tableBody.innerHTML += `
               <tr>

                        <td>${order.order_number}</td>
                    
                        <td>${order.userId}</td>
                    
                        <td>
                            $${Number(
                                    order.total_amount
                                ).toLocaleString()}
                        </td>
                    
                        <td>
                    
                            <span
                                class="status ${order.status.toLowerCase()}">
                    
                                ${order.status}
                    
                            </span>
                    
                        </td>
                    
                        <td>
                            ${new Date(
                                    order.createdAt
                                ).toLocaleString()}
                        </td>
                    
                        <td>
                    
                            <button
                                class="btn btn-primary btn-sm"
                                onclick="viewOrder(${order.id})">
                    
                                View
                    
                            </button>
                    
                        </td>
                    
               </tr>
            `;

        });

    } catch (error) {

        console.error(error);
    }
}

async function viewOrder(id) {
    selectedOrderId = id;
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

        const order =
            result.data.find(
                o => o.id === id
            );

        document.getElementById(
            "orderNumber"
        ).textContent =
            order.order_number;

        document.getElementById(
            "orderUserId"
        ).textContent =
            order.userId;

        document.getElementById(
            "orderCustomer"
        ).textContent =
            order.customerName;

        document.getElementById(
            "orderEmail"
        ).textContent =
            order.customerEmail;

        document.getElementById(
            "orderTotal"
        ).textContent =
            order.total_amount;

        document.getElementById(
            "statusSelect"
        ).value =
            order.status;

        const itemsBody =
            document.getElementById(
                "orderItemsBody"
            );

        itemsBody.innerHTML = "";

        order.items.forEach(item => {

            itemsBody.innerHTML += `
                <tr>

                    <td>${item.product_name}</td>

                    <td>$${item.price}</td>

                    <td>${item.quantity}</td>

                    <td>$${item.subTotal}</td>

                </tr>
            `;
        });

        const modal =
            new bootstrap.Modal(
                document.getElementById(
                    "orderModal"
                )
            );

        modal.show();

    } catch (error) {

        console.error(error);
    }
}

async function updateOrderStatus() {

    if (!selectedOrderId) {
        return;
    }

    const token =
        localStorage.getItem("token");

    const status =
        document.getElementById(
            "statusSelect"
        ).value;

    try {

        const response =
            await fetch(
                `/api/v1/admin/orders/${selectedOrderId}/status`,
                {
                    method: "PATCH",

                    headers: {

                        "Content-Type":
                            "application/json",

                        Authorization:
                            `Bearer ${token}`
                    },

                    body:
                        JSON.stringify({
                            status: status
                        })
                }
            );

        const result =
            await response.json();

        if (result.success) {

            alert(
                "✅ Order status updated successfully"
            );

            location.reload();

        } else {

            alert(
                result?.status?.message ||
                "Failed to update order status"
            );
        }

    } catch (error) {

        console.error(error);

        alert(
            "Unable to connect to server"
        );
    }
}
function logout() {

    localStorage.clear();

    window.location.href =
        "/login";
}