<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html lang="es">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1">
    <title>ShopLite • Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<nav class="navbar navbar-expand-lg bg-white border-bottom">
    <div class="container">
        <a class="navbar-brand text-danger" href="${pageContext.request.contextPath}/home">ShopLite • Admin</a>
    </div>
</nav>

<section class="container my-5">
    <c:if test="${param.err=='1'}">
        <div class="alert alert-danger">Datos inválidos</div>
    </c:if>

    <!-- (Agregar / Editar) -->
    <div class="card shadow-sm mb-4">
        <div class="card-body p-4">
            <h3 class="mb-3">
                <c:choose>
                    <c:when test="${not empty editProduct}">Editar producto</c:when>
                    <c:otherwise>Nuevo producto</c:otherwise>
                </c:choose>
            </h3>
            <form method="post" action="${pageContext.request.contextPath}/admin" class="row g-3">
                <input type="hidden" name="id" value="${editProduct.id}"/>
                <input type="hidden" name="action" value="<c:choose><c:when test='${not empty editProduct}'>update</c:when><c:otherwise>add</c:otherwise></c:choose>"/>
                <div class="col-12">
                    <label class="form-label">Nombre</label>
                    <input class="form-control" name="name" placeholder="Teclado 60%" value="${editProduct.name}"/>
                </div>
                <div class="col-12">
                    <label class="form-label">Precio</label>
                    <input class="form-control" name="price" placeholder="59.99" value="${editProduct.price}"/>
                </div>
                <div class="col-12">
                    <label class="form-label">Stock</label>
                    <input class="form-control" name="stock" placeholder="10" value="${editProduct.stock}"/>
                </div>
                <div class="col-12">
                    <button class="btn btn-primary">
                        <c:choose>
                            <c:when test="${not empty editProduct}">Actualizar</c:when>
                            <c:otherwise>Guardar</c:otherwise>
                        </c:choose>
                    </button>
                    <c:if test="${not empty editProduct}">
                        <a href="${pageContext.request.contextPath}/admin" class="btn btn-secondary">Cancelar</a>
                    </c:if>
                </div>
            </form>
        </div>
    </div>

    <!-- Lista de productos en tarjetas -->
    <div class="row row-cols-1 row-cols-md-3 g-4">
        <c:forEach var="p" items="${products}">
            <div class="col">
                <div class="card h-100 shadow-sm">
                    <div class="card-body">
                        <h5 class="card-title">${p.name}</h5>
                        <p class="card-text">
                            Precio: $${p.price} <br/>
                            Stock: ${p.stock}
                        </p>
                        <div class="d-flex justify-content-between">
                            <a href="${pageContext.request.contextPath}/admin?action=edit&id=${p.id}" class="btn btn-sm btn-warning">Editar</a>
                            <form method="post" action="${pageContext.request.contextPath}/admin" style="display:inline;">
                                <input type="hidden" name="id" value="${p.id}"/>
                                <input type="hidden" name="action" value="delete"/>
                                <button type="submit" class="btn btn-sm btn-danger" onclick="return confirm('¿Eliminar este producto?')">Eliminar</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</section>
</body>
</html>
