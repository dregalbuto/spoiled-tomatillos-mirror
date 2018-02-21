package edu.northeastern.cs4500.spoiledTomatillos.data.movies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Controller that performs actions on movies.
 */
@Controller
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieCachedRepository movieCachedRepository;

    /**
     * Takes in id and returns a movie with matching id.
     * @param id id of the movie to get.
     * @return Movie with all its info.
     */
    @CrossOrigin
    @RequestMapping("/info")
    @ResponseBody
    public Movie info(@RequestParam(required=true) String id) {
        return this.movieCachedRepository.getMovie(id);
    }

    /**
     * Takes in a s and find title with matching parts.
     * @param s title search.
     * @return A list of id of movies that is related to s.
     */
    @CrossOrigin
    @RequestMapping("/search")
    @ResponseBody
    public List<String> search(@RequestParam(required=true) String s) {
        return this.movieCachedRepository.searchMovie(new MovieSearchQuery(s));
    }
}
