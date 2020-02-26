package cinema.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cinema.dto.PersonDto;
import cinema.persistence.entity.Movie;
import cinema.persistence.entity.Person;
import cinema.persistence.repository.MovieRepository;
import cinema.persistence.repository.PersonRepository;
import cinema.service.IPersonService;

@Service
@Transactional
public class PersonService implements IPersonService {

	@Autowired
	PersonRepository personRepository;
	@Autowired
	MovieRepository movieRepository;
	@Autowired
	ModelMapper mapper;
	
	@Override
	public List<Person> getAllPersons() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<PersonDto> getMovieDirector(Integer idMovie) {
		return movieRepository.findById(idMovie)
			.map(me -> {
				Person d = me.getDirector();
				return Objects.isNull(d) ? null : mapper.map(d, PersonDto.class); 
			});
	}

	@Override
	public List<PersonDto> getMovieActors(Integer idMovie) {
		return movieRepository.findById(idMovie)
				.map(me -> me.getActors().stream()
						.map(a -> mapper.map(a, PersonDto.class))
						.collect(Collectors.toList()))
				.orElse(List.of());
	}
	
}
