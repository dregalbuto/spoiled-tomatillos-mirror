package edu.northeastern.cs4500.spoiledtomatillos.groups;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import edu.northeastern.cs4500.spoiledtomatillos.movies.Movie;
import edu.northeastern.cs4500.spoiledtomatillos.reviews.Review;
import edu.northeastern.cs4500.spoiledtomatillos.user.model.User;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Group object for data of a group
 */
@Data
@Entity
public class Group {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private int id;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn//(name = "id")
  private User creator;

  @JsonManagedReference
  @ManyToMany(cascade = CascadeType.ALL)
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
  @OneToMany(cascade = CascadeType.ALL)
  private Collection<Review> reviews;

  public Group(User creator, Movie topic) {
    this.creator = creator;
    this.users = new ArrayList<>();
    this.isBlackList = true;
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

  public boolean contains(User user) {
    if (user == null) {
      return false;
    }
    return this.getCreator().getId() == user.getId() || this.users.contains(user);
  }
}
