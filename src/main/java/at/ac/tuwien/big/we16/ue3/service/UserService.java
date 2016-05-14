package at.ac.tuwien.big.we16.ue3.service;

import at.ac.tuwien.big.we16.ue3.exception.UserNotFoundException;
import at.ac.tuwien.big.we16.ue3.model.User;
import org.h2.tools.Server;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.sql.SQLException;

public class UserService {

    private static EntityManagerFactory emfactory;
    private static EntityManager em;

    public UserService(){
        emfactory = Persistence.createEntityManagerFactory("defaultPersistenceUnit");
        em = emfactory.createEntityManager();
    }

    public void createUser(User user) {

        //TODO: write to db
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();

    }

    public User getUserByEmail(String email) throws UserNotFoundException {

        //TODO: read from db
        TypedQuery<User> q = em.createQuery("select u from User u where u.email=" + "'" + email + "'", User.class);
        return q.getSingleResult();
    }


}
