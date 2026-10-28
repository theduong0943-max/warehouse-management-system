package com.warehouse.repository;

import com.warehouse.model.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

public class ProductRepositoryImpl implements ProductRepository {
    
    // Khởi tạo EntityManagerFactory dựa trên 'persistence.xml' bạn vừa tạo
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("WarehousePU");

    @Override
    public void save(Product product) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(product); // Thêm mới hoặc cập nhật vào CSDL
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public Optional<Product> findById(String id) {
        EntityManager em = emf.createEntityManager();
        try {
            Product product = em.find(Product.class, id);
            return Optional.ofNullable(product);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Product> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Product> searchProducts(String keyword) {
        EntityManager em = emf.createEntityManager();
        try {
            String jpql = "SELECT p FROM Product p WHERE LOWER(p.productId) LIKE LOWER(:kw) OR LOWER(p.name) LIKE LOWER(:kw)";
            return em.createQuery(jpql, Product.class)
                     .setParameter("kw", "%" + keyword + "%")
                     .getResultList();
        } finally {
            em.close();
        }
    }
}