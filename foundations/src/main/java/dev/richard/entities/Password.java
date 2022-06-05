package dev.richard.entities;

import java.util.Arrays;

public class Password {
    private byte[] hash;
    private byte[] salt;

    public Password() {

    }

    public Password(byte[] hash, byte[] salt) {
        this.hash = hash;
        this.salt = salt;
    }

    public byte[] getHash() {
        return hash;
    }

    @Override
    public String toString() {
        return "Password{" +
                "hash=" + Arrays.toString(hash) +
                ", salt=" + Arrays.toString(salt) +
                '}';
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setHash(byte[] hash) {
        this.hash = hash;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

}
