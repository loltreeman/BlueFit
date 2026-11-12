package app.components;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.dto.DeleteDTO;
import app.dto.PlanDTO;
import app.entities.Member;
import app.entities.MembershipPlan;
import app.repositories.MembershipPlanRepository;
import app.repositories.SubscriptionRepository;
import app.rest.errorhandlers.ServiceException;

@Component
public class PlanComponent {

	@Autowired
	private MembershipPlanRepository planRepo;

	@Autowired
	private SubscriptionRepository subscriptionRepo;

	@Autowired
	private MemberComponent memberComponent;

	public PlanDTO addPlan(String name, BigDecimal price, Integer durationDays, String eligibleAffiliation)
			throws ServiceException {
		if (name != null && planRepo.findByName(name) != null) {
			throw new ServiceException("A plan named " + name + " already exists.");
		}

		MembershipPlan plan = new MembershipPlan(name, price, durationDays, eligibleAffiliation);
		return toDTO(planRepo.save(plan));
	}

	public PlanDTO getPlan(Long pk) throws ServiceException {
		return toDTO(find(pk));
	}

	public List<PlanDTO> getAllPlans() {
		return toDTOs(planRepo.findAll());
	}

	public List<PlanDTO> getAvailablePlans(Long memberPk) throws ServiceException {
		Member member = memberComponent.find(memberPk);
		return toDTOs(planRepo.findByEligibleAffiliationIn(Arrays.asList(member.getAffiliation(), "ALL")));
	}

	// existing subscriptions keep their agreedPrice
	public PlanDTO updatePrice(Long pk, BigDecimal price) throws ServiceException {
		MembershipPlan plan = find(pk);
		plan.setPrice(price);
		return toDTO(planRepo.save(plan));
	}

	public DeleteDTO deletePlan(Long pk) throws ServiceException {
		MembershipPlan plan = find(pk);

		if (subscriptionRepo.countByPlan(plan) > 0) {
			throw new ServiceException("Plan " + pk + " is used by subscriptions and cannot be deleted.");
		}

		planRepo.delete(plan);
		return new DeleteDTO(true, pk);
	}

	public MembershipPlan find(Long pk) throws ServiceException {
		return planRepo.findById(pk).orElseThrow(() -> new ServiceException("Plan " + pk + " not found."));
	}

	public PlanDTO toDTO(MembershipPlan p) {
		return new PlanDTO(p.getPk(), p.getName(), p.getPrice(), p.getDurationDays(), p.getEligibleAffiliation());
	}

	private List<PlanDTO> toDTOs(List<MembershipPlan> plans) {
		List<PlanDTO> list = new ArrayList<>();
		for (MembershipPlan p : plans) {
			list.add(toDTO(p));
		}
		return list;
	}

}
