package cinema.persistence.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "movies")
public class Movie {

	
	private Integer idMovie;
	private String title;
	private Integer year;
	private Integer duration;
	private List<String> genres;
	private String synopsis;
	
	private Person director;
	private List<Person> actors;
	
	public Movie() {
		this(null,null);
	}
	
	public Movie(String title, Integer year) {
		this(title, year, null);
	}

	public Movie(String title, Integer year, Integer duration) {
		this(null, title, year, duration, null);
	}

	public Movie(String title, Integer year, Integer duration, Person director) {
		this(null, title, year, duration , director);
	}
	
	public Movie(Integer idMovie, String title, Integer year, Integer duration, 
			Person director) 
	{
		super();
		this.idMovie = idMovie;
		this.title = title;
		this.year = year;
		this.duration = duration;
		this.director = director;
		this.actors = new ArrayList<>();
		this.genres = new ArrayList<>();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_movie")
	public Integer getIdMovie() {
		return idMovie;
	}

	public void setIdMovie(Integer idMovie) {
		this.idMovie = idMovie;
	}

	@Column(nullable = false, length = 255)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(nullable = false)
	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	@Column(nullable = true)
	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	
	@ElementCollection
	@CollectionTable(
			name = "genres",
			joinColumns = @JoinColumn(name = "id_movie"))
	@Column(name="genre")
	public List<String> getGenres() {
		return genres;
	}

	public void setGenres(List<String> genres) {
		this.genres = genres;
	}

	@Column(nullable = true)
	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	@ManyToOne // (fetch=FetchType.LAZY) : default EAGER
	@JoinColumn(name="id_director", nullable=true)
	public Person getDirector() {
		return director;
	}

	public void setDirector(Person director) {
		this.director = director;
	}

	@ManyToMany //(fetch = FetchType.EAGER) // load all actors with movie
	 @JoinTable(name="act",
     	joinColumns=@JoinColumn(name="id_movie"),
     	inverseJoinColumns=@JoinColumn(name="id_actor")
     )
	public List<Person> getActors() {
		return actors;
	}

	public void setActors(List<Person> actors) {
		this.actors = actors;
	}
	
	@Transient
	public int getNumberActors() {
		return actors.size();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(title);
		return builder.append(" (")
				.append(year)
				.append(')')
				.append('#')
				.append(idMovie)
				.toString();
	}
}
