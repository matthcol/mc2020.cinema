package cinema.persistence.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import cinema.persistence.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
	Set<Movie> findByTitle(String title);
	Set<Movie> findByYearBetween(int year1, int year2);
	Set<Movie> findByTitleAndYear(String title, int year);
}
