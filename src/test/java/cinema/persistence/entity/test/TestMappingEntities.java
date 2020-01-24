package cinema.persistence.entity.test;
/**
 * this is not a unit test case
 */
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import cinema.persistence.entity.Movie;
import cinema.persistence.entity.Person;
import cinema.persistence.repository.MovieRepository;
import cinema.persistence.repository.PersonRepository;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class TestMappingEntities {
	@Autowired
	PersonRepository repoPersons;
	@Autowired
	MovieRepository repoMovies;
	
	@Rollback(false)
	@Test
	void scenario01SaveData() {
		var joaq =	new Person("Joaquin Phoenix", LocalDate.of(1974, 10, 28));
		var gege =	new Person("Gerard Darmond", LocalDate.of(1948, 2, 29));
		var todd =	new Person("Todd Phillips", LocalDate.of(1970, 12, 20));
		var clint =	new Person("Clint Eastwood", LocalDate.of(1930, 5, 31));
		var brad = 	new Person("Bradley Cooper", LocalDate.of(1975, 1, 5));
		var gene =	new Person("Gene Hackman", LocalDate.of(1930, 1, 30));
		var morgan = new Person("Morgan Freeman", LocalDate.of(1937, 6, 1));
		var	persons = List.of(joaq, gege, todd, clint, brad, gene, morgan);
		persons.forEach(repoPersons::save);
		var joker =	new Movie("Joker", 2019, 165, todd);
		var parasite =	new Movie("Parasite", 2019, 132);
		var interstellar = 	new Movie("Interstellar", 2014);
		var granTorino = new Movie("Gran Torino", 2008, 116, clint);
		var impitoyable = new Movie("Impitoyable", 1992,130, clint);
		var americanSniper = new Movie("American Sniper", 2014, 133, clint);
		var veryBadTrip = new Movie("Very Bad Trip", 2009, 100, todd);
		var avengersInfinity = new Movie("Avengers: Infinity War", 2018, 149);
		var avengersEndgame = new Movie("Avengers: Endgame", 2019, 181);
		var avengers = new Movie("Avengers", 2012, 143);
		var captainMarvel = new Movie("Captain Marvel", 2019, 123);
		var avengersUltron = new Movie("Avengers: Age of Ultron", 2015, 141);
		var nightOf = new Movie("Night of the Day of the Dawn of the Son of the Bride of the Return of the Revenge of the Terror of the Attack of the Evil Mutant Hellbound Flesh Eating Crawling Alien Zombified Subhumanoid Living Dead, Part 5",
					2011, 84);
		var movies = List.of(joker, parasite, interstellar, granTorino, impitoyable, americanSniper,
				veryBadTrip);
		movies.forEach(repoMovies::save);
	}

	@Rollback(false)
	@Test
	void scenario02AddDirectorExistingMovie() {
		var movies = repoMovies.findByTitle("Interstellar");
		if (movies.size() > 0) {
			var interstellar = movies.stream().findFirst().get();
			var chris = new Person("Christopher Nolan", LocalDate.of(1970, 7, 30));
			repoPersons.save(chris);
			interstellar.setDirector(chris);
		}
	}
	
	
	
	@Rollback(false)
	@Test
	void scenario03SelectMovieWithDirector() {
		var movies = repoMovies.findByTitle("Interstellar");
		if (movies.size() > 0) {
			// 
			var interstellar = movies.stream().findFirst().get();
			// if fetch mode is eager, director is already fetched
			// if not (LAZY)  the first access to attribute fetch the data
			var director = interstellar.getDirector();
			System.out.println(director);
		}
	}
	
	@Rollback(false)
	@Test
	void scenario04AddMovieExistingDirector() {
		// add movie from front action
		Movie batman = new Movie("The Dark Knight", 2008, 153);
		// add movie in the repository
		repoMovies.save(batman);
		// load Chris Nolan from database
		var nolan =  repoPersons.findByName("Christopher Nolan")
				.stream().findFirst().get();
		batman.setDirector(nolan);
		repoMovies.flush();
	}
	
	@Test
	void scenario05SelectTitlePartial() {
		var data = repoMovies.findByTitleContainingIgnoreCase("dark");
		System.out.println(data);
	}
	
	@Test
	void scenario05SelectByDirector() {
		var data1 = repoMovies.findByDirectorNameEndingWith("Eastwood");
		var nolan = repoPersons.findByName("Christopher Nolan")
				.stream().findFirst().get();
		var data2 = repoMovies.findByDirector(nolan);
		System.out.println(data1);
		System.out.println(data2);
	}
	
	@Test
	void scenario06SelectPersonByYear() {
		var data = repoPersons.findByBirthdateYear(1930);
		System.out.println(data);
	}
	
	@Rollback(false)
	@Test
	void scenario07MovieWithActor() {
		var impitoyable = repoMovies.findByTitle("Impitoyable").stream().findFirst().get();
		var clint = repoPersons.findByName("Clint Eastwood").stream().findFirst().get();
		var gene = repoPersons.findByName("Gene Hackman").stream().findFirst().get();
		impitoyable.setActors(List.of(clint,gene));
		repoMovies.flush();
	}
	
	@Rollback(false)
	@Test
	void scenario08MovieAddActor() {
		var impitoyable = repoMovies.findByTitle("Impitoyable").stream().findFirst().get();
		var morgan = repoPersons.findByName("Morgan Freeman").stream().findFirst().get();
		impitoyable.getActors().add(morgan);
		repoMovies.flush();
	}
	
	@Test
	void testLazyActors() {
		// read a movie : select the movie + its director 
		var impitoyable = repoMovies.findByTitle("Impitoyable").stream().findFirst().get();
		// read actors
		// var actors = impitoyable.getActors();
		// System.out.println(actors);
	}
	
}








