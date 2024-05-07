package utils;

import org.mindrot.jbcrypt.BCrypt;


public class Hash {

    private static int round = 10;

    public static String hashPassword(String password) {
        try {
            String salt = BCrypt.gensalt(round);
            String hashedPassword = BCrypt.hashpw(password, salt);
            return hashedPassword;
        } catch (Exception e) {
            // Handle the exception appropriately (e.g., log it or throw a custom exception)
            e.printStackTrace();
            return null;
        }
    }

    public static boolean checkPassword(String input , String hashed){
        boolean password_verified = false ;

//        if (login1 == null || login2 == null) {
//            throw new IllegalArgumentException("Invalid login objects provided for comparison");
//        }

        String password = input;
        String stored_hash = hashed;


        if (password == null || stored_hash == null) {
            return false;
        }

        // Check if the stored hash starts with the bcrypt prefix
        if (!stored_hash.startsWith("$2a$")) {
            // Handle the case where the stored hash is not a valid bcrypt hash
            return false;
        }

        password_verified = BCrypt.checkpw(password,stored_hash);

        return password_verified;
    }
}
