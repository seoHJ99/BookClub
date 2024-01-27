package book.chat.repository;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class BoardRepository {
    private static final Map<Long, String> store = new HashMap<>(); //static

}
