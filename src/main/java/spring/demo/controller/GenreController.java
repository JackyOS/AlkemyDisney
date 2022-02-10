package spring.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.demo.dto.GenreDTO;
import spring.demo.service.GenreService;

import java.util.List;

@RestController
@RequestMapping("/api/genre")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @GetMapping //listar generos
    public ResponseEntity<List<GenreDTO>> getGenre() {
        return ResponseEntity.ok().body(genreService.getGenre());
    }


    @PostMapping //crear genero
    public ResponseEntity<GenreDTO> saveGenre(@RequestBody GenreDTO genreDTO){
        return new ResponseEntity<>(genreService.createGenre(genreDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}") // actualizamos el genero
    public ResponseEntity<GenreDTO> updateGenre(@RequestBody GenreDTO genreDTO, @PathVariable long id){
        GenreDTO genreRes = genreService.updateGenre(genreDTO, id); //actualizamos la movie
        return new ResponseEntity<>(genreRes, HttpStatus.OK);
    }

    @DeleteMapping("/{id}") //borramos el genero
    public ResponseEntity<String> deleteGenre(@PathVariable long id){
        genreService.deleteGenre(id);
        return new ResponseEntity<>("Genre deleted successfully",HttpStatus.OK);
    }

}
