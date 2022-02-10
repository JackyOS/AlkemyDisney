package spring.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.demo.dto.ReviewDTO;
import spring.demo.service.ReviewService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/movies/{movieId}/reviews")
    public ResponseEntity<ReviewDTO> saveReview(@PathVariable long movieId,@Valid @RequestBody ReviewDTO reviewDTO){
        return new ResponseEntity<>(reviewService.createReview(movieId, reviewDTO), HttpStatus.CREATED);
    }

    @GetMapping("/movies/{movieId}/reviews")
    public List<ReviewDTO> getReviewByMovieId(@PathVariable Long movieId){
        return reviewService.getReviewsByMovieId(movieId);
    }

    @GetMapping("/movies/{movieId}/reviews/{reviewId}")
    public ResponseEntity<ReviewDTO> getById(@PathVariable Long movieId, @PathVariable Long reviewId){
        return ResponseEntity.ok().body(reviewService.getReviewById(movieId, reviewId));
    }

    @PutMapping("/movies/{movieId}/reviews/{reviewId}")
    public ResponseEntity<ReviewDTO> getById(@PathVariable Long movieId, @PathVariable Long reviewId, @Valid @RequestBody ReviewDTO reviewDTO){
        return ResponseEntity.ok().body(reviewService.updateReview(movieId, reviewId, reviewDTO));
    }

    @DeleteMapping("/movies/{movieId}/reviews/{reviewId}")
    public ResponseEntity<String> delete(@PathVariable Long movieId, @PathVariable Long reviewId){
        reviewService.deleteReview(movieId, reviewId);
        return new ResponseEntity<>("Review deleted successfully",HttpStatus.OK);
    }

}
