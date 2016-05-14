package at.ac.tuwien.big.we16.ue3.model;

import javax.persistence.*;

@Entity
public class RelatedProduct {

    @Id
    private String id;

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "baseProduct", nullable = false)
    private Product product;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
