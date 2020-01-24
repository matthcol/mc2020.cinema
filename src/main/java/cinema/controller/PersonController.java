package cinema.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import cinema.persistence.entity.Person;
public class PersonController {
	
	@PostMapping
	Person addPerson(Person person) {
		// TODO
		return person;
	}

	@GetMapping
	List<Person> getAllPersons() {
		// TODO
		return null;
	}
}
