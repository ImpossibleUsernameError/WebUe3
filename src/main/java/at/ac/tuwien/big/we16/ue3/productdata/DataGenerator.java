package at.ac.tuwien.big.we16.ue3.productdata;

import at.ac.tuwien.big.we16.ue3.model.Product;
import at.ac.tuwien.big.we16.ue3.model.ProductType;
import at.ac.tuwien.big.we16.ue3.model.User;
import at.ac.tuwien.big.we16.ue3.service.ProductService;
import at.ac.tuwien.big.we16.ue3.service.ServiceFactory;
import at.ac.tuwien.big.we16.ue3.service.UserService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;


public class DataGenerator {

    private static UserService userService = ServiceFactory.getUserService();
    private static ProductService productService = ServiceFactory.getProductService();

    public void generateData() {
        generateUserData();
        generateProductData();
        insertRelatedProducts();
    }

    private void generateUserData() {
        // TODO add the computer user to the database
        User comUser = new User();
        comUser.setEmail("comUser@comUsers.at");
        comUser.increaseBalance(Integer.MAX_VALUE);
        comUser.setDate(GregorianCalendar.from(ZonedDateTime.of(LocalDateTime.of(1970, 1, 1, 0,0), ZoneId.of("Europe/Berlin"))).getTime());
        comUser.setFirstname("Computer");
        comUser.setLastname("User");
        comUser.setPassword("nothing");
        userService.createUser(comUser);
    }

    private void generateProductData() {
        // TODO load products via JSONDataLoader and write them to the database
        List<Product> products = new LinkedList<>();
        for(JSONDataLoader.Music m : JSONDataLoader.getMusic()){
            Product p = new Product();
            p.setId(m.getId());
            p.setName(m.getAlbum_name());
            p.setImage(m.getImg());
            p.setImageAlt(m.getArtist() + ", " + m.getAlbum_name());
            p.setProducer(m.getArtist());
            p.setAuctionEnd(GregorianCalendar.from(ZonedDateTime.of(LocalDateTime.parse(m.getEnd_time()), ZoneId.of("Europe/Berlin"))).getTime());
            p.setExpired(p.getAuctionEnd().before(new Date()));
            p.setType(ProductType.ALBUM);
            p.setYear(Integer.parseInt(m.getYear()));
            products.add(p);
        }
        for(JSONDataLoader.Book b : JSONDataLoader.getBooks()){
            Product p = new Product();
            p.setId(b.getId());
            p.setName(b.getTitle());
            p.setImage(b.getImg());
            p.setImageAlt(b.getAuthor() + ", " + b.getTitle());
            p.setProducer(b.getAuthor());
            p.setAuctionEnd(GregorianCalendar.from(ZonedDateTime.of(LocalDateTime.parse(b.getEnd_time()), ZoneId.of("Europe/Berlin"))).getTime());
            p.setExpired(p.getAuctionEnd().before(new Date()));
            p.setType(ProductType.BOOK);
            p.setYear(Integer.parseInt(b.getYear()));
            products.add(p);
        }
        for(JSONDataLoader.Movie m : JSONDataLoader.getFilms()){
            Product p = new Product();
            p.setId(m.getId());
            p.setName(m.getTitle());
            p.setImage(m.getImg());
            p.setImageAlt(m.getDirector() + ", " + m.getTitle());
            p.setProducer(m.getDirector());
            p.setAuctionEnd(GregorianCalendar.from(ZonedDateTime.of(LocalDateTime.parse(m.getEnd_time()), ZoneId.of("Europe/Berlin"))).getTime());
            p.setExpired(p.getAuctionEnd().before(new Date()));
            p.setType(ProductType.FILM);
            p.setYear(Integer.parseInt(m.getYear()));
            products.add(p);
        }
        for(Product p : products){
            productService.createProduct(p);
        }
    }

    private void insertRelatedProducts() {
        // TODO load related products from dbpedia and write them to the database
    }
}
