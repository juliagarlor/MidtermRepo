package com.ironhack.theBestMidtermProject.utils.classes;

import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;

public class PasswordUtil {
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("1234"));
    }
}
