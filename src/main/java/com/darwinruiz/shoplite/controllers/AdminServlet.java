package com.darwinruiz.shoplite.controllers;

import com.darwinruiz.shoplite.models.Product;
import com.darwinruiz.shoplite.repositories.ProductRepository;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    private final ProductRepository repo = new ProductRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int page = 1;
        int size = 10;

        try {
            page = Integer.parseInt(req.getParameter("page"));
            if (page < 1) page = 1;
        } catch (NumberFormatException ignored) {}

        try {
            size = Integer.parseInt(req.getParameter("size"));
            if (size < 1) size = 10;
        } catch (NumberFormatException ignored) {}

        int total = repo.count();
        int offset = (page - 1) * size;
        List<Product> products = repo.findAll(size, offset);

        req.setAttribute("products", products);
        req.setAttribute("page", page);
        req.setAttribute("size", size);
        req.setAttribute("total", total);


        String action = req.getParameter("action");
        if ("edit".equals(action)) {
            try {
                Long id = Long.parseLong(req.getParameter("id"));
                Product product = repo.findById(id).orElse(null);
                req.setAttribute("editProduct", product);
            } catch (NumberFormatException ignored) {}
        }

        try {
            req.getRequestDispatcher("/admin.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");
        String idStr = req.getParameter("id");
        Long id = null;

        try { if (idStr != null) id = Long.parseLong(idStr); } catch (NumberFormatException ignored) {}

        if ("delete".equals(action) && id != null) {
            repo.delete(id);
        } else {
            String name = req.getParameter("name");
            String priceStr = req.getParameter("price");
            String stockStr = req.getParameter("stock");

            double price = 0;
            int stock = 0;
            try { price = Double.parseDouble(priceStr); } catch (NumberFormatException ignored) {}
            try { stock = Integer.parseInt(stockStr); } catch (NumberFormatException ignored) {}

            if (name == null || name.trim().isEmpty() || price <= 0) {
                resp.sendRedirect(req.getContextPath() + "/admin?err=1");
                return;
            }

            Product product = new Product(id != null ? id : 0L, name.trim(), price, stock);

            if ("update".equals(action) && id != null) {
                repo.update(product);
            } else {
                repo.add(product);
            }
        }

        resp.sendRedirect(req.getContextPath() + "/admin");
    }
}
