package book.chat.repository;

import book.chat.DTO.ReviewDTO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReviewBoardRepository {
    private static final Map<Long, ReviewDTO> reviews  = new HashMap<>(); //static

    public List<ReviewDTO> findAll(){
        return new ArrayList<>(reviews.values());
    }
}
