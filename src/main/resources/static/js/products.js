let editingProductId = null;
requireAdmin();

document.addEventListener(
    "DOMContentLoaded",
    () => {

        loadProducts();

        const productForm =
            document.getElementById("productForm");

        if (productForm) {

            productForm.addEventListener(
                "submit",
                createProduct
            );
        }
    }
);

async function loadProducts() {

    const token =
        localStorage.getItem("token");

    try {

        const response =
            await fetch(
                "/api/v1/products",
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

        const products =
            result.data;

        const tableBody =
            document.getElementById(
                "productTableBody"
            );

        tableBody.innerHTML = "";

        products.forEach(product => {

            tableBody.innerHTML += `
                <tr>

                    <td>${product.id}</td>

                    <td>${product.name}</td>

                    <td>$${product.price}</td>
                    
                    <td>${product.description}</td>

                    <td>${product.stock_quantity}</td>

                    <td>
                        <span class="category-badge">
                            ${product.category.name}
                        </span>
                    </td>

                    <td>

                        <button
                            class="btn btn-warning btn-sm"
                            onclick="editProduct(${product.id})">
    
                            Edit

                        </button>

                         <button
                            class="btn btn-danger btn-sm"
                            onclick="deleteProduct(${product.id})">

                            Delete

                         </button>

                    </td>

                </tr>
            `;
        });

    } catch (error) {

        console.error(error);
    }
}

async function createProduct(event) {

    event.preventDefault();

    console.log("editingProductId:", editingProductId);

    const token =
        localStorage.getItem("token");

    const request = {

        name:
        document.getElementById("name").value,

        description:
        document.getElementById("description").value,

        price:
            Number(
                document.getElementById("price").value
            ),

        stock_quantity:
            Number(
                document.getElementById("stock").value
            ),

        categoryId:
            Number(
                document.getElementById("categoryId").value
            )
    };

    try {

        const url =
            editingProductId
                ? `/api/v1/products/${editingProductId}`
                : "/api/v1/products";

        const method =
            editingProductId
                ? "PUT"
                : "POST";

        const response =
            await fetch(
                url,
                {
                    method,

                    headers: {
                        "Content-Type":
                            "application/json",

                        Authorization:
                            `Bearer ${token}`
                    },

                    body:
                        JSON.stringify(request)
                }
            );

        const result =
            await response.json();

        if (result.success) {

            alert(
                editingProductId
                    ? "✅ Product updated successfully"
                    : "✅ Product created successfully"
            );

            location.reload();

        } else {

            alert(
                result?.status?.message ||
                "Failed to create product"
            );
        }

    } catch (error) {

        console.error(error);

        alert(
            "Unable to connect to server"
        );
    }
}

async function deleteProduct(id) {

    const confirmed =
        confirm(
            "Are you sure you want to delete this product?"
        );

    if (!confirmed) {
        return;
    }

    const token =
        localStorage.getItem("token");

    try {

        const response =
            await fetch(
                `/api/v1/products/${id}`,
                {
                    method: "DELETE",

                    headers: {
                        Authorization:
                            `Bearer ${token}`
                    }
                }
            );

        const result =
            await response.json();

        if (result.success) {

            alert(
                "✅ Product deleted successfully"
            );

            await loadProducts();

        } else {

            alert(
                result?.status?.message ||
                "Delete failed."
            );
        }

    } catch (error) {

        console.error(error);

        alert(
            "Unable to connect to server."
        );
    }
}
async function editProduct(id) {

    editingProductId = id;
    const token =
        localStorage.getItem("token");

    try {

        const response =
            await fetch(
                `/api/v1/products/${id}`,
                {
                    headers: {
                        Authorization:
                            `Bearer ${token}`
                    }
                }
            );

        const result =
            await response.json();

        const product =
            result.data;

        document.getElementById("name").value =
            product.name;

        document.getElementById("description").value =
            product.description;

        document.getElementById("price").value =
            product.price;

        document.getElementById("stock").value =
            product.stock_quantity;

        document.getElementById("categoryId").value =
            product.category.id;

        const modal =
            new bootstrap.Modal(
                document.getElementById(
                    "addProductModal"
                )
            );

        modal.show();

    } catch (error) {

        console.error(error);

        alert(
            "Failed to load product."
        );
    }
}



function logout() {

    localStorage.clear();

    window.location.href =
        "/login";
}