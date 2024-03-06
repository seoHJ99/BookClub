package book.chat.domain.service;

import book.chat.domain.entity.Comment;
import book.chat.domain.repository.CommentRepository;
import book.chat.web.DTO.CommentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public void save(CommentDTO commentDTO){
        // todo writerId 로그인 구현후 삽입
        commentDTO.setDate(LocalDate.now());
        commentDTO.setTime(LocalTime.now());
        commentDTO.setWriterId("aaa");
        commentRepository.save(new Comment(commentDTO));
    }

    public List<CommentDTO> findByBoardNo(Long no){
        List<Comment> entities = commentRepository.findByIdBoardNo(no);
        return entities.stream()
                .map(entity -> new CommentDTO(entity))
                .collect(Collectors.toList());
    }

}
