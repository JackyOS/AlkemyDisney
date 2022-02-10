package spring.demo.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.demo.dto.PersonBasicDTO;
import spring.demo.dto.PersonDTO;
import spring.demo.entity.Movie;
import spring.demo.entity.Person;
import spring.demo.exception.ResourceNotFoundException;
import spring.demo.repo.MovieRepo;
import spring.demo.repo.PersonRepo;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    PersonRepo personRepo;

    @Autowired
    MovieRepo movieRepo;

    @Autowired
    ModelMapper modelMapper;

    public List<PersonDTO> getPersonsDetails(){
        List<Person> persons = personRepo.findAll(); //traemos la lista de la base de datos
        return persons.stream().map(person -> mapperDTO(person)).collect(Collectors.toList());
    }

    public List<PersonBasicDTO> getPersons(){
        List<Person> persons = personRepo.findAll();
        return persons.stream().map(person -> (modelMapper.map(person, PersonBasicDTO.class))).collect(Collectors.toList());
    }

    public PersonDTO getPersonByName(String name){
        Person person = personRepo.findByName(name);
        return mapperDTO(person);
    }

    public PersonDTO getPersonByAge(int age){
        Person person = personRepo.findByAge(age);
        return mapperDTO(person);
    }

    public List<PersonDTO> getPersonByMovieId(long movieId){
        Movie movie = movieRepo.findById(movieId)
                .orElseThrow(()-> new ResourceNotFoundException("Movie", "id", movieId));
        return movie.getPersons().stream().map(person -> mapperDTO(person)).collect(Collectors.toList());
    }

    public PersonDTO createPerson (PersonDTO personDTO){
        Person person = mapperEntity(personDTO);
        Person newPerson = personRepo.save(person);
        PersonDTO personRes = mapperDTO(newPerson);
        return personRes;
    }

    public PersonDTO getPersonById(long id){
        Person person = personRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Person", "id", id));
        return mapperDTO(person);
    }

    public PersonDTO updatePerson (PersonDTO personDTO, long id){
        Person person = personRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Person", "id", id));
        person.setName(personDTO.getName());
        person.setImage(personDTO.getImage());
        person.setAge(personDTO.getAge());
        person.setHistory(personDTO.getHistory());
        person.setWeight(personDTO.getWeight());
        person.setMovies(personDTO.getMovies());
        Person updatePerson = personRepo.save(person);
        return mapperDTO(updatePerson);
    }

    public void deletePerson(long id){
        Person person = personRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Person", "id", id));
        personRepo.delete(person);
    };

    //convierte la entidad a dto
    public PersonDTO mapperDTO(Person person){
        PersonDTO personDTO = modelMapper.map(person, PersonDTO.class);
        //PersonDTO personDTO = new PersonDTO();
        //personDTO.setId(person.getId());
        //personDTO.setName(person.getName());
        //personDTO.setImage(person.getImage());
        //personDTO.setAge(person.getAge());
        //personDTO.setWeight(person.getWeight());
        //personDTO.setHistory(person.getHistory());
        //personDTO.setMovies(person.getMovies());
        return personDTO;
    }

    //convierte dto a entidad
    public Person mapperEntity(PersonDTO personDTO){
        Person person = modelMapper.map(personDTO, Person.class);
        //Person person = new Person();
        //person.setName(personDTO.getName());
        //person.setImage(personDTO.getImage());
        //person.setAge(personDTO.getAge());
        //person.setWeight(personDTO.getWeight());
        //person.setHistory(personDTO.getHistory());
        //person.setMovies(personDTO.getMovies());
        return person;
    }


}
