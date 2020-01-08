package test.cinema.data;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import cinema.data.Movie;

class TestMovie {

	@Test
	void test() {
		Movie movie = new Movie();
		System.out.println(movie);
		new Movie();
	}

}
