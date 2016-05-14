package at.ac.tuwien.big.we16.ue3.productdata;

import at.ac.tuwien.big.we16.ue3.model.User;
import at.ac.tuwien.big.we16.ue3.service.ServiceFactory;
import at.ac.tuwien.big.we16.ue3.service.UserService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.GregorianCalendar;


public class DataGenerator {

   private static UserService userService = ServiceFactory.getUserService();

    public void generateData() {
        generateUserData();
        generateProductData();
        insertRelatedProducts();
    }

    private void generateUserData() {
        // TODO add the computer user to the database
        User comUser = new User();
        comUser.setId("1");
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
    }

    private void insertRelatedProducts() {
        // TODO load related products from dbpedia and write them to the database
    }
}
