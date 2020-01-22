package cinema.persistence.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import cinema.persistence.entity.Movie;
import cinema.persistence.entity.Person;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
	Set<Movie> findByTitle(String title);
	Set<Movie> findByTitleContainingIgnoreCase(String title);
	Set<Movie> findByYearBetween(int year1, int year2);
	Set<Movie> findByTitleAndYear(String title, int year);
	Set<Movie> findByDirector(Person director);
	Set<Movie> findByDirectorNameEndingWith(String name);
}
