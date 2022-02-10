package spring.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.demo.entity.Person;


@Repository
public interface PersonRepo extends JpaRepository<Person,Long> {
    Person findByName(String name);

    Person findByAge(int age);

    //Person findByMoviesId(long movieId);



}








