package book.chat.domain.repository;

import book.chat.domain.DTO.MeetingDto;
import book.chat.domain.DTO.MemberDTO;
import book.chat.domain.DTO.MemberJoinForm;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemberRepositoryImpl implements MemberRepository{
    private long sequence =0L;

    private static final Map<Long, MemberDTO> store = new HashMap<>(); //static

    public MemberDTO save(MemberJoinForm joinForm){
        MemberDTO entity = new MemberDTO(joinForm);
        store.put(++sequence, entity);
        return entity;
    }

    public Optional<MemberDTO> findById(Long id){
        return Optional.ofNullable(store.get(id));
    }
}
