package cinema.dto;

import java.time.LocalDate;
import java.time.MonthDay;
import java.util.Objects;
import java.util.OptionalInt;

public class PersonDto {

	private Integer idPerson;
	private String name;
	private LocalDate birthdate;
	
	public Integer getIdPerson() {
		return idPerson;
	}
	public void setIdPerson(Integer idPerson) {
		this.idPerson = idPerson;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}
	
	

}
