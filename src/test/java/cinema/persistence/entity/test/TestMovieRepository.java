package cinema.persistence.entity.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import cinema.persistence.entity.Movie;
import cinema.persistence.entity.Person;
import cinema.persistence.repository.MovieRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class TestMovieRepository {

	@Autowired
	MovieRepository repoMovie;
	
	@Autowired
	EntityManager entityManager;
	
	@Test
	void testSave() {
		Movie movie = new Movie("Joker", 2019);
		repoMovie.save(movie);
		var id = movie.getIdMovie();
		System.out.println("Id new movie: " + id);
		assertNotNull(id);
	}
	
	
	@Test
	void testSaveWithDirector() {
		// Given
		Person person = new Person("Todd Phillips", LocalDate.of(1970, 12, 20));
		Movie movie = new Movie("Joker", 2019, 165, person);
		entityManager.persist(person); // already in cache
		// when 
		repoMovie.save(movie);
		// then 
		System.out.println(movie);
		System.out.println(person);
	}
	
	@Test
	void testSelectAll() {
		// given 
		List<Movie> data = List.of(
				new Movie("Joker", 2019),
				new Movie("Parasite", 2019, 132),				
				new Movie("Interstellar", 2014),				
				new Movie("Gran Torino", 2008, 116)
				);
		data.forEach(entityManager::persist);
		// when
		var dataRead = repoMovie.findAll();
		// then
		dataRead.forEach(System.out::println);
		assertEquals(data.size(), dataRead.size());
		assertTrue(dataRead.stream()
				.map(Movie::getTitle)
				.allMatch(tr -> data.stream()
							.map(Movie::getTitle)
							.anyMatch(th -> th.equals(tr))
						));
	}
	
	@Test
	void testFindById() {
		// given
		Movie movie = new Movie("Joker", 2019);
		entityManager.persist(movie);
		var id = movie.getIdMovie();
		// when
		var movieReadOpt = repoMovie.findById(id);
		System.out.println(movieReadOpt);
		assertAll(
				()->assertTrue(movieReadOpt.isPresent()),
				()->assertEquals(movie.getTitle(), movieReadOpt.get().getTitle()));
		
	}
	
	@Test
	void testFindByTitle() {
		// given 
		String title = "Le Roi Lion";
		List<Movie> data = List.of(
				new Movie("Joker", 2019),
				new Movie(title, 2019),				
				new Movie(title, 1994)
				);
		data.forEach(entityManager::persist);
		// when
		var dataRead = repoMovie.findByTitle(title);
		System.out.println(dataRead);
	}
	
	@Test
	void testFindByYearBetween() {
		// given 
		int year1 = 1995;
		int year2 = 2015;
		List<Movie> data = List.of(
				new Movie("Joker", 2019),
				new Movie("Le Roi Lion", 1994),				
				new Movie("Seven", year1),
				new Movie("Mad Max: Fury Road", year2),
				new Movie("Gran Torino", 2008)
				);
		data.forEach(entityManager::persist);
		// when
		var dataRead = repoMovie.findByYearBetween(year1, year2);
		// then
		System.out.println(dataRead);
		assertAll(
				() -> assertEquals(3, dataRead.size()),
				() -> assertTrue(dataRead.stream()
						.mapToInt(Movie::getYear)
						.allMatch(y -> (y >= year1) && (y <= year2))));
		
	}
	
	@Test
	void testFindByTitleAndYear() {
		// given 
		String title = "Le Roi Lion";
		int year = 1994;
		List<Movie> data = List.of(
				new Movie("Forest Gump", year),
				new Movie(title, year),				
				new Movie(title, 2019));
		data.forEach(entityManager::persist);
		// when
		var dataRead = repoMovie.findByTitleAndYear(title, year);
		// then
		System.out.println(dataRead);
		// TODO : asserts
	}	
	
	
}
