package utils;

import java.util.Map;

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
}
