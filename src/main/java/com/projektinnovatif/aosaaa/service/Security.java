package com.projektinnovatif.aosaaa.service;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;

public class Security {

	public static final int PBKDF_ITERATIONS = 100000;
	
	@Value("${passwordresetvalidityminutes}")
	public static final int PASSWORD_RESET_EXPIRY_MINUTES = 30;
	
	/**
	 * Generates a password hash
	 * @param password
	 * @param salt
	 * @param iterations
	 * @return Password hash
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static String generatePasswordHash (String password, byte[] salt, int iterations) throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        char[] chars = password.toCharArray();
        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return toHex(hash);
    }
	
	
	/**
	 * Generates a hashing salt
	 * @param saltLength
	 * @return Hashing salt
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] getSalt(int saltLength) throws NoSuchAlgorithmException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[saltLength];
        sr.nextBytes(salt);
        return salt;
    }
	
	
	/** 
	 * Converts a byte array to a hexadecimal representation
	 * @param array
	 * @return Hexidecimal representation of the byte array
	 * @throws NoSuchAlgorithmException
	 */
	public static String toHex(byte[] array) throws NoSuchAlgorithmException
    {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0)
        {
            return String.format("%0"  +paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }
	
	
	/**
	 * Returns a SHA1 hash of a given string
	 * @param value
	 * @return
	 */
	public static String getSHA1Hash (String value) {
		return DigestUtils.sha1Hex(value);
	}
	 
	
}
