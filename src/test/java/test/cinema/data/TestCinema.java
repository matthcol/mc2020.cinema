package test.cinema.data;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Formatter;
import java.util.List;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import javax.print.attribute.standard.Fidelity;

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
				new Person("Todd Phillips", LocalDate.of(1970, 12, 20)),	//2							// 2
				new Person("Clint Eastwood", LocalDate.of(1930, 5, 31)),	// 3
				new Person("Bradley Cooper", LocalDate.of(1975, 1, 5)),		// 4
				new Person("Gene Hackman", LocalDate.of(1930, 1, 30)),  // 5
				new Person("Morgan Freeman", LocalDate.of(1937, 6, 1))  // 6
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
				new Movie("Very Bad Trip", 2009, 100, todd),	// 6
				new Movie("Avengers: Infinity War", 2018, 149),
				new Movie("Avengers: Endgame", 2019, 181),
				new Movie("Avengers", 2012, 143),
				new Movie("Captain Marvel", 2019, 123),
				new Movie("Avengers: Age of Ultron", 2015, 141)
				//new Movie("Night of the Day of the Dawn of the Son of the Bride of the Return of the Revenge of the Terror of the Attack of the Evil Mutant Hellbound Flesh Eating Crawling Alien Zombified Subhumanoid Living Dead, Part 5",
				//	2011, 84)
				);
		movies.get(0).addActor(persons.get(0));
		movies.get(4).addAllActors(persons.get(3), persons.get(5), persons.get(6));
		var actorsParasite = List.of(
				new Person("Kang-ho Song"),
				new Person("Yeo-jeong Jo"),
				new Person("Woo-sik Choi"),
				new Person("Jeong-eun Lee"));
		persons.addAll(actorsParasite);
		movies.get(1).addAllActors(actorsParasite);
	}
	
	@Test
	void testModifiableList() {
		System.out.println(persons);
		System.out.println(persons.getClass());
		persons.add(new Person("Bradley Cooper", LocalDate.of(1975, 1, 5)));
		System.out.println(persons);
	}

	@Test
	void displayMoviesForI() {
		System.out.println("*** Movie list ***");
		for (int i=0; i < movies.size(); i++) {
			var movie = movies.get(i);
			System.out.println(" - " + movie + " directed by " + movie.getDirector());
		}
	}
	
	@Test
	void displayMoviesForEach() {
		System.out.println("*** Movie list ***");
		for (var movie: movies) {
			System.out.println(" - " + movie + " directed by " + movie.getDirector());
		}
	}
	
	@Test
	void totalDurationOfMoviesDirectedByClintEastwood() {
		int totalDurationInt = 0; // TODO
		var clint = persons.get(3);
		for (var movie: movies) {
			if (clint.equals(movie.getDirector())) {
				totalDurationInt += movie.getDuration();
			}
		}
		var totalDuration = Duration.ofMinutes(totalDurationInt);
		System.out.println("Total duration of movies directed by Clint Eastwood: "
				+ totalDuration);
		// select sum(duration) from movie 
		// where id_director = (select id_person from person where name = 'Clint Eastwood')
	}
	
	@Test
	void totalDurationOfMoviesDirectedByClintEastwoodStreaming() {
		var clint = persons.get(3);
		int totalDuration = movies.stream()
			.filter(m -> clint.equals(m.getDirector()))
			.mapToInt(Movie::getDuration)
			//.forEach(System.out::println);
			.sum();
		System.out.println("Total duration of movies directed by Clint Eastwood: "
				+ totalDuration);
	}
	
	@Test
	void testSortedMovies() {
		SortedSet<Movie> sortedMovies = new TreeSet<>(
				Comparator.comparing(Movie::getYear, Comparator.reverseOrder())
					.thenComparing(Movie::getTitle));
				//(m1,m2) -> -1);
		sortedMovies.addAll(movies);
		System.out.println(movies);
	}
	
	@Test
	void testSortMovies() {
		Collections.sort(movies, 
				Comparator.comparing(Movie::getYear)
					.thenComparing(Movie::getTitle));
					//.thenComparing(m->m.getTitle()));
		System.out.println(movies);
	}
	
	@Test
	void testAvengersFirstYear() {
		var optFirstYear = movies.stream()
			.filter(m -> m.getTitle().contains("Avengers"))
			//.forEach(System.out::println);
			.mapToInt(Movie::getYear)
			.min();
		if (optFirstYear.isPresent()) {
			System.out.println("First year: " + optFirstYear.getAsInt());
		} else {
			System.out.println("Pas de r�sultat");
		}
	}
	
	@Test
	void testAvengersFirstLastYear() {
		var stats = movies.stream()
				.filter(m -> m.getTitle().contains("Avengers"))
				//.forEach(System.out::println);
				.mapToInt(Movie::getYear)
				.summaryStatistics();
		System.out.println("First year: " + stats.getMin());
		System.out.println("Last year: " + stats.getMax());
	}
	
	@Test
	void testListeChronologiqueAvengersMovie() {
		var avengersMovies = movies.stream()
			.filter(m -> m.getTitle().contains("Avengers"))
			.collect(Collectors.toCollection(()->new TreeSet<>(
					Comparator.comparing(Movie::getYear)
					)));
		System.out.println(avengersMovies);
	}
	
	@Test
	void titlesAvengersMovies() {
		var joinedTitles = movies.stream()
				.filter(m -> m.getTitle().contains("Avengers"))
				.map(Movie::getTitle)
				.collect(Collectors.joining(", "));
		System.out.println(joinedTitles);
	}
	
	@Test
	void testLimit() {
		movies.stream()
			.filter((Movie m) -> m.getYear() > 1900)
			.limit(5)
			.forEach(System.out::println);		
	}

	@Test
	void filmOver2H() {
		long nbFilms = movies.stream()
			.filter(m -> m.getDuration()>=120)
			.count();
		System.out.println("Nb films de plus de 2H : " + nbFilms);
	}
	
	@Test
	void longestTitle() {
		var maxLength = movies.stream()
			.map(Movie::getTitle)
			.mapToInt(String::length)
			//.forEach(System.out::println);
			.max()
			.getAsInt();
		System.out.println(maxLength);
		var titlesMaxLength = movies.stream()
			.map(Movie::getTitle)
			.filter(t -> t.length() == maxLength)
			.collect(Collectors.toList());
		System.out.println("Films au titre le + long : " + titlesMaxLength);
	}
	
	@Test
	void nbMovieByYear() {
		var res = movies.stream()
			.collect(Collectors.groupingBy(Movie::getYear, Collectors.counting()));
		System.out.println(res);
	}
	
	@Test
	void nbMovieByDirector() {
		var nbMovieByDir = movies.stream()
			.map(Movie::getDirector)
			.filter(Objects::nonNull)
			.collect(Collectors.groupingBy(UnaryOperator.identity(), 
					Collectors.counting()));
		System.out.println(nbMovieByDir);
		nbMovieByDir = movies.stream()
				.filter(m -> Objects.nonNull(m.getDirector()))
				.collect(Collectors.groupingBy(Movie::getDirector, 
						Collectors.counting()));
		System.out.println(nbMovieByDir);
		var filmographies = movies.stream()
				.filter(m -> Objects.nonNull(m.getDirector()))
				.collect(Collectors.groupingBy(Movie::getDirector,
						Collectors.toCollection(
								()->new TreeSet<>(Comparator.comparing(Movie::getYear,
										Comparator.reverseOrder()))
								)));
		System.out.println(filmographies);	
	}
	
	@Test
	void personsByDecade() {
		var res = persons.stream()
			.collect(Collectors.groupingBy(p -> p.getBirthdate().getYear() / 10));
		System.out.println(res);
	}
	
	@Test
	void testParasite() {
		movies.stream()
			.filter(m -> m.getTitle().equals("Parasite"))
			.findFirst()
			.ifPresent(System.out::println);
	}
	
	@Test
	void testParasiteStream() {
		var movie = movies.get(1);
		var actorsText = movie.streamActors()
				.map(Person::getName)
				.collect(Collectors.joining(", "));
		System.out.println("Acteurs de Parasite : " + actorsText);
	}
	
	@Test
	void testParasiteIterable() {
		var movie = movies.get(1);
		for (var it = movie.iteratorActor(); it.hasNext(); ) {
			var actor = it.next();
			System.out.println(actor);
		}
	}
}
