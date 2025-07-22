<%@ page import="models.entity.Usuario" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>CRUD Usuarios</title>
    <style>
    body {
      font-family: Arial, sans-serif;
      margin: 40px;
      background-color: #f5f7fa;
      color: #333;
    }

    h2, h3 {
      color: #2c3e50;
    }

    table {
      border-collapse: collapse;
      width: 100%;
      margin-bottom: 30px;
    }

    table, th, td {
      border: 1px solid #ccc;
    }

    th, td {
      padding: 12px;
      text-align: left;
    }

    tr:nth-child(even) {
      background-color: #f9f9f9;
    }

    tr:hover {
      background-color: #eef;
    }

    form {
      background-color: #fff;
      padding: 20px;
      border: 1px solid #ddd;
      border-radius: 8px;
      max-width: 400px;
    }

    input[type="text"] {
      width: 100%;
      padding: 8px;
      margin: 6px 0 12px 0;
      border: 1px solid #ccc;
      border-radius: 4px;
    }

    input[type="submit"], .action-button {
      background-color: #3498db;
      color: white;
      border: none;
      padding: 10px 16px;
      margin: 4px 2px;
      border-radius: 4px;
      cursor: pointer;
      text-decoration: none;
      display: inline-block;
    }

    input[type="submit"]:hover, .action-button:hover {
      background-color: #2980b9;
    }

    .action-buttons {
      display: flex;
      gap: 8px;
    }
  </style>
</head>
<body>
    
    <c:set var="enEdicion" value="${not empty usuario}" />
    
    <c:if test="${enEdicion}">
    <div style="background: #fffae6; padding:10px; margin-bottom:10px; border:1px solid #ffd700;">
        ⚠️ Estás editando un usuario. Verifica los datos antes de guardar.
    </div>
    </c:if>
    <%
    Usuario usuarioEditando = (Usuario) request.getAttribute("usuario");
    boolean enEdicion = (usuarioEditando != null);
    List<Usuario> usuarios = (List<Usuario>) request.getAttribute("usuarios");
    %>

<c:if test="${!enEdicion}">
    <h2>Usuarios</h2>
    <table border="1" width="100%">
        <tr><th>ID</th><th>Nombre</th><th>Correo</th><th>Teléfono</th><th>Acciones</th></tr>
        <c:choose>
            <c:when test="${not empty usuarios}">
                <c:forEach var="u" items="${usuarios}">
                    <tr>
                        <td>${u.id}</td>
                        <td>${u.nombre}</td>
                        <td>${u.correo}</td>
                        <td>${u.telefono}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/UsuarioServlet?action=edit&id=${u.id}">Editar</a> |
                            <a href="${pageContext.request.contextPath}/UsuarioServlet?action=delete&id=${u.id}"
                               onclick="return confirm('¿Eliminar usuario ${u.nombre}?')">Eliminar</a>
                        </td>
                    </tr>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <tr><td colspan="5">No hay usuarios para mostrar</td></tr>
            </c:otherwise>
        </c:choose>
    </table>
</c:if>

    <br><br>
    
    <h3><%= request.getAttribute("usuario") != null ? "Editar usuario" : "Crear nuevo usuario" %></h3>

    <form action="${pageContext.request.contextPath}/UsuarioServlet" method="post">
        <input type="hidden" name="action" value="<%= request.getAttribute("usuario") != null ? "update" : "create" %>">
        <%
            Usuario usuarioForm = (Usuario) request.getAttribute("usuario");
            boolean editando = usuarioForm != null;
        %>
        <% if (editando) { %>
            <input type="hidden" name="id" value="<%= usuarioForm.getId() %>">
        <% } %>

        Nombre: <input type="text" name="nombre" value="<%= editando ? usuarioForm.getNombre() : "" %>"><br>
        Correo: <input type="text" name="correo" value="<%= editando ? usuarioForm.getCorreo() : "" %>"><br>
        Teléfono: <input type="text" name="telefono" value="<%= editando ? usuarioForm.getTelefono() : "" %>"><br>
        <input type="submit" value="<%= editando ? "Actualizar" : "Guardar" %>">
        <c:if test="${enEdicion}">
        <a href="${pageContext.request.contextPath}/UsuarioServlet?action=list">Cancelar edición</a>
    </c:if>
    </form>
</body>
</html>