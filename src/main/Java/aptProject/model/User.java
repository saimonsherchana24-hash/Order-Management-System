package aptProject.model;

public class User {
    private int id;
    private String fullName;
    private String username;
    private String email;
    private String passwordHash;
    private String role;
    private String profileImage; // path to uploaded profile picture

    public User() {}

    public User(String fullName, String username, String email, String passwordHash, String role) {
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    public int getId()                              { return id; }
    public void setId(int id)                       { this.id = id; }

    public String getFullName()                     { return fullName; }
    public void setFullName(String fullName)        { this.fullName = fullName; }

    public String getUsername()                     { return username; }
    public void setUsername(String username)        { this.username = username; }

    public String getEmail()                        { return email; }
    public void setEmail(String email)              { this.email = email; }

    public String getPasswordHash()                 { return passwordHash; }
    public void setPasswordHash(String p)           { this.passwordHash = p; }

    public String getRole()                         { return role; }
    public void setRole(String role)                { this.role = role; }

    public String getProfileImage()                 { return profileImage; }
    public void setProfileImage(String profileImage){ this.profileImage = profileImage; }

    /** Returns initials if no profile image set */
    public String getInitial() {
        return (fullName != null && !fullName.isEmpty())
               ? fullName.substring(0, 1).toUpperCase() : "A";
    }
}