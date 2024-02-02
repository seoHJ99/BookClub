package book.chat.domain.repository;

import book.chat.domain.DTO.ReviewDTO;
import book.chat.entity.RedisTestEntity;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ReviewBoardRepositoryImpl implements ReviewBoardRepository {

    private long sequence =0L;

    private static final Map<Long, ReviewDTO> store = new HashMap<>(); //static

    public ReviewDTO save(ReviewDTO entity){
        store.put(++sequence, entity);
        return entity;
    }

    public Optional<ReviewDTO> findById(Long id){
        return Optional.ofNullable(store.get(id));
    }

    public List<ReviewDTO> findAll(){
        return new ArrayList<>(store.values());
    }

    @Override
    public List<ReviewDTO> findAllByBookName(String name) {
        return null;
    }

    @Override
    public List<ReviewDTO> findAllByClubNo(Long no) {
        return null;
    }

    @Override
    public List<ReviewDTO> findAllByMember(Long no) {
        return null;
    }

    @Override
    public List<ReviewDTO> findAllByMember(String memberName) {
        return null;
    }

    @Override
    public List<ReviewDTO> findAllByReviewName(String name) {
        return null;
    }
}
