package app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.entities.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	public Member findBySchoolId(String schoolId);

	public List<Member> findByNameContainingIgnoreCase(String name);

}
