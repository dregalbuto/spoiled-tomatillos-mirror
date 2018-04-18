package edu.northeastern.cs4500.spoiledtomatillos.user.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import edu.northeastern.cs4500.spoiledtomatillos.groups.Group;
import edu.northeastern.cs4500.spoiledtomatillos.recommendations.Recommendation;
import edu.northeastern.cs4500.spoiledtomatillos.reviews.Review;
import edu.northeastern.cs4500.spoiledtomatillos.user.service.UserService;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.*;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;


/**
 * Class for a user of Spoiled Tomatillos
 */
@Data
@Entity(name = "users")
@JsonSerialize(using = UserSeralizer.class)
public class User {

    private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static SecureRandom rnd = new SecureRandom();
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty(value = "id")
    private int id;
    @JsonProperty(value = "first_name")
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    @JsonProperty(value = "last_name")
    private String lastName;
    @JsonProperty(value = "email")
    private String email;
    @JsonProperty(value = "username")
    //@Column(unique = true)
    private String username;
    @JsonProperty(value = "password")
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    //@JsonIgnore
    private String password;
    @JsonProperty(value = "enabled")
    private boolean enabled;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    //@JsonIgnore
    private String token;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    //@JsonIgnore
    private long tokenExpiration;
    /**
     * All of the roles this user has
     */
    @JsonManagedReference
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    @JsonProperty(value = "roles")
    private Collection<Role> roles;
    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonProperty(value = "reviews")
    private Collection<Review> reviews = new ArrayList<>();
    @JsonManagedReference
    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL)
    @JsonProperty(value = "recommendations")
    private Collection<Recommendation> recommendations = new ArrayList<>();
    //@OneToOne(mappedBy = "user", cascade = CascadeType.ALL,
    //        optional = false, fetch = FetchType.LAZY)
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    //@PrimaryKeyJoinColumn
    @JsonProperty(value = "friends")
    @JsonManagedReference
    private FriendList friends;

    @JsonBackReference
    @ManyToMany
    private Collection<Group> groups = new ArrayList<>();

    /**
     * Empty constructor for User.
     */
    public User() {
        // Empty constructor for user.
    }

    /**
     * Constructs a User with first, last name, email, username, encrypted
     * of given password with no permission and is enabled.
     */
    public User(String firstName, String lastName, String email,
                String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.setPassword(password);
        this.enabled = true;
        this.token = "";
        this.tokenExpiration = 0;
        this.friends = new FriendList(this);
    }

    /**
     * Generate a random secure string of given length.
     * @param len Length of string to generate.
     * @return Randomly generated String with length of given len.
     */
    private static String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }

    /**
     * Check if given login info is valid.
     * @param email Email of this user.
     * @param token Token of this user.
     * @param repo Repository that this user is stored in.
     * @return true if the login information provided is valid.
     */
    public static boolean validLogin(String email, String token, UserService repo) {
        User user = repo.findByEmail(email);
        if (user == null || !user.isEnabled()) {
            return false;
        }
        return user.validToken(token);
    }

    /**
     * Add given Recommendation to this user.
     * @param r Recommendation to add.
     */
    public void addRecommendation(Recommendation r) {
        this.recommendations.add(r);
    }

    /**
     * Delete the given Recommendation.
     * @param r Recommendation to remove.
     */
    public void deleteRecommendation(Recommendation r) {
        if (this.recommendations.contains(r)) {
            this.recommendations.remove(r);
        }
    }

    /**
     * Given password in plain text and saves it encrypted.
     *
     * @param password user password in plain text.
     */
    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * Set this password to a random password of length 12.
     * @return Randomly generated password.
     */
    public String randomPassword() {
        String randomStr = randomString(12);
        this.setTokenExpired();
        this.setPassword(randomStr);
        return randomStr;
    }

    /**
     * Check if password is correct.
     * @param plainPassword Password in paintext to check.
     * @return True if password matches that is stored in database.
     */
    public boolean checkPassword(String plainPassword) {
        return BCrypt.checkpw(plainPassword, this.password);
    }

    /**
     * Get the current token if the password is valid. Token will have at least
     * 10 minutes before it expires.
     *
     * @param plainPassword Plain text password of this user.
     * @return token to access the user.
     * @throws IllegalAccessException If the password is wrong or user is disabled.
     */
    public String getToken(String plainPassword) throws IllegalAccessException {
        if (!this.isEnabled()) {
            throw new IllegalAccessException("User is disabled");
        }
        if (this.checkPassword(plainPassword)) {
            this.updateTokenExpiration(600000);
            return this.token;
        }
        throw new IllegalAccessException("Do not have permission to access token");
    }

    /**
     * Update the expiration of the current token or create a new one if expired.
     *
     * @param time how long until the current token expires.
     */
    private void updateTokenExpiration(long time) {
        if (this.isTokenExpired()) {
            this.token = UUID.randomUUID().toString();
        }
        this.tokenExpiration = new Date().getTime() + time;
    }

    /**
     * Check if current token is expired.
     *
     * @return true if current token is expired.
     */
    private boolean isTokenExpired() {
        return new Date().after(new Date(this.tokenExpiration));
    }

    /**
     * Set the current token as expired.
     */
    public void setTokenExpired() {
        if (!isTokenExpired()) {
            this.tokenExpiration = 0;
        }
    }

    /**
     * Check if the given token is valid. Make sure it has at least 10 minutes
     * before expiring.
     *
     * @param token String to check if it is a valid token.
     * @return true if given token is right and not expired.
     */
    public boolean validToken(String token) {
        if (!this.isTokenExpired() && this.token.equals(token)) {
            this.updateTokenExpiration(600000);
            return true;
        }
        return false;
    }
}

/**
 * Seralizes User to JSON
 */
@SuppressWarnings("serial")
class UserSeralizer extends StdSerializer<User> {

    /**
     * Default Constructor.
     */
    public UserSeralizer() {
        this(null);
    }

    /**
     * Pass to super constructor.
     * @param t type of User class.
     */
    protected UserSeralizer(Class<User> t) {
        super(t);
    }

    /**
     * Serializes given User with given generator.
     * @param user User object to serialize.
     * @param jsonGenerator Generator that creates the JSON.
     * @param serializerProvider Not used.
     * @throws IOException exception when JSON can't be generated.
     */
    @Override
    public void serialize(User user, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", user.getId());
        jsonGenerator.writeStringField("first_name", user.getFirstName());
        jsonGenerator.writeStringField("last_name", user.getLastName());
        jsonGenerator.writeStringField("email", user.getEmail());
        jsonGenerator.writeStringField("username", user.getUsername());
        jsonGenerator.writeBooleanField("enabled", user.isEnabled());
        jsonGenerator.writeArrayFieldStart("roles");
        for (Role role : user.getRoles()) {
            jsonGenerator.writeObject(role);
        }
        jsonGenerator.writeEndArray();

        jsonGenerator.writeArrayFieldStart("reviews");
        for (Review review : user.getReviews()) {
            jsonGenerator.writeNumber(review.getId());
        }
        jsonGenerator.writeEndArray();

        jsonGenerator.writeFieldName("friends");
        jsonGenerator.writeObject(user.getFriends());

        jsonGenerator.writeArrayFieldStart("groups");
        for (Group group : user.getGroups()) {
            jsonGenerator.writeNumber(group.getId());
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}
