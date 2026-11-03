package org.example.e_commerce_api.Constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum PermissionEnum {

 // Product
 PRODUCT_CREATE(
         "PRODUCT",
         "Create new products"
 ),
 PRODUCT_VIEW(
         "PRODUCT",
         "View products"
 ),
 PRODUCT_UPDATE(
         "PRODUCT",
         "Update products"
 ),
 PRODUCT_DELETE(
         "PRODUCT",
         "Delete products"
 ),

 // Category
 CATEGORY_CREATE(
         "CATEGORY",
         "Create categories"
 ),
 CATEGORY_VIEW(
         "CATEGORY",
         "View categories"
 ),
 CATEGORY_UPDATE(
         "CATEGORY",
         "Update categories"
 ),
 CATEGORY_DELETE(
         "CATEGORY",
         "Delete categories"
 ),

 // Order
 ORDER_CREATE(
         "ORDER",
         "Place new orders"
 ),
 ORDER_VIEW(
         "ORDER",
         "View orders"
 ),
 ORDER_UPDATE(
         "ORDER",
         "Update orders"
 ),
 ORDER_CANCEL(
         "ORDER",
         "Cancel orders"
 ),

 // Customer
 CUSTOMER_VIEW(
         "CUSTOMER",
         "View customer information"
 ),
 CUSTOMER_UPDATE(
         "CUSTOMER",
         "Update customer information"
 ),

 // Cart
 CART_VIEW(
         "CART",
         "View shopping cart"
 ),

 CART_ADD_ITEM(
         "CART",
         "Add item to cart"
 ),

 CART_UPDATE_ITEM(
         "CART",
         "Update cart item quantity"
 ),

 CART_REMOVE_ITEM(
         "CART",
         "Remove item from cart"
 ),

 CART_CLEAR(
         "CART",
         "Clear shopping cart"
 ),

 // Role
 ROLE_VIEW(
         "ROLE",
         "View roles"
 ),
 ROLE_CREATE(
         "ROLE",
         "Create roles"
 ),
 ROLE_UPDATE(
         "ROLE",
         "Update roles"
 ),
 ROLE_DELETE(
         "ROLE",
         "Delete roles"
 );

 private final String category;
 private final String description;

 public String getAuthority() {
  return name();
 }
}
