package com.darwinruiz.shoplite.controllers;

import com.darwinruiz.shoplite.models.Product;
import com.darwinruiz.shoplite.repositories.ProductRepository;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private final ProductRepository repo = new ProductRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Product> all = repo.findAll();

        int page = 1;
        int size = 5;

        // Validar page
        try {
            page = Integer.parseInt(req.getParameter("page"));
            if (page < 1) page = 1;
        } catch (NumberFormatException ignored) {}

        // Validar size
        try {
            size = Integer.parseInt(req.getParameter("size"));
            if (size < 1) size = 5;
        } catch (NumberFormatException ignored) {}

        int total = all.size();
        int fromIndex = Math.min(Math.max(0, (page - 1) * size), total);
        int toIndex = Math.min(fromIndex + size, total);

        List<Product> items = (fromIndex >= toIndex) ?
                Collections.emptyList() :
                all.subList(fromIndex, toIndex);

        req.setAttribute("items", items);
        req.setAttribute("page", page);
        req.setAttribute("size", size);
        req.setAttribute("total", total);

        try {
            req.getRequestDispatcher("/home.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }
}
