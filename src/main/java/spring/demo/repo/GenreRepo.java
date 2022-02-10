package spring.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.demo.entity.Genre;

@Repository
public interface GenreRepo extends JpaRepository<Genre,Long> {

}








