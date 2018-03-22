package edu.northeastern.cs4500.spoiledtomatillos.groups;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.*;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import edu.northeastern.cs4500.spoiledtomatillos.movies.Movie;
import edu.northeastern.cs4500.spoiledtomatillos.reviews.Review;
import edu.northeastern.cs4500.spoiledtomatillos.user.model.User;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.mapping.FetchProfile;

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
          CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
  @JoinColumn//(name = "id")
  private User creator;

  @JsonManagedReference
  @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE,
          CascadeType.PERSIST, CascadeType.REFRESH}, fetch = FetchType.LAZY)
  @JoinColumn//(name = "id")
  private Collection<User> users;

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

  public boolean removeUser(User user) {
    if (user == null) {
      return false;
    }
    return this.getUsers().remove(user);
  }

  public boolean contains(User user) {
    if (user == null) {
      return false;
    }
    return this.getCreator().getId() == user.getId() || this.users.contains(user);
  }
}
class GroupSeralizer extends StdSerializer<Group> {

  public GroupSeralizer() {
    this(null);
  }

  protected GroupSeralizer(Class<Group> t) {
    super(t);
  }

  @Override
  public void serialize(Group group, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
    jsonGenerator.writeStartObject();
    jsonGenerator.writeNumberField("id", group.getId());
    jsonGenerator.writeStringField("name", group.getName());
    jsonGenerator.writeObjectFieldStart("creator");
    jsonGenerator.writeNumberField("id",group.getCreator().getId());
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
    jsonGenerator.writeStringField("id",group.getTopic().getId());
    jsonGenerator.writeEndObject();
    jsonGenerator.writeArrayFieldStart("reviews");
    for (Review r : group.getReviews()) {
      jsonGenerator.writeNumber(r.getId());
    }
    jsonGenerator.writeEndArray();
    jsonGenerator.writeBooleanField("blacklist",group.isBlackList());
    jsonGenerator.writeEndObject();
  }
}
