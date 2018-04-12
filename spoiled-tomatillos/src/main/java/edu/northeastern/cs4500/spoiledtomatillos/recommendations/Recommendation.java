package edu.northeastern.cs4500.spoiledtomatillos.recommendations;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import edu.northeastern.cs4500.spoiledtomatillos.JsonStrings;
import edu.northeastern.cs4500.spoiledtomatillos.movies.Movie;
import edu.northeastern.cs4500.spoiledtomatillos.user.model.User;
import lombok.Data;

@Data
@Entity(name="recommendations")
@JsonAutoDetect(fieldVisibility=Visibility.ANY)
@JsonPropertyOrder({"recommendationId", "message"})
@ResponseBody
public class Recommendation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonProperty(JsonStrings.REC_ID)
	private int id;
	
	// The user this recommendation belongs to
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	@JsonProperty("owner")
	private User owner;
	
	// The movie being recommended
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
	@JsonBackReference
	private Movie movie;
	
	// The user who made the recommendation 
	// Default is null because rec. might not have a source
	@JsonProperty("sender")
	private String senderEmail = null;
	
	// Message from the recommendationGiver
	// Default is null because rec. might not have a message
	private String message = null;
	
	public Recommendation() {
		// empty constructor
	}
	
	public Recommendation(User owner, Movie movie, String senderEmail, String message) {
		this.owner = owner;
		this.movie = movie;
		this.senderEmail = senderEmail;
		this.message = message;
	}
	
	public Recommendation(User owner, Movie movie) {
		this.owner = owner;
		this.movie = movie;
	}
	
}
