package edu.northeastern.cs4500.spoiledtomatillos.recommendations;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import edu.northeastern.cs4500.spoiledtomatillos.movies.Movie;
import edu.northeastern.cs4500.spoiledtomatillos.user.model.User;
import lombok.Data;

@Data
@Entity(name="recommendations")
public class Recommendation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@OneToOne
	@JoinColumn
	// The user who made the recommendation 
	// Default is null because rec. might not have a source
	private User recommendationGiver = null;
	
	// Message from the recommendationGiver
	// Default is null because rec. might not have a message
	private String message = null;
	
	@OneToOne
	@JoinColumn
	// The movie being recommended
	private Movie movie;
	
	public Recommendation() {
		// empty constructor
	}
	
	public Recommendation(User source, Movie movie, String message) {
		this.recommendationGiver = source;
		this.movie = movie;
		this.message = message;
	}
	
	public Recommendation(Movie movie) {
		this.movie = movie;
	}
}
