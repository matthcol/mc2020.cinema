package test.tools;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import cinema.data.Movie;
import tools.TriFunction;

class TestTriFunction {

	@Test
	void test() {
		TriFunction<String,Integer, Integer, Movie> f;
		f = Movie::new;
		var m = f.apply("Joker", 2019, 165);
		System.out.println(m);
		System.out.println(m.getClass());
	}

}
