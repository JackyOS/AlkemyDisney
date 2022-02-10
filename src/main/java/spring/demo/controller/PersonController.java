package spring.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.demo.dto.MovieResponse;
import spring.demo.dto.PersonBasicDTO;
import spring.demo.dto.PersonDTO;
import spring.demo.dto.ReviewDTO;
import spring.demo.service.PersonService;
import spring.demo.utils.AppConst;

import javax.transaction.Transactional;
import java.util.List;


@RestController
@RequestMapping("/api/characters")
public class PersonController {


    @Autowired
    private PersonService personService;


    @GetMapping //listar personas - info basica
    public ResponseEntity<List<PersonBasicDTO>> getPersons() {
        return ResponseEntity.ok().body(personService.getPersons());
    }

    @GetMapping ("/details") //listar personas - info completa
    public ResponseEntity<List<PersonDTO>> getPersonsDetails() {
        return ResponseEntity.ok().body(personService.getPersonsDetails());
    }

    @GetMapping(params="name") //listar personas a patir del nombre
    public ResponseEntity<PersonDTO> getPersonByName(@RequestParam(value="name") String name) {
        return ResponseEntity.ok().body(personService.getPersonByName(name));
    }

    @GetMapping(params="age") //listar personas a patir de la edad
    public ResponseEntity<PersonDTO> getPersonByAge(@RequestParam(value="age") int age) {
        return ResponseEntity.ok().body(personService.getPersonByAge(age));
    }

    @GetMapping(params="idMovie") //listar personas a patir del id de la pelicula
    public ResponseEntity<List<PersonDTO>> getPersonByMovieId(@RequestParam(value="idMovie") long idMovie) {
        return ResponseEntity.ok().body(personService.getPersonByMovieId(idMovie));
    }

    @PostMapping //crear persona
    public ResponseEntity<PersonDTO> savePerson(@RequestBody PersonDTO personDTO){
        return new ResponseEntity<>(personService.createPerson(personDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}") // actualizamos la persona
    public ResponseEntity<PersonDTO> updatePerson(@RequestBody PersonDTO personDTO, @PathVariable long id){
        PersonDTO personRes = personService.updatePerson(personDTO, id); //actualizamos la movie
        return new ResponseEntity<>(personRes, HttpStatus.OK);
    }

    @DeleteMapping("/{id}") //borramos la persona
    public ResponseEntity<String> deletePerson(@PathVariable long id){
        personService.deletePerson(id);
        return new ResponseEntity<>("Person deleted successfully",HttpStatus.OK);
    }

}
