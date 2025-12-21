package com.example.inventory;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HashGen {
    public static void main(String[] args) {
        BCryptPasswordEncoder enc = new BCryptPasswordEncoder();

        System.out.println("adminpass -> " + enc.encode("adminpass"));
        System.out.println("testpass  -> " + enc.encode("testpass"));
    }
}
