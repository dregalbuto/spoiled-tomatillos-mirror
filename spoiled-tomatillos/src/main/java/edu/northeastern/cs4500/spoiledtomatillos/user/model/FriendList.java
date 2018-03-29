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
	private Collection<Integer> requests;

	//@ManyToMany(cascade = CascadeType.ALL)
	//@JoinColumn(name="id")
	@ElementCollection
	private Collection<Integer> friends;

	public FriendList() {
		// Empty constructor
	}

	public FriendList(User user) {
		this.user = user;
		this.requests = new ArrayList<>();
		this.friends = new ArrayList<>();
	}

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
