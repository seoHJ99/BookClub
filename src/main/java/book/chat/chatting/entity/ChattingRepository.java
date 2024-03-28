package book.chat.chatting.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChattingRepository extends JpaRepository<Chatting, Chatting.ChattingId> {

}
