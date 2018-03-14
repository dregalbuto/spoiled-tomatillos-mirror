package edu.northeastern.cs4500.spoiledTomatillos.data.reviews;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;

/**
 * Class for a review written by a user
 */

@Data
@Entity(name="reviews")
public class Review {
    @Id
    private int id;
    private String text;
    private int rating;

    @ManyToOne
    @JoinTable


    public Review() {

    }
}
