package cinema.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cinema.dto.PersonDto;
import cinema.service.IPersonService;

@RestController
@RequestMapping("/api/person")
public class PersonController {

	@Autowired
	IPersonService personService;
	
	@GetMapping("/directorByMovie/{idM}")
	Optional<PersonDto> getMovieDirector(@PathVariable("idM") Integer idMovie){
		return personService.getMovieDirector(idMovie);
	}
	
	@GetMapping("/actorsByMovie/{idM}")
	List<PersonDto> getMovieActors(@PathVariable("idM") Integer idMovie){
		return personService.getMovieActors(idMovie);
	}
	
	
//	@PostMapping
//	PersonDto addPerson(PersonDto person) {
//		// TODO
//		return person;
//	}
//
//	@GetMapping
//	List<PersonDto> getAllPersons() {
//		return null; //personService.getAllPersons();
//	}
}
