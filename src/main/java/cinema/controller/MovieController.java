package cinema.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cinema.dto.MovieFull;
import cinema.dto.MovieLight;

import cinema.service.IMovieService;

@RestController
@RequestMapping("/api/movie")
public class MovieController {
	
	@Autowired
	IMovieService movieService;
	
	@GetMapping
	@ResponseBody
	public List<MovieLight> allMovies() {
		return movieService.getAllMovies();
	}
	
	@GetMapping("/{id}")
	@ResponseBody
	public Optional<MovieFull> movieById(@PathVariable("id") int idMovie) {
		return movieService.getMovieById(idMovie);
	}
	
	@GetMapping("/byTitle")
	@ResponseBody
	public Set<MovieLight> movieByPartialTitle(@RequestParam("t") String partialTitle) {
		return movieService.getMovieByPartialTitle(partialTitle);
	}
	
	@GetMapping("/byDirector")
	@ResponseBody
	public Set<MovieLight> findByDirector(@RequestParam("d") int idDirector){
		return movieService.getMoviesByDirector(idDirector);
	}
	
	@GetMapping("/byActor")
	@ResponseBody
	public Set<MovieLight> findByActor(@RequestParam("a") int idActor){
		return movieService.getMoviesByActor(idActor);
	}
	
	@PostMapping
	@ResponseBody
	public MovieFull addMovie(@RequestBody MovieFull movie) {
		return movieService.addMovie(movie);
//		MovieFull movieSaved = movieRepository.save(movie);
//		movieRepository.flush();
//		return movieSaved;
	}

	@PutMapping("/modify")
	@ResponseBody
	public Optional<MovieFull> modifyMovie(@RequestBody MovieFull movie) {
		return movieService.modifyMovie(movie);
	}
//		// TODO : anywhere else
//		var optMovie = movieRepository.findById(movie.getIdMovie());
//		optMovie.ifPresent(m-> {
//			m.setTitle(movie.getTitle());
//			m.setYear(movie.getYear());
//			m.setDuration(movie.getDuration());
//			m.setDirector(movie.getDirector());
//			movieRepository.flush();
//		});
//		//		
//		return optMovie;
//	}
//	
	@PutMapping("/addActor")
	public Optional<MovieFull> addActor(@RequestParam("a") int idActor, @RequestParam("m") int idMovie) {
		return movieService.addActor(idActor, idMovie);
	}
//	
//	@PutMapping("/setDirector")
//	public Optional<Movie> setDirector(@RequestParam("d") int idDirector, @RequestParam("m") int idMovie) {
//		// TODO : anywhere else
//		var movieOpt = movieRepository.findById(idMovie);
//		var directorOpt = personRepository.findById(idDirector);
//		if (movieOpt.isPresent() && directorOpt.isPresent()) {
//			movieOpt.get().setDirector(directorOpt.get());
//			movieRepository.flush();
//		}
//		return movieOpt;
//	}
//	
//	
//	@DeleteMapping("/{id}")
//	@ResponseBody
//	public Optional<Movie> deleteMovie(@PathVariable("id") int idMovie) {
//		// TODO : anywhere else
//		var movieToDelete = movieRepository.findById(idMovie);
//		movieToDelete.ifPresent(m-> {
//			movieRepository.delete(m);
//			movieRepository.flush();
//		});
//		// 
//		return movieToDelete;
//	}
}
