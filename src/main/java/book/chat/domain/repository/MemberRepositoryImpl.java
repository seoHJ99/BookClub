package book.chat.domain.repository;

import book.chat.web.DTO.MemberDTO;
import book.chat.domain.entity.Member;
import book.chat.web.DTO.MemberJoinForm;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemberRepositoryImpl implements MemberRepository{
    private long sequence =0L;

    private static final Map<Long, Member> store = new HashMap<>(); //static

    @Override
    public Member save(MemberJoinForm joinForm){
        Member entity = new Member(joinForm);
        store.put(sequence, entity);
        return entity;
    }


    @Override
    public Member update(MemberDTO updateDto) {
        Member entity =store.get(updateDto.getNo());
        entity.updateField(updateDto);
        store.put(sequence, entity);
        return entity;
    }

    @Override
    public Optional<Member> findById(String id){
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findBySessionKey(String id) {
        return Optional.empty();
    }
}
