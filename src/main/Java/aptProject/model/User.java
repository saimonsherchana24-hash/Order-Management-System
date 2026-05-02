package aptProject.model;

public class User {
    private int id;
    private String fullName;
    private String username;
    private String email;
    private String passwordHash;
    private String role;

    public User() {
    }

    public User(String fullName, String username, String email, String passwordHash, String role) {
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}