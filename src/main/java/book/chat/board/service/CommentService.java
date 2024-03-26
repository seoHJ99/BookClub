package book.chat.board.service;

import book.chat.board.dto.ClubCommentDTO;
import book.chat.board.dto.CommentDTO;
import book.chat.board.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ClubCommentRepository clubCommentRepository;

    @Transactional
    public void save(CommentDTO commentDTO){
        commentDTO.setDate(LocalDate.now());
        commentDTO.setTime(LocalTime.now());
        commentRepository.save(new Comment(commentDTO));
    }

    public List<ClubCommentDTO> findClubCommentById(String id){
        List<ClubCommentDTO> dtoList = clubCommentRepository.findAllByIdWriterIdOrderByIdDateDesc(id).stream()
                .map(ClubCommentDTO::new)
                .collect(Collectors.toList());
        for (ClubCommentDTO clubCommentDTO : dtoList) {
            System.out.println(clubCommentDTO);
        }
        return dtoList;
    }

    public List<CommentDTO> findByBoardNo(Long no){
        List<Comment> entities = commentRepository.findByIdBoardNoOrderByIdDate(no);
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
        List<Comment> entity = commentRepository.findWithBoardByWriterIdOrderByIdDate(memberId);
        return entity.stream().map(CommentDTO::new).collect(Collectors.toList());
    }


}
