package book.chat.domain.service;

import book.chat.domain.entity.Comment;
import book.chat.domain.repository.CommentRepository;
import book.chat.web.DTO.CommentDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public void save(CommentDTO commentDTO){
        commentRepository.save(new Comment(commentDTO));
    }

    public List<CommentDTO> findByBoardNo(Long no){
        List<Comment> entities = commentRepository.findByBoardNo(no);
        return entities.stream()
                .map(entity -> new CommentDTO(entity))
                .collect(Collectors.toList());
    }

}
