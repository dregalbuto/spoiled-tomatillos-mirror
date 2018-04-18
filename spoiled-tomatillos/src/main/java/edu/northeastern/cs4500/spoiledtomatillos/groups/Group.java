package edu.northeastern.cs4500.spoiledtomatillos.groups;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import edu.northeastern.cs4500.spoiledtomatillos.movies.Movie;
import edu.northeastern.cs4500.spoiledtomatillos.reviews.Review;
import edu.northeastern.cs4500.spoiledtomatillos.user.model.User;
import lombok.Data;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Group object for data of a group
 */
@Data
@Entity(name = "usergroup")
@JsonSerialize(using = GroupSeralizer.class)
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})//, fetch = FetchType.LAZY)
    @JoinColumn//(name = "id")
    private User creator;

    @JsonManagedReference
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})//, fetch = FetchType.LAZY)
    @JoinColumn//(name = "id")
    private Collection<User> users = new ArrayList<>();

    private boolean isBlackList = true;

    /**
     * This is a blacklist of isBlackList == true, whitelist otherwise.
     */
    @ElementCollection
    private Collection<Integer> idList;

    @ManyToOne
    @JoinColumn//(name = "id")
    private Movie topic;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group")
    private Collection<Review> reviews;


    public Group() {
        //Default constructor
    }

    public Group(User creator, Movie topic, String name, boolean isBlackList) {
        this.creator = creator;
        this.name = name;
        this.users = new ArrayList<>();
        this.isBlackList = isBlackList;
        this.idList = new ArrayList<>();
        this.topic = topic;
        this.reviews = new ArrayList<>();
    }

    /**
     * Add given user to group and return if successful. Will fail if already in group, or is in blacklist, or on
     * whitelist.
     * @param user User to add to group.
     * @return true if the user is successfully added.
     */
    public boolean addUser(User user) {
        if (user == null) {
            return false;
        }
        if (this.contains(user)) {
            return false;
        }
        if ((this.isBlackList() && !this.idList.contains(user.getId()))
                || (!this.isBlackList() && this.idList.contains(user.getId()))) {
            return this.users.add(user);
        }
        return false;
    }

    /**
     * Remove given user from group.
     * @param user user to remove.
     * @return true if successfully removed.
     */
    public boolean removeUser(User user) {
        if (user == null) {
            return false;
        }
        return this.getUsers().remove(user);
    }

    /**
     * Check if group contains user.
     * @param user User to check if is in group.
     * @return true if is in group or created group.
     */
    public boolean contains(User user) {
        if (user == null) {
            return false;
        }
        return this.getCreator().getId() == user.getId() || this.users.contains(user);
    }
}

/**
 * Serializes group to json.
 */
class GroupSeralizer extends StdSerializer<Group> {

    private static final long serialVersionUID = 1L;

    /**
     * Default constructor
     */
    public GroupSeralizer() {
        this(null);
    }

    /**
     * Pass to super constructor.
     * @param t type of Group class.
     */
    protected GroupSeralizer(Class<Group> t) {
        super(t);
    }

    /**
     * Serializes given Group with given generator.
     * @param group Group object to serialize.
     * @param jsonGenerator Generator that creates the JSON.
     * @param serializerProvider Not used.
     * @throws IOException exception when JSON can't be generated.
     */
    @Override
    public void serialize(Group group, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", group.getId());
        jsonGenerator.writeStringField("name", group.getName());
        jsonGenerator.writeObjectFieldStart("creator");
        jsonGenerator.writeNumberField("id", group.getCreator().getId());
        jsonGenerator.writeEndObject();
        jsonGenerator.writeArrayFieldStart("users");
        for (User u : group.getUsers()) {
            jsonGenerator.writeNumber(u.getId());
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeArrayFieldStart("idList");
        for (Integer i : group.getIdList()) {
            jsonGenerator.writeNumber(i);
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeObjectFieldStart("topic");
        jsonGenerator.writeStringField("id", group.getTopic().getId());
        jsonGenerator.writeEndObject();
        jsonGenerator.writeArrayFieldStart("reviews");
        for (Review r : group.getReviews()) {
            jsonGenerator.writeNumber(r.getId());
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeBooleanField("blacklist", group.isBlackList());
        jsonGenerator.writeEndObject();
    }
}
