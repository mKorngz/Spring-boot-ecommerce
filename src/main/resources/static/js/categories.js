let editingCategoryId = null;
requireAdmin();

document.addEventListener(
    "DOMContentLoaded",
    () => {

        loadCategories();

        const categoryForm =
            document.getElementById("categoryForm");

        if (categoryForm) {

            categoryForm.addEventListener(
                "submit",
                createCategory
            );
        }
    }
);

async function loadCategories() {

    const token =
        localStorage.getItem("token");

    try {

        const response =
            await fetch(
                "/api/v1/categories",
                {
                    headers: {
                        Authorization:
                            `Bearer ${token}`
                    }
                }
            );

        const result =
            await response.json();

        const categories =
            result.data;

        const tableBody =
            document.getElementById(
                "categoryTableBody"
            );

        tableBody.innerHTML = "";

        categories.forEach(category => {

            tableBody.innerHTML += `
                <tr>

                    <td>${category.id}</td>

                    <td>${category.name}</td>

                    <td>${category.description}</td>

                    <td>

                        <button
                            class="btn btn-warning btn-sm"
                            onclick="editCategory(${category.id})">

                            Edit

                        </button>

                        <button
                            class="btn btn-danger btn-sm"
                            onclick="deleteCategory(${category.id})">

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

async function createCategory(event) {

    event.preventDefault();

    const token =
        localStorage.getItem("token");

    const request = {

        name:
        document.getElementById(
            "categoryName"
        ).value,

        description:
        document.getElementById(
            "categoryDescription"
        ).value
    };

    try {

        const url =
            editingCategoryId
                ? `/api/v1/admin/categories/${editingCategoryId}`
                : "/api/v1/admin/categories";

        const method =
            editingCategoryId
                ? "PUT"
                : "POST";

        const response = await fetch(
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
                editingCategoryId
                    ? "✅ Category updated successfully"
                    : "✅ Category created successfully"
            );

            editingCategoryId = null;

            location.reload();

        } else {

            alert(
                result?.status?.message ||
                "Failed to create category"
            );
        }

    } catch (error) {

        console.error(error);

        alert(
            "Unable to connect to server"
        );
    }
}

async function deleteCategory(id) {

    const confirmed =
        confirm(
            "Are you sure you want to delete this category?"
        );

    if (!confirmed) {
        return;
    }

    const token =
        localStorage.getItem("token");

    try {

        const response =
            await fetch(
                `/api/v1/admin/categories/${id}`,
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
                "✅ Category deleted successfully"
            );

            location.reload();

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

async function editCategory(id) {

    editingCategoryId = id;

    const token =
        localStorage.getItem("token");

    try {

        const response =
            await fetch(
                `/api/v1/categories/${id}`,
                {
                    headers: {
                        Authorization:
                            `Bearer ${token}`
                    }
                }
            );

        const result =
            await response.json();

        const category =
            result.data;

        document.getElementById(
            "categoryName"
        ).value =
            category.name;

        document.getElementById(
            "categoryDescription"
        ).value =
            category.description;

        const modal =
            new bootstrap.Modal(
                document.getElementById(
                    "categoryModal"
                )
            );

        modal.show();

    } catch (error) {

        console.error(error);

        alert(
            "Failed to load category."
        );
    }
}
function logout() {

    localStorage.clear();

    window.location.href =
        "/login";
}