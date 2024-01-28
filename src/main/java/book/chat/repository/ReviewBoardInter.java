package book.chat.repository;

import book.chat.DTO.ReviewDTO;

import java.util.List;

public interface ReviewBoardInter {

    List<ReviewDTO> findAll();

}
