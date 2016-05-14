package at.ac.tuwien.big.we16.ue3.service;

import at.ac.tuwien.big.we16.ue3.exception.ProductNotFoundException;
import at.ac.tuwien.big.we16.ue3.model.Bid;
import at.ac.tuwien.big.we16.ue3.model.Product;
import at.ac.tuwien.big.we16.ue3.model.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProductService {

    private static EntityManagerFactory emfactory;
    private static EntityManager em;

    public ProductService(){
        emfactory = Persistence.createEntityManagerFactory("defaultPersistenceUnit");
        em = emfactory.createEntityManager();
    }

    public Collection<Product> getAllProducts() {

        //TODO: read from db
        TypedQuery<Product> q = em.createQuery("select p from Product p", Product.class);
        return q.getResultList();
    }

    public Product getProductById(String id) throws ProductNotFoundException {

       //TODO: read from db

        return em.find(Product.class, id);
    }

    public void createProduct(Product p){
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
    }

    //TODO: write changed users and products to db
    public Collection<Product> checkProductsForExpiration() {
        Collection<Product> newlyExpiredProducts = new ArrayList<>();
        for (Product product : this.getAllProducts()) {
            em.getTransaction().begin();
            if (!product.hasExpired() && product.hasAuctionEnded()) {
                product.setExpired();
                newlyExpiredProducts.add(product);
                if (product.hasBids()) {
                    Bid highestBid = product.getHighestBid();
                    for (User user : product.getUsers()) {
                        //em.getTransaction().begin();
                        user.decrementRunningAuctions();
                        if (highestBid.isBy(user)) {
                            user.incrementWonAuctionsCount();
                        }
                        else {
                            user.incrementLostAuctionsCount();
                        }
                        //em.getTransaction().commit();
                    }
                }
            }
            em.getTransaction().commit();
        }
        return newlyExpiredProducts;
    }
}
