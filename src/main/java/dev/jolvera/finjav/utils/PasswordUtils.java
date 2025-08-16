package dev.jolvera.finjav.utils;


import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtils {
    public static String HashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public static Boolean VerifyPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
