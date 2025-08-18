package dev.jolvera.finjav.utils

import org.mindrot.jbcrypt.BCrypt

class PasswordUtils {
    companion object {
        fun hashPassword(password: String): String {
            return BCrypt.hashpw(password, BCrypt.gensalt(12))
        }

        fun verifyPassword(password: String, expectedPassword: String): Boolean {
            return BCrypt.checkpw(password, expectedPassword)
        }
    }
}