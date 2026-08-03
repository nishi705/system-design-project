package com.urlshortening;

import org.springframework.context.annotation.Configuration;

import java.math.BigInteger;
import java.security.SecureRandom;

@Configuration
public class Base62 {

    private static final char[] ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final int RADIX = ALPHABET.length;
    private static final SecureRandom RNG = new SecureRandom();

    public Base62(){}

//SecureRandom + Base62 encoding. to generate the unique code
    public static String randomCode(int length){
        int bits = (int) Math.ceil(length * 6.0);
        byte[] buf = new byte[(bits + 7)/8];
        RNG.nextBytes(buf);
        return encode(new BigInteger(1, buf)).substring(0, length);


    }

    private static String encode(BigInteger number){
        if(number.signum() == 0) return "0";
        BigInteger radix = BigInteger.valueOf(RADIX);
        StringBuilder sb = new StringBuilder();
        while(number.signum() > 0){
            BigInteger[] devRem = number.divideAndRemainder(radix);
            sb.append(ALPHABET[devRem[1].intValue()]);
            number = devRem[0];
        }

         return sb.reverse().toString();

    }
}
