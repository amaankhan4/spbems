package com.main.customerService.Utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class passwordUtils {
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static String encryptPassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }

    public static boolean matchPassword(String plainPassword, String encryptedPassword) {
        
        return passwordEncoder.matches(plainPassword, encryptedPassword);
    }

}
