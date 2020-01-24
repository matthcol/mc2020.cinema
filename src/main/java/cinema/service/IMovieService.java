package cinema.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;


import cinema.persistence.entity.Movie;

public interface IMovieService {
	List<Movie> getAllMovies();
	Optional<Movie> getMovieById(int idMovie);
	Set<Movie> getMovieByPartialTitle(String partialTitle);
	Set<Movie> getMoviesByDirector(int idDirector);
	Set<Movie> getMoviesByActor(int idActor);
}
