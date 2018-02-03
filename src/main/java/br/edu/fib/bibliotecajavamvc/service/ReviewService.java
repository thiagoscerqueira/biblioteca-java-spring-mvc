package br.edu.fib.bibliotecajavamvc.service;

import br.edu.fib.bibliotecajavamvc.model.Review;
import br.edu.fib.bibliotecajavamvc.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> reviewsDoLivro(Long idLivro) {
        return reviewRepository.reviewsDoLivro(idLivro);
    }

    public Double mediaDasAvaliacoes(List<Review> listaReviews) {
        Double average = listaReviews.stream().mapToDouble(review -> review.getAvaliacao()).average().orElse(0d);
        return Math.round(average * 100.00) / 100.00;
    }
}
