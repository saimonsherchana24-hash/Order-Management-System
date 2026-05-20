package aptProject.model;

/**
 * User — model class representing a registered user of the system.
 *
 * <p>Holds all user account data: identity fields (name, username, email),
 * a SHA-256 password hash, a role that controls access (USER or ADMIN),
 * and an optional profile image path. Instances are created during
 * registration and populated from the database on login.</p>
 */
public class User {

    // ── Fields ────────────────────────────────────────────────────────────────

    private int id;                // auto-generated primary key from the database
    private String fullName;       // user's display name (e.g. "Jane Smith")
    private String username;       // unique login handle
    private String email;          // unique email address
    private String passwordHash;   // SHA-256 hex hash — never the plain-text password
    private String role;           // "USER" or "ADMIN" — controls page access
    private String profileImage;   // context-relative path to uploaded profile picture

    // ── Constructors ──────────────────────────────────────────────────────────

    /** Default no-arg constructor — required by the DAO mapper. */
    public User() {}

    /**
     * Convenience constructor for creating a new user during registration.
     *
     * @param fullName     the user's full display name
     * @param username     the chosen login handle
     * @param email        the user's email address
     * @param passwordHash the pre-computed SHA-256 hash of the password
     * @param role         the access role ("USER" or "ADMIN")
     */
    public User(String fullName, String username, String email, String passwordHash, String role) {
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    // ── Getters and Setters ───────────────────────────────────────────────────

    /** Returns the database-assigned user ID. */
    public int getId(){ 
        return id; 
    }
    /** Sets the user ID (called by the DAO after reading from the database). */
    public void setId(int id){ 
        this.id = id; 
    }

    /** Returns the user's full display name. */
    public String getFullName(){
        return fullName; 
    }
    /** Updates the user's full display name. */
    public void setFullName(String fullName){ 
        this.fullName = fullName;
    }

    /** Returns the unique username used for login. */
    public String getUsername() { 
        return username;
    }
    /** Sets the username. */
    public void setUsername(String username) {
        this.username = username;
    }

    /** Returns the user's email address. */
    public String getEmail() {
        return email;
    }
    /** Sets the email address. */
    public void setEmail(String email) {
        this.email = email; 
    }

    /** Returns the stored SHA-256 password hash. */
    public String getPasswordHash() {
        return passwordHash;
    }
    /** Sets the password hash (must be pre-hashed before calling this). */
    public void setPasswordHash(String p) {
        this.passwordHash = p;
    }

    /** Returns the user's role ("USER" or "ADMIN"). */
    public String getRole() {
        return role;
    }
    /** Sets the user's role. */
    public void setRole(String role) {
        this.role = role;
    }

    /** Returns the context-relative path to the profile image, or {@code null} if not set. */
    public String getProfileImage()  { 
        return profileImage;
    }
    /** Sets the profile image path (e.g. {@code /Resource/profiles/user_1_123.jpg}). */
    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    // ── Utility ───────────────────────────────────────────────────────────────

    /**
     * Returns the first letter of the user's full name in upper case.
     * Used as a fallback avatar initial when no profile image is set.
     *
     * @return a single upper-case character, or {@code "A"} if the name is empty
     */
    public String getInitial() {
        return (fullName != null && !fullName.isEmpty())
               ? fullName.substring(0, 1).toUpperCase() : "A";
    }
}
