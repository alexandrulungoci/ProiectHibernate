package com.sdacademy.online_store.dao;

import com.sdacademy.online_store.model.CartModel;
import com.sdacademy.online_store.model.ProductModel;
import com.sdacademy.online_store.model.UserModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Properties;

public class ModelDao {

    private SessionFactory sessionFactory;

    public ModelDao() {

        Configuration configuration = new Configuration();
        Properties properties = new Properties();
        properties.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
        properties.put(Environment.URL, "jdbc:mysql://localhost:3306/magazin_online");
        properties.put(Environment.USER, "root");
        properties.put(Environment.PASS, "MySQL2020#$");
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
        properties.put(Environment.SHOW_SQL, "true");
        properties.put(Environment.HBM2DDL_AUTO, "update");
        configuration.setProperties(properties);
        configuration.addAnnotatedClass(CartModel.class);
        configuration.addAnnotatedClass(ProductModel.class);
        configuration.addAnnotatedClass(UserModel.class);

        sessionFactory = configuration.buildSessionFactory();

    }

    public void addUser(UserModel userModel) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(userModel);
        transaction.commit();
    }

    public UserModel findUserById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        UserModel userModel = session.find(UserModel.class, id);
        transaction.commit();
        return userModel;
    }

    public UserModel findUserByUsername(String name) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from UserModel where userName = '" + name + "'", UserModel.class);
        transaction.commit();
        List<UserModel> userModelList = query.getResultList();
        return userModelList.get(0);
    }

    public void addCart(CartModel cartModel) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(cartModel);
        transaction.commit();
    }

    public void addProduct(ProductModel productModel) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(productModel);
        transaction.commit();
    }

    public ProductModel findProductById(int id) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        ProductModel productModel = session.find(ProductModel.class, id);
        transaction.commit();
        return productModel;
    }

    public void updateUser(UserModel userModel) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(userModel);
        transaction.commit();
    }

    public List<ProductModel> getProducts() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from ProductModel", ProductModel.class);
        transaction.commit();
        return query.getResultList();
    }

    public void updateProducts(ProductModel productModel) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(productModel);
        transaction.commit();
    }

    public void updateCart(CartModel cartModel) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(cartModel);
        transaction.commit();
    }

}
