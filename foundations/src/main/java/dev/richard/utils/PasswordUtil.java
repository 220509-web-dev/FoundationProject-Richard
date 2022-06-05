package dev.richard.utils;

import dev.richard.entities.Password;
import org.apache.commons.lang3.exception.ExceptionUtils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class PasswordUtil {
    public static Password generatePassword(String password) {
        KeySpec keySpec;

        SecretKeyFactory factory;
        SecureRandom random = new SecureRandom();

        byte[] salt = new byte[16];
        random.nextBytes(salt);
        keySpec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(keySpec).getEncoded();
            Password generated = new Password(hash, salt);
            return generated;
        } catch (NoSuchAlgorithmException e) {
            LoggerUtil.log(ExceptionUtils.getMessage(e), LogLevel.ERROR);
            return null;
        } catch (InvalidKeySpecException e) {
            LoggerUtil.log(ExceptionUtils.getMessage(e), LogLevel.ERROR);
            return null;
        }
    }
}
