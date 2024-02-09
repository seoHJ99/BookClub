package book.chat.domain.repository;

import book.chat.domain.entity.Review;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ReviewBoardRepositoryImpl implements ReviewBoardRepository {

    private long sequence =0L;

    private static final Map<Long, Review> store = new HashMap<>(); //static


    public Optional<Review> findById(Long id){
        return Optional.ofNullable(store.get(id));
    }


    @Override
    public Review save(Review review) {
        store.put(++sequence, review);
        return review;
    }

    @Override
    public Review findByNo(Long no) {
        return store.get(no);
    }

    @Override
    public List<Review> findAll() {
        return null;
    }

    @Override
    public List<Review> findAllByBookName(String name) {
        return null;
    }

    @Override
    public List<Review> findAllByClubNo(Long no) {
        return null;
    }

    @Override
    public List<Review> findAllByMember(Long no) {
        return null;
    }

    @Override
    public List<Review> findAllByMember(String memberName) {
        return null;
    }

    @Override
    public List<Review> findAllByReviewName(String name) {
        return null;
    }
}
