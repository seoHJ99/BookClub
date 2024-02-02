package book.chat.domain.repository;

import book.chat.domain.DTO.MemberDTO;
import book.chat.domain.entity.Member;
import book.chat.domain.DTO.MemberJoinForm;

import java.util.Optional;

public interface MemberRepository {

    Member save(MemberJoinForm joinForm);
    Member update(MemberDTO memberDTO);

    Optional<Member> findById(String id);

    Optional<Member> findBySessionKey(String id);
}
