package edu.northeastern.cs4500.spoiledtomatillos.user.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

import lombok.Data;

@Data
@Entity
public class FriendList {

    @Id
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<User> request;

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<User> friends;

    public FriendList() {
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
        for (User u : friends) {
            if (u.getId() == user.getId()) {
                return false;
            }
        }
        for (User u : request) {
            if (u.getId() == user.getId()) {
                return false;
            }
        }
        this.request.add(user);
        return true;
    }

    public boolean acceptRequest(User user) {
        if (user == null) {
            return false;
        }
        if (!user.isEnabled() || !this.user.isEnabled()) {
            return false;
        }
        for (User u : friends) {
            if (u.getId() == user.getId()) {
                return false;
            }
        }
        boolean found = false;
        for (User u : request) {
            if (u.getId() == user.getId()) {
                found = true;
            }
        }
        if (!found) {
            return false;
        }
        this.friends.add(user);
        user.getFriends().addRequest(this.user);
        user.getFriends().acceptRequest(this.user);
        this.request.remove(user);
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
        for (User u : request) {
            if (u.getId() == user.getId()) {
                found = true;
            }
        }
        if (!found) {
            return false;
        }
        this.request.remove(user);
        return true;
    }

    public boolean removeFriend(User user) {
        if (!this.user.isEnabled()) {
            return false;
        }
        boolean found = false;
        for (User u : request) {
            if (u.getId() == user.getId()) {
                found = true;
            }
        }
        if (!found) {
            return false;
        }
        this.request.remove(user);
        user.getFriends().removeFriend(this.user);
        return true;
    }
}
