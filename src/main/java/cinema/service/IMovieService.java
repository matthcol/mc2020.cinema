package cinema.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import cinema.dto.MovieFull;
import cinema.dto.MovieLight;

public interface IMovieService {
	List<MovieLight> getAllMovies();
	Optional<MovieFull> getMovieById(int idMovie);
	Set<MovieLight> getMovieByPartialTitle(String partialTitle);
	Set<MovieLight> getMoviesByDirector(int idDirector);
	Set<MovieLight> getMoviesByActor(int idActor);
	MovieFull addMovie(MovieFull movie);
	Optional<MovieFull> modifyMovie(MovieFull movie);
	Optional<MovieFull> addActor(int idActor, int idMovie);
}
