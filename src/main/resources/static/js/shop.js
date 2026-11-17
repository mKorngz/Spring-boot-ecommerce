let products = [];
let currentCategory = "all";

document.addEventListener(
    "DOMContentLoaded",
    () => {

        loadCategories();
        loadProducts();

        document
            .getElementById("searchInput")
            .addEventListener(
                "keyup",
                renderProducts
            );
    }
);

async function loadProducts() {

    const response =
        await fetch("/api/v1/products");

    const result =
        await response.json();

    console.log("RESULT =", result);

    products = result.data;

    console.log("PRODUCTS =", products);

    renderProducts();
}

async function loadCategories() {

    const response =
        await fetch("/api/v1/categories");

    const result =
        await response.json();

    const container =
        document.getElementById(
            "categoryContainer"
        );

    container.innerHTML =
        `<button
            class="category-btn"
            onclick="filterCategory('all')">
                All
        </button>`;

    result.data.forEach(category => {

        container.innerHTML += `
            <button
                class="category-btn"
                onclick="filterCategory('${category.name}')">

                ${category.name}

            </button>
        `;
    });
}

function filterCategory(category) {

    currentCategory = category;

    renderProducts();
}

function renderProducts() {

    const search =
        document
            .getElementById(
                "searchInput"
            )
            .value
            .toLowerCase();

    const container =
        document.getElementById(
            "productContainer"
        );

    container.innerHTML = "";

    const filtered =
        products.filter(product => {

            const matchesSearch =
                product.name
                    .toLowerCase()
                    .includes(search);

            const matchesCategory =
                currentCategory === "all"
                ||
                product.category.name
                === currentCategory;

            return (
                matchesSearch
                &&
                matchesCategory
            );
        });

    filtered.forEach(product => {

        container.innerHTML += `
            <div class="col-lg-3">

                <div class="product-card">

                    <div class="product-icon">
                        <i class="bi ${getCategoryIcon(product.category.name)}"></i>
                    </div>

                    <span class="category-badge">
                        ${product.category.name}
                    </span>

                    <h5 class="product-title mt-3">
                        ${product.name}
                    </h5>

                    <p>
                        ${product.description}
                    </p>

                    <div class="price mb-2">
                        $${product.price}
                    </div>

                    <small>
                        Stock:
                        ${product.stock_quantity}
                    </small>

                    <button
                        class="add-btn mt-3"
                        onclick="addToCart(${product.id})">

                        Add To Cart

                    </button>

                </div>

            </div>
        `;
    });
}

async function addToCart(productId) {

    const token =
        localStorage.getItem("token");

    if (!token) {

        window.location.href =
            "/login";

        return;
    }

    await fetch(
        "/api/v1/cart/items",
        {
            method: "POST",
            headers: {
                "Content-Type":
                    "application/json",
                Authorization:
                    `Bearer ${token}`
            },
            body: JSON.stringify({
                productId,
                quantity: 1
            })
        }
    );

    alert("Added to cart");
}

function getCategoryIcon(categoryName) {

    if (!categoryName)
        return "bi-box-seam";

    switch (
        categoryName.toLowerCase()
        ) {

        case "electronics":
            return "bi-laptop";

        case "food":
            return "bi-cup-hot";

        case "fashion":
            return "bi-bag";

        case "books":
            return "bi-book";

        case "gaming":
            return "bi-controller";

        default:
            return "bi-box-seam";
    }
}