package cinema.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cinema.dto.MovieFull;
import cinema.persistence.entity.Movie;

@Configuration
public class Utils {
	
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper =  new ModelMapper();
		modelMapper.typeMap(MovieFull.class, Movie.class)
			.addMappings(m->m.skip(Movie::setDirector))
			.addMappings(m->m.skip(Movie::setActors));
		return modelMapper;
	}

	public static <T,U,C extends Collection<U>>
	C mapToCollection(Collection<? extends T> coll, ModelMapper modelMapper, 
			Class<U> classU, Supplier<C> supplierColl)
	{
		return coll.stream()
				.map(t->modelMapper.map(t, classU))
				.collect(Collectors.toCollection(supplierColl));
	}
	
	public static <T,U>
	List<U> mapToList(Collection<? extends T> coll, ModelMapper modelMapper, 
			Class<U> classU)
	{
		return mapToCollection(coll, modelMapper, classU, ArrayList::new);
	}
	
}
