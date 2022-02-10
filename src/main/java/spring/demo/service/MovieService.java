package spring.demo.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import spring.demo.dto.MovieDTO;
import spring.demo.dto.MovieResponse;
import spring.demo.dto.PersonDTO;
import spring.demo.entity.Movie;
import spring.demo.entity.Person;
import spring.demo.exception.ResourceNotFoundException;
import spring.demo.repo.MovieRepo;
import spring.demo.repo.PersonRepo;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class MovieService {

    @Autowired //injeccion de dependencias. Con esto no necesitamos instanciar el objeto
    MovieRepo movieRepo;

    @Autowired //injeccion de dependencias. Con esto no necesitamos instanciar el objeto
    PersonRepo personRepo;

    @Autowired
    ModelMapper modelMapper;


    public MovieResponse getMovies(int pageNumber, int pageSize, String sortBy, String sortDir){
        //orderno, si es asc ordeno ascendente sino ordeno descendente
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Movie> movies = movieRepo.findAll(pageable);
        List<Movie> listMovie = movies.getContent();
        List<MovieDTO> content = listMovie.stream().map(movie -> mapperDTO(movie))
                .collect(Collectors.toList());
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.setContent(content);
        movieResponse.setPageNumber(movies.getNumber());
        movieResponse.setPageSize(movies.getSize());
        movieResponse.setTotalElements(movies.getTotalElements());
        movieResponse.setTotalPages(movies.getTotalPages());
        movieResponse.setLast(movies.isLast());
        return movieResponse;
    }


    public MovieDTO getMovieByTitle(String title){
        Movie movie = movieRepo.findByTitle(title);
        return mapperDTO(movie);
    }

    public List<MovieDTO> getMovieByGenreId(Long idGenre){
        List<Movie> movies =  movieRepo.findByGenresId(idGenre);
        //devolvemo la lista mapeada, la convertimos a dto
        return movies.stream().map(movie -> mapperDTO(movie)).collect(Collectors.toList());
    }


    public MovieDTO getMovieById(long id){
        //busco por id una pelicula
        Movie movie = movieRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Movie", "id", id));
        //or else throw => si no encuentra la pelicula tira una excepcion
        //la clase que creamos para manejar los errores  ResourceNotFoundException

        //mapeamos la entidad a dto
        return mapperDTO(movie);
    }

    public MovieDTO createMovie (MovieDTO movieDTO){
        //al crear una movie, recibimos un json
        //convertimos de dto (el json) a una entidad para guardarlo en la base de datos
        Movie movie = mapperEntity(movieDTO);
        //se guarda en la base de datos
        Movie newMovie = movieRepo.save(movie);
        //convertimos la entidad a dto (el json) para dar la respuesta
        MovieDTO movieRes = mapperDTO(newMovie);
        return movieRes;
    }

    public MovieDTO updateMovie (MovieDTO movieDTO, long id){
        Movie movie = movieRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Movie", "id", id));
        movie.setTitle(movieDTO.getTitle());
        movie.setImage(movieDTO.getImage());
        movie.setDate(movieDTO.getDate());
        movie.setRating(movieDTO.getRating());
        movie.setPersons(movieDTO.getPersons());
        movie.setGenres(movieDTO.getGenres());
        movie.setReviews(movieDTO.getReviews());
        Movie updatedMovie = movieRepo.save(movie);
        return mapperDTO(updatedMovie);
    }

    public void deleteMovie(long id){
        Movie movie = movieRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Movie", "id", id));
        movieRepo.delete(movie);
    };

    //convierte la entidad a dto
    public MovieDTO mapperDTO(Movie movie){
        MovieDTO movieDTO = modelMapper.map(movie, MovieDTO.class);
                     //lo que tenemos -entidad-, a que lo queremos convertir -dto-

        //con la dependencia model mapper evitamos hacer esto:
        //MovieDTO movieDTO = new MovieDTO();
        //movieDTO.setId(movie.getId());
        //movieDTO.setTitle(movie.getTitle());
        //movieDTO.setImage(movie.getImage());
        //movieDTO.setDate(movie.getDate());
        //movieDTO.setRating(movie.getRating());
        //movieDTO.setPersons(movie.getPersons());
        //movieDTO.setGenres(movie.getGenres());
        //movieDTO.setReviews(movie.getReviews());
        return movieDTO;
    }

    //convierte dto a entidad
    public Movie mapperEntity(MovieDTO movieDTO){
        Movie movie = modelMapper.map(movieDTO, Movie.class);
        //Movie movie = new Movie();
        //movie.setTitle(movieDTO.getTitle());
        //movie.setImage(movieDTO.getImage());
        //movie.setDate(movieDTO.getDate());
        //movie.setRating(movieDTO.getRating());
        //movie.setPersons(movieDTO.getPersons());
        //movie.setGenres(movieDTO.getGenres());
        //movie.setReviews(movieDTO.getReviews());
        return movie;
    }


    public MovieDTO addPersonToMovie(long movieId, long personId){
        Person person = personRepo.findById(personId)
                .orElseThrow(()-> new ResourceNotFoundException("Person", "id", personId));
        Movie movie = movieRepo.findById(movieId)
                .orElseThrow(()-> new ResourceNotFoundException("Movie", "id", movieId));
        movie.addCharacter(person);
        Movie updatedMovie = movieRepo.save(movie);
        //convertimos la entidad a dto (el json) para dar la respuesta
        return mapperDTO(updatedMovie);
    }


}
