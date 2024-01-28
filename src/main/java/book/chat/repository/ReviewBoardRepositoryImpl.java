package book.chat.repository;

import book.chat.DTO.ReviewDTO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReviewBoardRepositoryImpl implements ReviewBoardRepository {
    private static final Map<Long, ReviewDTO> store = new HashMap<>(); //static

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
