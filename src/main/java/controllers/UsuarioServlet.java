package controllers;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import models.persistence.ControladoraPersistencia;
import models.entity.Usuario;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

//path para las actiones del crud
@WebServlet(name = "UsuarioServlet", urlPatterns = {"/UsuarioServlet"})
public class UsuarioServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(UsuarioServlet.class.getName());
    private ControladoraPersistencia controladora = new ControladoraPersistencia();

    
    //metodo para traer la lista de los usuarios desde la base de datos
    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String action = request.getParameter("action");

    logger.log(Level.INFO, "Acción recibida en doGet: {0}", action);

    if (action == null || action.isEmpty()) {
        // Aquí rediriges a la acción "list" por defecto
        response.sendRedirect("UsuarioServlet?action=list"); 
        return;
    }

    switch (action) {
        case "list":
            listarUsuarios(request, response);
            break;
        case "delete":
            eliminarUsuario(request, response);
            break;
        case "edit":
            cargarUsuario(request, response);
            break;
        default:
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no soportada");
            break;
    }
}

// metodo para agregar usuarios nuevos a la base de datos
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        logger.log(Level.INFO, "Acción recibida en doPost: {0}", action);

        if (action == null || action.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no especificada");
            return;
        }

        if (action.equals("create")) {
            String nombre = request.getParameter("nombre");
            String correo = request.getParameter("correo");
            String telefono = request.getParameter("telefono");

            logger.log(Level.INFO, "Creando usuario: {0}, {1}, {2}", new Object[]{nombre, correo, telefono});

            controladora.guardarUsuario(nombre, correo, telefono);
        } else if (action.equals("update")) {
            long id = Long.parseLong(request.getParameter("id"));
            String nombre = request.getParameter("nombre");
            String correo = request.getParameter("correo");
            String telefono = request.getParameter("telefono");

            Usuario user = controladora.getUsuario(id);
            if (user == null) {
                logger.severe("Usuario no encontrado para actualizar. ID: " + id);
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuario no encontrado");
                return;
            }

            user.setNombre(nombre);
            user.setCorreo(correo);
            user.setTelefono(telefono);
            controladora.editarUsuario(user);
        } else {
            logger.warning("Acción POST no reconocida: " + action);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida en POST");
            return;
        }
        //se redirege a la lista en ves de al index para que aparescan toda la info de los usuarios.
        response.sendRedirect("UsuarioServlet?action=list");
    }

    private void listarUsuarios(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Usuario> usuarios = controladora.getUsuarios();
        System.out.println("Usuarios obtenidos en servlet: " + (usuarios != null ? usuarios.size() : "null"));

        // 👇 Comprobación: Mostrar en consola si hay usuarios
        if (usuarios != null && !usuarios.isEmpty()) {
            System.out.println("Número de usuarios obtenidos: " + usuarios.size());
            logger.log(Level.INFO, "Usuarios obtenidos: {0}", usuarios.size());
        } else {
            System.out.println("No se encontraron usuarios en la base de datos.");
            logger.warning("No se encontraron usuarios en la base de datos.");
        }

        // 👇 Pasar la lista al JSP
        request.setAttribute("usuarios", usuarios);

        // 👇 Redirigir al JSP
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    //metodo para eliminar usuarios
    private void eliminarUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            logger.log(Level.INFO, "Eliminando usuario con ID: {0}", id);

            controladora.eliminarUsuario(id);

            response.sendRedirect("UsuarioServlet?action=list");
        } catch (NumberFormatException e) {
            logger.log(Level.SEVERE, "ID inválido para eliminar usuario", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID inválido");
        }
    }

    //metodo para cargar el usuario a editar en el form
    private void cargarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            logger.log(Level.INFO, "Cargando usuario con ID: {0}", id);

            Usuario usuario = controladora.getUsuario(id);
            if (usuario == null) {
                logger.warning("Usuario no encontrado. ID: " + id);
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Usuario no encontrado");
                return;
            }

            request.setAttribute("usuario", usuario);
            List<Usuario> usuarios = controladora.getUsuarios();
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            logger.log(Level.SEVERE, "ID inválido para cargar usuario", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID inválido");
        }
    }
}