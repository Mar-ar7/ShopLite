package com.darwinruiz.shoplite.repositories;

import com.darwinruiz.shoplite.models.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class ProductRepository {
    private static final List<Product> DATA = new ArrayList<>();
    private static final AtomicLong ID = new AtomicLong(1000);

    static {
        DATA.add(new Product(ID.incrementAndGet(), "Teclado mecánico", 59.99));
        DATA.add(new Product(ID.incrementAndGet(), "Mouse inalámbrico", 29.90));
        DATA.add(new Product(ID.incrementAndGet(), "Monitor 24\"", 139.00));
        DATA.add(new Product(ID.incrementAndGet(), "Headset", 49.90));
        DATA.add(new Product(ID.incrementAndGet(), "Webcam", 39.50));
    }

    public List<Product> findAll() {
        return DATA;
    }

    public void add(Product p) {
        DATA.add(p);
    }

    public long nextId() {
        return ID.incrementAndGet();
    }
}