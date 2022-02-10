package spring.demo.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import spring.demo.dto.ReviewDTO;
import spring.demo.entity.Movie;
import spring.demo.entity.Review;
import spring.demo.exception.AppException;
import spring.demo.exception.ResourceNotFoundException;
import spring.demo.repo.MovieRepo;
import spring.demo.repo.ReviewRepo;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepo reviewRepo;

    @Autowired
    private MovieRepo MovieRepo;

    @Autowired
    private ModelMapper modelMapper;

    public ReviewDTO createReview(long movieId, ReviewDTO reviewDTO){
        Review review = mapperEntity(reviewDTO);

        //buscamos la pelicula por id para asignarle el comentario
        Movie movie = MovieRepo.findById(movieId)
                 .orElseThrow(()-> new ResourceNotFoundException("Movie", "id", movieId));

        //asigno el comentario a la pelicula (estoy agregando la pelicula a ese comnetario)
        review.setMovie(movie);

        //se guarda en la base de datos
        Review newReview = reviewRepo.save(review);
        //convertimos la entidad a dto (el json) para dar la respuesta
        ReviewDTO reviewRes = mapperDTO(newReview);
        return reviewRes;
    }

    public List<ReviewDTO> getReviewsByMovieId(long movieId){
        List<Review> reviews = reviewRepo.findByMovieId(movieId);
        return reviews.stream().map(review -> mapperDTO(review)).collect(Collectors.toList());
    }

    public ReviewDTO getReviewById(long movieId, long reviewId){
     Review review = reviewRepo.findById(reviewId)
             .orElseThrow(()-> new ResourceNotFoundException("Review", "id", reviewId));
     Movie movie = MovieRepo.findById(movieId)
             .orElseThrow(()-> new ResourceNotFoundException("Movie", "id", movieId));
     //si el id de la movie del review no es igual el id de la movie, entonces el comentario no pertenece a la pelicula
     if(!review.getMovie().getId().equals(movie.getId())) {
         throw new AppException(HttpStatus.BAD_REQUEST, "The review not belong to the movie");
     }
     return mapperDTO(review);
    }

    public ReviewDTO updateReview(long movieId, long reviewId, ReviewDTO reviewDTO){
        Review review = reviewRepo.findById(reviewId)
                .orElseThrow(()-> new ResourceNotFoundException("Review", "id", reviewId));

        Movie movie = MovieRepo.findById(movieId)
                .orElseThrow(()-> new ResourceNotFoundException("Movie", "id", movieId));

        if(!review.getMovie().getId().equals(movie.getId())) {
            throw new AppException(HttpStatus.BAD_REQUEST, "The review not belong to the movie");
        }

        review.setName(reviewDTO.getName());
        review.setEmail(reviewDTO.getEmail());
        review.setContent(reviewDTO.getContent());

        Review newReview = reviewRepo.save(review);

        return mapperDTO(newReview);
    }

    public void deleteReview(long movieId,long reviewId){
        Review review = reviewRepo.findById(reviewId)
                .orElseThrow(()-> new ResourceNotFoundException("Review", "id", reviewId));

        Movie movie = MovieRepo.findById(movieId)
                .orElseThrow(()-> new ResourceNotFoundException("Movie", "id", movieId));

        if(!review.getMovie().getId().equals(movie.getId())) {
            throw new AppException(HttpStatus.BAD_REQUEST, "The review not belong to the movie");
        }

        reviewRepo.delete(review);
    }

    //convierte la entidad a dto, un objeto de transferencia de datos
    public ReviewDTO mapperDTO(Review review){
        ReviewDTO reviewDTO = modelMapper.map(review, ReviewDTO.class);
        //ReviewDTO reviewDTO = new ReviewDTO();
        //reviewDTO.setId(review.getId());
        //reviewDTO.setName(review.getName());
        //reviewDTO.setEmail(review.getEmail());
        //reviewDTO.setContent(review.getContent());
        return reviewDTO;
    }

    //convierte dto a entidad
    public Review mapperEntity(ReviewDTO reviewDTO){
        Review review = modelMapper.map(reviewDTO, Review.class);
        //Review review = new Review();
        //review.setId(reviewDTO.getId());
        //review.setName(reviewDTO.getName());
        //review.setEmail(reviewDTO.getEmail());
        //review.setContent(reviewDTO.getContent());
        return review;
    }

}
