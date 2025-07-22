package models.jpa;

import jakarta.persistence.*;
import models.entity.Usuario;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

public class UsuarioJpaController {

    private static final Logger logger = Logger.getLogger(UsuarioJpaController.class.getName());
    private EntityManagerFactory emf;

    public UsuarioJpaController() {
        try {
            this.emf = Persistence.createEntityManagerFactory("CrudUsuariosPU");
            logger.log(Level.INFO, "EntityManagerFactory creado correctamente.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al inicializar EntityManagerFactory", e);
            throw e;
        }
    }

    public void create(Usuario user) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            System.out.println("Usuario creado: " + user);
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace(); 
            logger.log(Level.SEVERE, "Error al crear usuario", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarios() {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u", Usuario.class);
            List<Usuario> usuarios = query.getResultList();
            logger.log(Level.INFO, "Usuarios obtenidos: {0}", usuarios.size());
            return usuarios;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al obtener usuarios", e);
            return null;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public Usuario findUsuario(Long id) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            return em.find(Usuario.class, id);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al buscar usuario por ID", e);
            return null;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario user) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            //e.printStackTrace();
            logger.log(Level.SEVERE, "Error al editar usuario", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void delete(Long id) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            Usuario user = em.find(Usuario.class, id);
            if (user != null) {
                em.remove(user);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            logger.log(Level.SEVERE, "Error al eliminar usuario", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}