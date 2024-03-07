package book.chat.member.entity;

import book.chat.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Member save(Member joinForm);
//    Member update(MemberDTO memberDTO);

    Optional<Member> findById(String id);

    Member findByNo(Long no);

//    Optional<Member> findBySessionKey(String id);

//    List<Member> findByJoinClubIn(Long clubNo);

    List<Member> findByNoIn(List<Long> memberNos);
}
