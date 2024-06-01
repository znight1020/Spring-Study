package hello.servlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 동시성 문제 고려 시, ConcurrentHashMap, AtomicLong 이란 게 있다.
 * */
public class MemberRepository {
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequnce = 0L;

    private static final MemberRepository instance = new MemberRepository();

    public static MemberRepository getInstance(){
        return instance;
    }
    private MemberRepository() {}

    public Member save(Member member){
        member.setId(++sequnce);
        store.put(member.getId(), member);
        return member;
    }

    public List<Member> findAll(){
        return new ArrayList<>(store.values());
    }
    public Member findById(long id){
        return store.get(id);
    }

    public void clearStore(){
        store.clear();
    }
}
