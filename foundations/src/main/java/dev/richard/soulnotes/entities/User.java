package dev.richard.soulnotes.entities;

import java.util.Arrays;

public class User {
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private byte[] passwordHash;
    private byte[] salt;
    private int roleId;

    private Roles roleType;
    private Password passGen;



    public User() {

    }

    /**
     * Constructor for making a new user.
     * @param userId
     * @param firstName
     * @param lastName
     * @param email
     * @param username
     * @param passwordHash
     * @param salt
     * @param roleType
     */
    public User(int userId, String firstName, String lastName, String email, String username, byte[] passwordHash, byte[] salt, Roles roleType) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.passwordHash = passwordHash;
        this.salt = salt;
        this.roleType = roleType;
    }
/**
 * Constructor for making a new user.
 * @param userId
 * @param firstName
 * @param lastName
 * @param email
 * @param username
 * @param password
 * @param roleId
 */
    public User(int userId, String firstName, String lastName, String email, String username, String password) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(byte[] passwordHash) {
        if (password != null) {

        }
        this.passwordHash = passwordHash;
    }
    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public Roles getRoleType() {
        return roleType;
    }

    public void setRoleType(Roles roleType) {
        this.roleType = Roles.from(roleId);
    }

    public int getRoleId() {
        return roleType.Value;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", passwordHash='" + Arrays.toString(passwordHash) + '\'' +
                ", salt='" + Arrays.toString(salt) + '\'' +
                ", roleId=" + roleId +
                ", roleType=" + roleType +
                '}';
    }
}

