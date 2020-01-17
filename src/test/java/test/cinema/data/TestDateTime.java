package test.cinema.data;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.Test;

class TestDateTime {

	@Test
	void testParseFrenchDate() {
		String dateStr = "15/02/2021";
		LocalDate date = LocalDate.parse(dateStr, 
				DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		System.out.println(date);
		assertAll(
				()->assertEquals(15, date.getDayOfMonth(), "day"),
				()->assertEquals(1, date.getMonthValue(), "month"),
				()->assertEquals(2020, date.getYear(), "year"));
	}
	
	@Test
	void testFormatDate() {
		LocalDate date = LocalDate.now();
		var formats = List.of(
				DateTimeFormatter.ofPattern("dd/MM/yyyy"),
				DateTimeFormatter.ofPattern("eeee dd MMMM yyyy"),
				DateTimeFormatter.ofPattern("eeee dd MMMM yyyy",
						Locale.US),
				DateTimeFormatter.ofPattern("eeee dd MMMM yyyy",
						new Locale("es", "es")),
				DateTimeFormatter.ofPattern("eeee dd MMMM yyyy",
						new Locale("lv", "lv")),
				DateTimeFormatter.ofPattern("eeee dd MMMM yyyy",
						new Locale("ru", "ru")),
				DateTimeFormatter.ofPattern("eeee dd MMMM yyyy",
						new Locale("ja", "jpn")));
		formats.forEach(f -> System.out.println(date.format(f)));
	}
	
	@Test
	void unPetitTourEnInde() {
		var date = LocalDate.now();
		Arrays.stream(Locale.getAvailableLocales())
				.filter(l -> l.getCountry().equals("IN"))
				//.forEach(System.out::println);
				.map(l -> date.format(
						DateTimeFormatter.ofPattern("eeee dd MMMM yyyy", l)))
				.forEach(System.out::println);
	}
	
	@Test
	void tourDuMonde80jours() {
		var dtHere = LocalDateTime.now();
		System.out.println("Here: " + dtHere);
		var dtNY = LocalDateTime.now(ZoneId.of("America/New_York"));
		System.out.println("New York: " + dtNY);
		var dtNZ = LocalDateTime.now(ZoneId.of("Pacific/Auckland"));
		System.out.println("New Zealand: " + dtNZ);
		var dtMidway = LocalDateTime.now(ZoneId.of("Pacific/Midway"));
		System.out.println("Midway: " + dtMidway);
		//System.out.println(ZoneId.SHORT_IDS);
		var fmt = DateTimeFormatter.ofPattern("kk:mm:ss dd/MM/yyyy");
		System.out.println("A Midway c'est : " + dtMidway.format(fmt));
	}

}
