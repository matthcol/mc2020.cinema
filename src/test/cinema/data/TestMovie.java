package test.cinema.data;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import cinema.data.Movie;

class TestMovie {

	@Test
	void testFirst() {
		Movie movie = new Movie("Joker", 2019, 165);
		Movie movie2 = new Movie("Parasite", 2019, 132);
		Movie movie3 = new Movie("Interstellar", 2014);
		List<Movie> movies = List.of(movie, movie2, movie3);
		System.out.println("Movies: " + movies); 
		System.out.println("Title: " + movie.getTitle());
		Movie oneMovie = movies.get(0);
		System.out.println("Movie tonight: " + oneMovie);
		// 
		movie.setYear(1900);
		System.out.println(movie);
		System.out.println(movies.get(0));
		System.out.println(oneMovie);
	}
	
	@Test
	void testEquals1() {
		Movie movieJ = new Movie("Joker", 2019, 165);
		Movie movieP = new Movie("Parasite", 2019, 132);
		Movie movie = movieJ;
		System.out.println(movieJ == movieP);
		System.out.println(movieJ == movieJ);
		System.out.println(movie == movieJ);
	}
	
	
	@Test
	void testEquals() {
		Movie movieChaosI = new Movie("Chaos", 2005);
		Movie movieChaosII = new Movie("Chaos", 2005);
		System.out.println(movieChaosI == movieChaosII);
		System.out.println(movieChaosI.equals(movieChaosII));
	}
	
	@Test
	void testMovieAsObject() {
		Movie movieChaosI = new Movie("Chaos", 2005);
		Object obj = "Chaos";//movieChaosI;
		Movie movie = (Movie) obj;
		System.out.println(movie.getTitle());
	}

}
