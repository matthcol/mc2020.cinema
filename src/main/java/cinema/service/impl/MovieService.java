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

import cinema.dto.MovieFull;
import cinema.dto.MovieLight;
import cinema.persistence.entity.Movie;
import cinema.persistence.entity.Person;
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
	public Optional<MovieFull> getMovieById(int idMovie) {
		return movieRepository.findById(idMovie)
				.map(me -> mapper.map(me, MovieFull.class));
	}

	@Override
	public Set<MovieLight> getMovieByPartialTitle(String partialTitle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<MovieLight> getMoviesByDirector(int idDirector) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<MovieLight> getMoviesByActor(int idActor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MovieFull addMovie(MovieFull movieDto) {
		Movie movieEntity = mapper.map(movieDto, Movie.class);
		movieRepository.save(movieEntity);
		mapper.map(movieEntity, movieDto);
		return movieDto;
	}

	@Override
	public Optional<MovieFull> modifyMovie(MovieFull movie) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<MovieFull> addActor(int idActor, int idMovie) {
		return movieRepository.findById(idMovie)
			.flatMap(me -> personRepository.findById(idActor)
					.map(ae -> { 
							me.getActors().add(ae);
							return mapper.map(me, MovieFull.class);
					}));
	}

//	@Override
//	public Set<MovieLight> getMovieByPartialTitle(String partialTitle) {
//		return movieRepository.findByTitleContainingIgnoreCase(partialTitle);
//	}
//
//	@Override
//	public Set<MovieLight> getMoviesByDirector(int idDirector) {
//		var directorOpt = personRepository.findById(idDirector);
//		return directorOpt.map(d->movieRepository.findByDirector(d))
//				.orElseGet(()->Collections.emptySet());
//	}
//
//	@Override
//	public Set<MovieLight> getMoviesByActor(int idActor) {
//		return movieRepository.findByActorsIdPerson(idActor);
//	}
//
//	@Override
//	public MovieFull addMovie(MovieFull movie) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Optional<MovieFull> modifyMovie(MovieFull movie) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
