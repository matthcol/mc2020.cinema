package cinema.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cinema.dto.MovieLight;
import cinema.persistence.entity.Movie;
import cinema.persistence.repository.MovieRepository;
import cinema.persistence.repository.PersonRepository;
import cinema.service.IMovieService;

@Service
@Transactional
public class MovieService implements IMovieService {
	
	@Autowired
	MovieRepository movieRepository;
	
	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	ModelMapper mapper;

	@Override
	public List<MovieLight> getAllMovies() {
		List<Movie> movieEntities = movieRepository.findAll();
		return movieEntities.stream()
			.map(me->mapper.map(me, MovieLight.class))
			.collect(Collectors.toList());
	}

	@Override
	public Optional<Movie> getMovieById(int idMovie) {
		return movieRepository.findById(idMovie);
	}

	@Override
	public Set<Movie> getMovieByPartialTitle(String partialTitle) {
		return movieRepository.findByTitleContainingIgnoreCase(partialTitle);
	}

	@Override
	public Set<Movie> getMoviesByDirector(int idDirector) {
		var directorOpt = personRepository.findById(idDirector);
		return directorOpt.map(d->movieRepository.findByDirector(d))
				.orElseGet(()->Collections.emptySet());
	}

	@Override
	public Set<Movie> getMoviesByActor(int idActor) {
		return movieRepository.findByActorsIdPerson(idActor);
	}

}
