package book.chat.board.service;

import book.chat.board.dto.CommentDTO;
import book.chat.board.entity.Comment;
import book.chat.board.entity.CommentRepository;
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
//
//    public List<CommentDTO> findByWriter(String memberId){
//        List<Comment> entity = commentRepository.findByIdWriterId(memberId);
//        return entity.stream().map(CommentDTO::new).collect(Collectors.toList());
//    }

    public List<CommentDTO> findByWriter(String memberId){
        List<Comment> entity = commentRepository.findWithBoardByWriterId(memberId);
        return entity.stream().map(CommentDTO::new).collect(Collectors.toList());
    }


}
