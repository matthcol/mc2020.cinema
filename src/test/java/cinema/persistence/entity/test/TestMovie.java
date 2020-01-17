package cinema.persistence.entity.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import cinema.persistence.entity.Movie;
import cinema.persistence.repository.MovieRepository;

@DataJpaTest
class TestMovie {

	@Autowired
	MovieRepository repoMovie;
	
	@Test
	void test() {
		Movie movie = new Movie("Joker", 2019);
		repoMovie.save(movie);
	}

}
