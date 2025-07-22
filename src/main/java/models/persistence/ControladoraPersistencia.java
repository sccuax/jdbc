/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models.persistence;
import java.util.List;
import models.jpa.UsuarioJpaController;
import models.entity.Usuario;
/**
 *
 * @author LENOVO
 */
public class ControladoraPersistencia {
    
    private UsuarioJpaController usuarioJpa = new UsuarioJpaController();

    public void guardarUsuario(String nombre, String correo, String telefono) {
        Usuario user = new Usuario();
        user.setNombre(nombre);
        user.setCorreo(correo);
        user.setTelefono(telefono);
        usuarioJpa.create(user);
    }

    public List<Usuario> getUsuarios() {
        return usuarioJpa.findUsuarios();
    }

    public void editarUsuario(Usuario user) {
        usuarioJpa.edit(user);
    }

    public void eliminarUsuario(Integer id) {
        usuarioJpa.delete(id);
    }

    public Usuario getUsuario(Integer id) {
        return usuarioJpa.findUsuario(id);
    }
    
}
