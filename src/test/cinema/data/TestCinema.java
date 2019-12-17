package test.cinema.data;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cinema.data.Movie;
import cinema.data.Person;

class TestCinema {

	private List<Movie> movies;
	private List<Person> persons;
	
	@BeforeEach
	void initData() {
		persons = new ArrayList<>();
		Collections.addAll(persons,		
				new Person("Joaquin Phoenix", LocalDate.of(1974, 10, 28)), 	// 0
				new Person("Gerard Darmond", LocalDate.of(1948, 2, 29)),	// 1
				new Person("Todd Phillips"),								// 2
				new Person("Clint Eastwood", LocalDate.of(1930, 5, 31)),	// 3
				new Person("Bradley Cooper", LocalDate.of(1975, 1, 5))		// 4
				);
		var clint = persons.get(3);
		var todd = persons.get(2);
		movies = new ArrayList<>();
		Collections.addAll(movies, 
				new Movie("Joker", 2019, 165, todd),			// 0
				new Movie("Parasite", 2019, 132),				// 1
				new Movie("Interstellar", 2014),				// 2
				new Movie("Gran Torino", 2008, 116, clint),		// 3
				new Movie("Impitoyable", 1992, clint),			// 4
				new Movie("American Sniper", 2014, 133, clint),	// 5
				new Movie("Very Bad Trip", 2009, 100, todd)		// 6
				);
	}
	
	@Test
	void testModifiableList() {
		System.out.println(persons);
		System.out.println(persons.getClass());
		persons.add(new Person("Bradley Cooper", LocalDate.of(1975, 1, 5)));
		System.out.println(persons);
	}

	@Test
	void displayMovies() {
		for (int i=0; i < movies.size(); i++) {
			var movie = movies.get(i);
			System.out.println(" - " + movie + " directed by " + movie.getDirector());
		}
	}
}
