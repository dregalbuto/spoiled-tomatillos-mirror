package edu.northeastern.cs4500.spoiledtomatillos.user.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A list of friends for a user. Manages requests, maintains friends.
 */
@Data
@EqualsAndHashCode(exclude = {"user"})
@Entity
public class FriendList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToOne
    @JsonBackReference
    private User user;

    @ElementCollection
    private Collection<Integer> requests;

    @ElementCollection
    private Collection<Integer> friends;

    /**
     * Empty constructor.
     */
    public FriendList() {
        // Empty constructor
    }

    /**
     * Create a FriendList for a user.
     * @param user
     */
    public FriendList(User user) {
        this.user = user;
        this.requests = new ArrayList<>();
        this.friends = new ArrayList<>();
    }

    /**
     * Add friend request from a given User.
     * @param friend Request to add.
     * @return true if the request successful. Will fail if already friends.
     */
    public boolean addRequest(User friend) {
        if (friend == null) {
            return false;
        }
        if (!friend.isEnabled() || !this.user.isEnabled()) {
            return false;
        }
        if (friends.contains(friend.getId())) {
            return false;
        }
        if (requests.contains(friend.getId())) {
            return false;
        }
        this.requests.add(friend.getId());
        return true;
    }

    /**
     * Accept friend request from given user.
     * @param friend User's friend request to accept.
     * @return true if successful and becomes friends. Will fail if the given friend did not have a request to be
     * friend.
     */
    public boolean acceptRequest(User friend) {
        if (friend == null) {
            return false;
        }
        if (!friend.isEnabled() || !this.user.isEnabled()) {
            return false;
        }
        if (friends.contains(friend.getId())) {
            return false;
        }
        if (!requests.contains(friend.getId())) {
            return false;
        }
        this.friends.add(friend.getId());
        friend.getFriends().addRequest(this.user);
        friend.getFriends().acceptRequest(this.user);
        this.requests.remove(friend.getId());
        return true;
    }

    /**
     * Reject the friend request.
     * @param friend User's friend request to reject.
     * @return true if successfully removed friend request.
     */
    public boolean rejectRequest(User friend) {
        if (friend == null) {
            return false;
        }
        if (!friend.isEnabled() || !this.user.isEnabled()) {
            return false;
        }
        if (!requests.contains(friend.getId())) {
            return false;
        }
        this.requests.remove(friend.getId());
        return true;
    }

    /**
     * Remove friend from Friend list.
     * @param friend User to remove from friend list.
     * @return true if successfully become no longer friends.
     */
    public boolean removeFriend(User friend) {
        if (!friend.isEnabled() || !this.user.isEnabled()) {
            return false;
        }
        if (!friends.contains(friend.getId())) {
            return false;
        }
        this.friends.remove(friend.getId());
        friend.getFriends().getFriends().remove(this.user.getId());
        return true;
    }
}
