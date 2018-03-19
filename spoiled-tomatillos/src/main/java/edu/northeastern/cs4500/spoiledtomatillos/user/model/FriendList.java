package edu.northeastern.cs4500.spoiledtomatillos.user.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class FriendList {

    @Id
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<User> request;

    @OneToMany(cascade = CascadeType.ALL)
    private Collection<User> frends;

    public FriendList() {
        this.request = new ArrayList<>();
        this.frends = new ArrayList<>();
    }

    public boolean addRequest(User user) {
        if (!user.isEnabled() || !this.user.isEnabled()) {
            return false;
        }
        for (User u : frends) {
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
        if (!user.isEnabled() || !this.user.isEnabled()) {
            return false;
        }
        for (User u : frends) {
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
        this.frends.add(user);
        this.request.remove(user);
        return true;
    }

    public boolean rejectRequest(User user) {
        if (!this.user.isEnabled()) {
            return false;
        }
        boolean found = false;
        for (User u : request) {
            if (u.getId() == user.getId()) {
                found = true;
            }
        }
        if (found) {
            this.request.remove(user);
            return true;
        }
        return false;
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
        if (found) {
            this.request.remove(user);
            return true;
        }
        return false;
    }
}
