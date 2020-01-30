package test.cinema.data;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.postgresql.ds.PGSimpleDataSource;

import cinema.dto.Movie;
import cinema.dto.Person;

class TestJdbc {
	static DataSource ds;
	
	@BeforeAll
	static void initDataSource() {
		PGSimpleDataSource pgds = new PGSimpleDataSource();
		String host = "localhost";
		String dbname = "postgres";
		int port = 5432;
		String user = "cinema";
		String password = "password";
		pgds.setURL(
				"jdbc:postgresql://"   
				+ host+ ":" + port + "/" + dbname);
		pgds.setUser(user);
		pgds.setPassword(password);
		ds = pgds;
	}

	@Test
	void testLireFilms() throws SQLException {
		var listMovie = new TreeSet<Movie>(
				Comparator.comparing(Movie::getTitle)
					.thenComparing(Movie::getYear));
		String sql = "select * from film";
		try (
			Connection conn = ds.getConnection();
			Statement request = conn.createStatement();
			ResultSet res = request.executeQuery(sql);
		) {
			while (res.next()) {
				String title = res.getString("titre");
				int year = res.getInt("annee");
				int duration = res.getInt("duree");
				listMovie.add(new Movie(title, year, duration));
			}
		} // 	res/request/conn.close();  // in every scenario if possible
		System.out.println(listMovie);
		for (var m: listMovie) {
			System.out.println(m + " de durée " + m.getDuration() + " mn");
		}
		var totalDuration = listMovie.parallelStream()
			.mapToInt(Movie::getDuration)
			.sum();
		System.out.println("Durée totale: " + totalDuration);
		
	}

	@Test
	void testLireFilmsFiltre() throws SQLException {
		var listMovie = new TreeSet<Movie>(
				Comparator.comparing(Movie::getTitle)
					.thenComparing(Movie::getYear));
		String sql = "select * from film where duree >= ?";
		int durationThreshold = 120;
		// sql += durationThreshold;
		System.out.println(sql);
		try (
			Connection conn = ds.getConnection();
			PreparedStatement request = conn.prepareStatement(sql);
		) {
			request.setInt(1, durationThreshold);
			try (ResultSet res = request.executeQuery()){
				while (res.next()) {
					String title = res.getString("titre");
					int year = res.getInt("annee");
					int duration = res.getInt("duree");
					listMovie.add(new Movie(title, year, duration));
				}
			} // res.close()
		} // request/conn.close()
		System.out.println(listMovie);
		// check all movies with duration >= durationThreshold
		assertAll(listMovie.stream()
			.map(m -> () -> assertTrue(m.getDuration() >= durationThreshold, m.getTitle())));	
	}
	
	@Test
	void testLireFilmsFiltreInjection() throws SQLException {
		String sql = "select * from film where titre = '";
		String sql2 = "'";
		String title = "Joker' or true or titre='";
		sql += title + sql2;
		System.out.println(sql);
		try (
				Connection conn = ds.getConnection();
				Statement request = conn.createStatement()
			){
				try (ResultSet res = request.executeQuery(sql)) {
					while(res.next()) {
						System.out.println(res.getString("titre"));
					}
				}
			}
	}
	
	@Test
	void testLireFilmsFiltreInjectionNotAllowed() throws SQLException {
		String sql = "select * from film where titre = ?";
		String title = "Joker' or true or titre='";
		try (
			Connection conn = ds.getConnection();
			PreparedStatement request = conn.prepareStatement(sql)
		){
			request.setString(1, title);
			try (ResultSet res = request.executeQuery()) {
				while(res.next()) {
					System.out.println(res.getString("titre"));
				}
			}
		}

	}
		
	@Test
	void movieIsComparable(){
		Movie m = new Movie("Joker", 2019);
		System.out.println(m instanceof Comparable);
	}
	
	@Test
	void testAddPerson() throws SQLException {
		var persons = List.of(
				new Person("Bradley Cooper", LocalDate.of(1975, 1, 5)),
				new Person("Gene Hackman", LocalDate.of(1930, 1, 30)), 
				new Person("Morgan Freeman", LocalDate.of(1937, 6, 1)) 
		);
		String sql = "insert into individu (prenom, nom, date_naissance) values (?,?,?)";
		try (
			Connection conn = ds.getConnection();
			PreparedStatement request = conn.prepareStatement(sql);	
		) {
			for (var person: persons) {
				String fullname = person.getName();
				var parts = fullname.split(" ");
				System.out.println(Arrays.toString(parts));
				request.setString(1, parts[0]);
				request.setString(2, parts[1]);
				request.setDate(3, Date.valueOf(person.getBirthdate()));
				request.executeUpdate();
			}
		}
	}
}
