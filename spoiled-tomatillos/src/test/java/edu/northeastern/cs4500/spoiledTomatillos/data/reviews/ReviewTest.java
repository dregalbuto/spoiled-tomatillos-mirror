package edu.northeastern.cs4500.spoiledTomatillos.data.reviews;

import org.junit.Test;

import static org.junit.Assert.*;

public class ReviewTest {

    Review testreview = new Review();

    @Test
    public void testReviewId() {
        testreview.setId(1234);
        assertEquals(1234, testreview.getId());
    }

    @Test
    public void testReviewText() {
        testreview.setText("This is a review.");
        assertEquals("This is a review.", testreview.getText());
    }

    @Test
    public void testReviewRating() {
        testreview.setRating(5);
        assertEquals(5, testreview.getRating());
    }



}