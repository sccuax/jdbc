<%@ page import="models.entity.Usuario" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" %>

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
    <h2>Usuarios</h2>

    <table border="1">
        <tr>
            <th>ID</th><th>Nombre</th><th>Correo</th><th>Teléfono</th><th>Acciones</th>
        </tr>

<%
    List<Usuario> usuarios = (List<Usuario>) request.getAttribute("usuarios");
    if (usuarios != null) {
        for (Usuario u : usuarios) {
%>
        <tr>
            <td><%= u.getId() %></td>
            <td><%= u.getNombre() %></td>
            <td><%= u.getCorreo() %></td>
            <td><%= u.getTelefono() %></td>
            <td>
                <a href="UsuarioServlet?action=edit&id=<%= u.getId() %>">Editar</a> |
                <a href="UsuarioServlet?action=delete&id=<%= u.getId() %>" onclick="return confirm('¿Estás seguro de eliminar este usuario?');">Eliminar</a>
            </td>
        </tr>
<%
        }
    } else {
%>
        <tr><td colspan="5">No hay usuarios para mostrar</td></tr>
<%
    }
%>
    </table>

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
    </form>
</body>
</html>