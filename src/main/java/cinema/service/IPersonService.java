package cinema.service;

import java.util.List;
import java.util.Optional;

import cinema.dto.PersonDto;
import cinema.persistence.entity.Person;

public interface IPersonService {

	List<Person> getAllPersons();

	Optional<PersonDto> getMovieDirector(Integer idMovie);

	List<PersonDto> getMovieActors(Integer idMovie);

}
