package spring.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.demo.entity.Review;

import java.util.List;

public interface ReviewRepo extends JpaRepository <Review, Long> {

    List<Review> findByMovieId(long movieId);

}
