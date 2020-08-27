package cinema.dto;

import java.util.List;

public class MovieFull extends MovieLight {
	private String originalTitle;
	private Integer duration;
	private List<String> genres;
	private String synopsis;
	private PersonDto director;
	private List<PersonDto> actors;
	private int numberActors;
	
	
	public String getOriginalTitle() {
		return originalTitle;
	}
	
	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}
	
	public Integer getDuration() {
		return duration;
	}
	
	public void setDuration(Integer duration) {
		this.duration = duration;
	}
	public List<String> getGenres() {
		return genres;
	}
	public void setGenres(List<String> genres) {
		this.genres = genres;
	}
	public String getSynopsis() {
		return synopsis;
	}
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public PersonDto getDirector() {
		return director;
	}

	public void setDirector(PersonDto director) {
		this.director = director;
	}

	public List<PersonDto> getActors() {
		return actors;
	}

	public void setActors(List<PersonDto> actors) {
		this.actors = actors;
	}

	public int getNumberActors() {
		return numberActors;
	}

	public void setNumberActors(int numberActors) {
		this.numberActors = numberActors;
	}

 }
