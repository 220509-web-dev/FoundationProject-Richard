package dev.richard.entities;

public class User {
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private int roleId;

    private Roles roleType;

    public User() {

    }

    /**
     * Constructor for making a new user.
     * @param userId
     * @param firstName
     * @param lastName
     * @param email
     * @param username
     * @param password
     * @param roleType
     */
    public User(int userId, String firstName, String lastName, String email, String username, String password, Roles roleType) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.roleType = roleType;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Roles getRoleType() {
        return roleType;
    }

    public void setRoleType(Roles roleType) {
        this.roleType = roleType;
    }

    public int getRoleId() {
        return roleType.Value;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}

