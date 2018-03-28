package edu.northeastern.cs4500.spoiledtomatillos.user.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.hibernate.annotations.GenericGenerator;

@Data
@EqualsAndHashCode(exclude={"user"})
@Entity
public class FriendList {

    @Id
    //@Column(name="id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@GeneratedValue(generator = "foreigngen")
    //@GenericGenerator(strategy = "foreign", name="foreigngen",
    //        parameters = @org.hibernate.annotations.Parameter(name = "property", value="users"))
    private int id;

    //@MapsId
    //@JoinColumn(name = "id")
    @OneToOne//(mappedBy = "friends")
    @JsonBackReference
    private User user;

    //@ManyToMany(cascade = CascadeType.ALL)
    //@JoinColumn(name="id")
    @ElementCollection
    private Collection<Integer> request;

    //@ManyToMany(cascade = CascadeType.ALL)
    //@JoinColumn(name="id")
    @ElementCollection
    private Collection<Integer> friends;

    public FriendList() {
        // Empty constructor
    }

    public FriendList(User user) {
        this.user = user;
        this.request = new ArrayList<>();
        this.friends = new ArrayList<>();
    }

    public boolean addRequest(User user) {
        if (user == null) {
            return false;
        }
        if (!user.isEnabled() || !this.user.isEnabled()) {
            return false;
        }
        for (int id : friends) {
            if (id == user.getId()) {
                return false;
            }
        }
        for (int id : request) {
            if (id == user.getId()) {
                return false;
            }
        }
        this.request.add(user.getId());
        return true;
    }

    public boolean acceptRequest(User user) {
        if (user == null) {
            return false;
        }
        if (!user.isEnabled() || !this.user.isEnabled()) {
            return false;
        }
        for (int id : friends) {
            if (id == user.getId()) {
                return false;
            }
        }
        boolean found = false;
        for (int id : request) {
            if (id == user.getId()) {
                found = true;
            }
        }
        if (!found) {
            return false;
        }
        this.friends.add(user.getId());
        user.getFriends().addRequest(this.user);
        user.getFriends().acceptRequest(this.user);
        this.request.remove(user.getId());
        return true;
    }

    public boolean rejectRequest(User user) {
        if (user == null) {
            return false;
        }
        if (!this.user.isEnabled()) {
            return false;
        }
        boolean found = false;
        for (int id : request) {
            if (id == user.getId()) {
                found = true;
            }
        }
        if (!found) {
            return false;
        }
        this.request.remove(user.getId());
        return true;
    }

    public boolean removeFriend(User user) {
        if (!this.user.isEnabled()) {
            return false;
        }
        boolean found = false;
        for (int id : friends) {
            if (id == user.getId()) {
                found = true;
            }
        }
        if (!found) {
            return false;
        }
        this.friends.remove(user.getId());
        user.getFriends().getFriends().remove(this.user.getId());
        return true;
    }
}
