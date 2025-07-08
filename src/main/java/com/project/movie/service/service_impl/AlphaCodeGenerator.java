package com.project.movie.service.service_impl;

import java.security.SecureRandom;


public class AlphaCodeGenerator {

    private final String alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final SecureRandom secureRandom = new SecureRandom();

    public String generateCode(){
        StringBuilder code = new StringBuilder(6);
        for (int i=0;i<6;i++){
            int index = secureRandom.nextInt(alphabets.length());
            code.append(alphabets.charAt(index));
        }
        return code.toString();
    }

}
