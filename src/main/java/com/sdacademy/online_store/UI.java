package com.sdacademy.online_store;

import com.sdacademy.online_store.dao.ModelDao;
import com.sdacademy.online_store.model.CartModel;
import com.sdacademy.online_store.model.ProductModel;
import com.sdacademy.online_store.model.UserModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class UI {

    ModelDao modelDao = new ModelDao();
    Scanner scanner = new Scanner(System.in);
    int idProdInCart = 1;

    public void startProgram() throws ParseException {
        int optiune = -1;
        while (optiune != 10) {
            printMenu();
            optiune = scanner.nextInt();
            scanner.nextLine();
            if (optiune == 1) {
                addUser();
            } else if (optiune == 2) {
                createCart();
            } else if (optiune == 3) {
                adaugaProdusInMagazin();
            } else if (optiune == 4) {
                addProductInCart();
            } else if (optiune == 5) {
                afiseazaProduse();
            } else if (optiune == 6) {
                displayCart();
            } else if (optiune == 7) {
                removeAllProductsFromCart();
            }
        }
    }

    private void printMenu() {
        System.out.println("Chose option");
        System.out.println("1. Add user");
        System.out.println("2. Create cart");
        System.out.println("3. Add product in store");
        System.out.println("4. Add prduct in cart");
        System.out.println("5. List products");
        System.out.println("6. Display cart");
        System.out.println("7. Remove all products from user");
    }

    private void addUser() {
        UserModel user = new UserModel();
        System.out.println("Enter first name");
        String firstName = scanner.nextLine();
        System.out.println("Enter last name");
        String lastName = scanner.nextLine();
        System.out.println("Enter user name");
        String userName = scanner.nextLine();
        System.out.println("Enter password");
        String password = scanner.nextLine();
        user.setLastName(lastName);
        user.setUserName(userName);
        user.setPassword(password);
        modelDao.addUser(user);
    }

    private void createCart() throws ParseException {
        CartModel cartModel = new CartModel();
        System.out.println("Introdu id utilizator");
        int id = scanner.nextInt();
        scanner.nextLine();
        UserModel userModel = modelDao.findUserById(id);
        System.out.println("Introdu data comanda dd/mm/yyyy");
        String dataC = scanner.nextLine();
        Date dataP = new SimpleDateFormat("dd/mm/yyyy").parse(dataC);
        cartModel.setOrderDate(dataP);
        cartModel.setUserModel(userModel);
        userModel.setCartModel(cartModel);
        modelDao.addCart(cartModel);
    }

    public void adaugaProdusInMagazin() {
        ProductModel productModel = new ProductModel();
        System.out.println("Nume?");
        String nume = scanner.nextLine();
        System.out.println("Cantitate?");
        int cantitate = scanner.nextInt();
        scanner.nextLine();
        productModel.setName(nume);
        productModel.setQuantityInStock(cantitate);
        modelDao.addProduct(productModel);
    }

    private void addProductInCart() {
        System.out.println("Enter Username");
        String nume = scanner.nextLine();
        UserModel userModel = modelDao.findUserByUsername(nume);
        System.out.println("Enter password");
        String password = scanner.nextLine();
        if (userModel.getPassword().equals(password)) {
            CartModel cartModel = userModel.getCartModel();
            List<ProductModel> produse = cartModel.getProductModelList();
            System.out.println("Alege produs din lista");
            afiseazaProduse();
            int id1 = scanner.nextInt();
            scanner.nextLine();
            System.out.println("Enter quantity");
            int quantityInCart = scanner.nextInt();
            scanner.nextLine();
            ProductModel produsAles = modelDao.findProductById(id1);
            int initialStock = produsAles.getQuantityInStock();
            ProductModel productInCart = new ProductModel();
            productInCart.setName(produsAles.getName());
            productInCart.setId(produsAles.getId());
            productInCart.setDescription(produsAles.getDescription());
            productInCart.setQuantityInCart(quantityInCart);
            productInCart.setIdProductInCart(idProdInCart);
            idProdInCart++;
            produse.add(productInCart);
            cartModel.setProductModelList(produse);
            modelDao.updateCart(cartModel);
            int newStockQuantity = initialStock - quantityInCart;
            produsAles.setQuantityInStock(newStockQuantity);
            modelDao.updateProducts(produsAles);
            modelDao.updateUser(userModel);
        } else
            System.out.println("Wrong password !");
    }

    public void afiseazaProduse() {
        List<ProductModel> productModelList = modelDao.getProducts();
        productModelList.forEach(prod -> {
            System.out.println("(id) " + prod.getId() + " (nume) " + prod.getName() + "   (cant) " + prod.getQuantityInStock());
        });
    }

    public void displayCart() {
        System.out.println("Enter Username");
        String nume = scanner.nextLine();
        UserModel userModel = modelDao.findUserByUsername(nume);
        List<ProductModel> productModelListByUser = userModel.getCartModel().getProductModelList();
        productModelListByUser.forEach(prod -> {
            System.out.println(prod.getIdProductInCart()+" (nume) " + prod.getName() + "   (cant) "
                    + prod.getQuantityInCart());
        });

    }

    public void removeAllProductsFromCart(){
        System.out.println("Enter Username");
        String userName = scanner.nextLine();
        UserModel userModel = modelDao.findUserByUsername(userName);
        CartModel cartModel = userModel.getCartModel();
        List<ProductModel> productModelListByUser = cartModel.getProductModelList();
        productModelListByUser.clear();
        cartModel.setProductModelList(productModelListByUser);
        modelDao.updateCart(cartModel);
    }
}