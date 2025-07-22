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
 */  /*

    La controladora de persistencia es una instancia del archivo jpa y model.usuario para dar persistencia a las clases controladoras
*/
public class ControladoraPersistencia {
    
    private UsuarioJpaController usuarioJpa = new UsuarioJpaController();

    // clase para guardar usuarios en la base de datos a traves del servlet
    public void guardarUsuario(String nombre, String correo, String telefono) {
        Usuario user = new Usuario();
        user.setNombre(nombre);
        user.setCorreo(correo);
        user.setTelefono(telefono);
        usuarioJpa.create(user);
    }

    //clase para mostrar la lista de los usuarios a traves del servlet
    public List<Usuario> getUsuarios() {
        return usuarioJpa.findUsuarios();
    }

    //clase para editar los usuarios a traves del servlet
    public void editarUsuario(Usuario user) {
        usuarioJpa.edit(user);
    }

    //clase para eliminar los usuarios a traves del servlet
    public void eliminarUsuario(Long id) {
        usuarioJpa.delete(id);
    }

    //clase para mostrar el usuario en el form al dar en editar a traves del servlet
    public Usuario getUsuario(Long id) {
        return usuarioJpa.findUsuario(id);
    }
    
}
