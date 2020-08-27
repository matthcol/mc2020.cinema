package cinema.service.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cinema.config.Utils;
import cinema.dto.MovieFull;
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
	ModelMapper modelMapper;
	
	@Override
	public List<MovieLight> getAllMovies() {
		List<Movie> movieEntities = movieRepository.findAll();
		return Utils.mapToList(movieEntities, modelMapper, MovieLight.class);
	}

	@Override
	public Optional<MovieFull> getMovieById(int idMovie) {
		return movieRepository.findById(idMovie)
				.map(me -> modelMapper.map(me, MovieFull.class));
	}

	@Override
	public Set<MovieLight> getMovieByPartialTitle(String partialTitle) {
		return toMovieLightSetAlbateticYearOrder(
				movieRepository.findByTitleContainingIgnoreCase(partialTitle));
	}

	@Override
	public Set<MovieLight> getMoviesByDirector(int idDirector) {
		return personRepository.findById(idDirector)
				.map(movieRepository::findByDirector)
				.map(this::toMovieLightSetChronologicOrder)
				.orElseGet(Collections::emptySet);
	}

	@Override
	public Set<MovieLight> getMoviesByActor(int idActor) {
		return personRepository.findById(idActor)
			.map(movieRepository::findByActors)
			.map(this::toMovieLightSetChronologicOrder)
			.orElseGet(Collections::emptySet);
	}

	@Override
	public MovieFull addMovie(MovieFull movieDto) {
		// prevent copy association attributes
		Movie movieEntity = modelMapper
				.typeMap(MovieFull.class, Movie.class)
				.map(movieDto);
		movieRepository.save(movieEntity);
		return modelMapper.map(movieEntity, MovieFull.class);
	}

	@Override
	public Optional<MovieFull> modifyMovie(MovieFull movieDto) {
		return movieRepository.findById(movieDto.getIdMovie())
			.map(me -> { 
				modelMapper
					.typeMap(MovieFull.class, Movie.class)
					.map(movieDto, me);
				return modelMapper.map(me, MovieFull.class);});
	}

	@Override
	public Optional<MovieFull> addActor(int idActor, int idMovie) {
		return movieRepository.findById(idMovie)
			.flatMap(me -> personRepository.findById(idActor)
					.map(ae -> { 
							me.getActors().add(ae);
							return modelMapper.map(me, MovieFull.class);
					}));
	}

	@Override
	public Optional<MovieFull> setDirector(int idDirector, int idMovie) {
		return movieRepository.findById(idMovie)
				.flatMap(me -> personRepository.findById(idDirector)
						.map(de -> { 
								me.setDirector(de);
								return modelMapper.map(me, MovieFull.class);
						}));
	}

	@Override
	public Optional<MovieFull> deleteMovie(int idMovie) {
		return movieRepository.findById(idMovie)
				.map(me -> {
					MovieFull movieDto = modelMapper.map(me, MovieFull.class);
					movieRepository.deleteById(idMovie);
					return movieDto;
				});
	}
	
	private static final Comparator<MovieLight> CHRONOLOGIC_ORDER = 
			Comparator.comparing(MovieLight::getYear)
			.thenComparing(MovieLight::getIdMovie);

	private static final Comparator<MovieLight> ALPHABETIC_YEAR_ORDER	= 
			Comparator.comparing(MovieLight::getTitle)
			.thenComparing(MovieLight::getYear)
			.thenComparing(MovieLight::getIdMovie);
	
	private Set<MovieLight> toMovieLightSet(
			Collection<?> coll, Comparator<? super MovieLight> cmp) 
	{
		return Utils.mapToCollection(
			coll,
			modelMapper, MovieLight.class, ()->new TreeSet<>(cmp));
	}
	
	private Set<MovieLight> toMovieLightSetChronologicOrder(
			Collection<?> coll) 
	{
		return toMovieLightSet(coll, CHRONOLOGIC_ORDER);
	}
	
	private Set<MovieLight> toMovieLightSetAlbateticYearOrder(
			Collection<?> coll) 
	{
		return toMovieLightSet(coll, ALPHABETIC_YEAR_ORDER);
	}

}