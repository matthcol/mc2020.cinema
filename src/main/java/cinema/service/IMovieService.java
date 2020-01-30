package cinema.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import cinema.dto.SimpleMovie;
import cinema.persistence.entity.Movie;

public interface IMovieService {
	List<SimpleMovie> getAllMovies();
	Optional<Movie> getMovieById(int idMovie);
	Set<Movie> getMovieByPartialTitle(String partialTitle);
	Set<Movie> getMoviesByDirector(int idDirector);
	Set<Movie> getMoviesByActor(int idActor);
}
