package spring.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.demo.dto.MovieDTO;
import spring.demo.dto.MovieResponse;
import spring.demo.dto.PersonDTO;
import spring.demo.dto.ReviewDTO;
import spring.demo.entity.Movie;
import spring.demo.entity.Person;
import spring.demo.exception.ResourceNotFoundException;
import spring.demo.service.MovieService;
import spring.demo.utils.AppConst;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping //listar peliculas
    public MovieResponse getMovies(
            @RequestParam(value="pageNumber", defaultValue = AppConst.DEFAULT_NUMBER_PAGE, required = false) int pageNumber,
            @RequestParam(value="pageSize", defaultValue = AppConst.DEFAULT_SIZE_PAGE, required = false) int pageSize,
            @RequestParam(value="sortBy", defaultValue = AppConst.DEFAULT_ORDER_BY, required = false) String sortBy,
            @RequestParam(value="sortDir", defaultValue = AppConst.DEFAULT_ORDER_DIR, required = false) String sortDir //direccion -> asc o desc
    ) {
        return movieService.getMovies(pageNumber, pageSize, sortBy, sortDir);
    }

    @GetMapping(params="name") //listar movies a patir del nombre
    public ResponseEntity<MovieDTO> getMovieByName(@RequestParam(value="name") String name) {
        return ResponseEntity.ok().body(movieService.getMovieByTitle(name));
    }

    @GetMapping(params="idGenre") //listar personas a patir del id del genero
    public ResponseEntity<List<MovieDTO>> getMovieByName(@RequestParam(value="idGenre") long idGenre) {
        return ResponseEntity.ok().body(movieService.getMovieByGenreId(idGenre));
    }

    @GetMapping("/{id}") //le pasamos el id  --> listamos peliculas por id
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable long id){
        return ResponseEntity.ok(movieService.getMovieById(id));
    }

    @PostMapping //crear pelicula
    public ResponseEntity<MovieDTO> saveMovie(@Valid @RequestBody MovieDTO movieDTO){
       return new ResponseEntity<>(movieService.createMovie(movieDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}") // actualizamos pelicula
    public ResponseEntity<MovieDTO> updateMovie(@Valid @RequestBody MovieDTO movieDTO, @PathVariable long id){
        MovieDTO movieRes = movieService.updateMovie(movieDTO, id); //actualizamos la movie
        return new ResponseEntity<>(movieRes, HttpStatus.OK);
    }

    @DeleteMapping("/{id}") //borramos la pelicula
    public ResponseEntity<String> deleteMovie(@PathVariable long id){
        movieService.deleteMovie(id);
        return new ResponseEntity<>("Movie deleted successfully",HttpStatus.OK);
    }

    @PutMapping("/{movieId}/character/{characterId}") //agrego personaje a la pelicula
    public ResponseEntity<MovieDTO> addPerson(@PathVariable Long movieId, @PathVariable Long characterId){
        return ResponseEntity.ok().body(movieService.addPersonToMovie(movieId, characterId));
    }


}
