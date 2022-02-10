package spring.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.demo.entity.Movie;

import java.util.List;

@Repository
public interface MovieRepo extends JpaRepository<Movie,Long> {
    Movie findByTitle(String title);

    List<Movie> findByGenresId(long genreId);

}








