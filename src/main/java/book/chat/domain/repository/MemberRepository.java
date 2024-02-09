package book.chat.domain.repository;

import book.chat.web.DTO.MemberDTO;
import book.chat.domain.entity.Member;
import book.chat.web.DTO.MemberJoinForm;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(MemberJoinForm joinForm);
    Member update(MemberDTO memberDTO);

    Optional<Member> findById(String id);

    Optional<Member> findBySessionKey(String id);

    List<Member> findByClubNo(Long clubNo);
}
