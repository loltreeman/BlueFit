package app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.entities.MembershipPlan;

@Repository
public interface MembershipPlanRepository extends JpaRepository<MembershipPlan, Long> {

	public MembershipPlan findByName(String name);

	public List<MembershipPlan> findByEligibleAffiliationIn(List<String> affiliations);

}
