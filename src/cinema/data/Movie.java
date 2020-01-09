package cinema.data;

import java.util.Objects;

public class Movie {
	// attributes
	private String title;
	private int year;
	private int duration;

	// constructors
	public Movie(String title, int year) {
		this(title, year, 0);
	}

	public Movie(String title, int year, int duration) {
		super();
		this.title = Objects.requireNonNull(title);
		this.year = year;
		this.duration = duration;
	}

	// getters + setters
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = Objects.requireNonNull(title);
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	// methods from Object
	
	@Override
	public String toString() {
		return title + " (" + year 
			//	+  ( duration==0 ? "" : ", " + duration + "mn")
				+ ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + year;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		Movie other = (Movie) obj;
		return this.title.equals(other.title)
				&& this.year == other.year;
	}
	
}
