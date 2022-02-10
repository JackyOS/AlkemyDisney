package spring.demo.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.demo.dto.GenreDTO;
import spring.demo.entity.Genre;
import spring.demo.exception.ResourceNotFoundException;
import spring.demo.repo.GenreRepo;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class GenreService {

    @Autowired
    GenreRepo genreRepo;

    @Autowired
    ModelMapper modelMapper;

    public GenreDTO createGenre (GenreDTO genreDTO){
        Genre genre = mapperEntity(genreDTO);
        Genre newGenre = genreRepo.save(genre);
        GenreDTO genreRes = mapperDTO(newGenre);
        return genreRes;
    }

    public List<GenreDTO> getGenre(){
        List<Genre> genres = genreRepo.findAll(); //traemos la lista de la base de datos
        //mapeamos la entidad a dto y lo mostramos en una lista
        return genres.stream().map(genre -> mapperDTO(genre)).collect(Collectors.toList());
    }

    public GenreDTO updateGenre (GenreDTO genreDTO, long id){
        Genre genre = genreRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Genre", "id", id));
        genre.setName(genreDTO.getName());
        genre.setImage(genreDTO.getImage());
        genre.setMovies(genreDTO.getMovies());
        Genre updatedGenre = genreRepo.save(genre);
        return mapperDTO(updatedGenre);
    }

    public void deleteGenre(long id){
        Genre genre = genreRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Genre", "id", id));
        genreRepo.delete(genre);
    };

    //convierte la entidad a dto
    public GenreDTO mapperDTO(Genre genre){
        GenreDTO genreDTO = modelMapper.map(genre, GenreDTO.class);
        //GenreDTO genreDTO = new GenreDTO();
        //genreDTO.setId(genre.getId());
        //genreDTO.setName(genre.getName());
        //genreDTO.setImage(genre.getImage());
        //genreDTO.setMovies(genre.getMovies());
        return genreDTO;
    }

    //convierte dto a entidad
    public Genre mapperEntity(GenreDTO genreDTO){
        Genre genre = modelMapper.map(genreDTO, Genre.class);
        //Genre genre = new Genre();
        //genre.setName(genreDTO.getName());
        //genre.setImage(genreDTO.getImage());
        //genre.setMovies(genreDTO.getMovies());
        return genre;
    }

}
