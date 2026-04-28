package utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.joda.time.Instant;

import pojo.Order;

public class DataUtility {
    public static String buildUserJson(int id, String username, String firstName, String lastName,
                                 String email, String password, String phone, int userStatus) {
        return String.format(
                "{\"id\":%d,\"username\":\"%s\",\"firstName\":\"%s\",\"lastName\":\"%s\"," +
                "\"email\":\"%s\",\"password\":\"%s\",\"phone\":\"%s\",\"userStatus\":%d}",
                id, username, firstName, lastName, email, password, phone, userStatus
        );
    }

    public static String buildUserJson(Map<String, String> row) {
        return buildUserJson(
                Integer.parseInt(row.get("id")),
                row.get("username"),
                row.get("firstName"),
                row.get("lastName"),
                row.get("email"),
                row.get("password"),
                row.get("phone"),
                Integer.parseInt(row.get("userStatus"))
        );
    }
    
    public static Order getOrderRequestBody() {
    	Order order = new Order();
    	Random random = new Random();
    	Integer orderId = random.nextInt(1000)+1;
    	Integer petId = random.nextInt(1000)+1;
    	Integer quantity = random.nextInt(10)+1;
    	Boolean complete = random.nextBoolean();
    	String shipDate = Instant.now().toString();
    	List<String> listOfStatus = Arrays.asList("placed", "approved", "delivered");
    	String status = listOfStatus.get(random.nextInt(listOfStatus.size()));
    	order.setId(orderId);
    	order.setPetId(petId);
    	order.setQuantity(quantity);
    	order.setShipDate(shipDate);
    	order.setStatus(status);
    	order.setComplete(complete);
    	return order;
    }
}
