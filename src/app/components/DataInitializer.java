package app.components;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.entities.CheckIn;
import app.entities.Member;
import app.entities.MembershipPlan;
import app.entities.Payment;
import app.entities.Subscription;
import app.repositories.CheckInRepository;
import app.repositories.MemberRepository;
import app.repositories.MembershipPlanRepository;
import app.repositories.PaymentRepository;
import app.repositories.SubscriptionRepository;

// test data for the demo; dates are relative to today so every rule can be shown
@Component
public class DataInitializer {

	@Autowired
	private MemberRepository memberRepo;

	@Autowired
	private MembershipPlanRepository planRepo;

	@Autowired
	private SubscriptionRepository subscriptionRepo;

	@Autowired
	private PaymentRepository paymentRepo;

	@Autowired
	private CheckInRepository checkInRepo;

	@PostConstruct
	public void init() {
		if (memberRepo.count() != 0) {
			return;
		}

		LocalDate today = LocalDate.now();

		MembershipPlan monthlyStudent = plan("Monthly Student", "800.00", 30, "STUDENT");
		MembershipPlan semesterStudent = plan("Semester Student", "3500.00", 120, "STUDENT");
		MembershipPlan monthlyFaculty = plan("Monthly Faculty", "1200.00", 30, "FACULTY");
		MembershipPlan quarterlyFaculty = plan("Quarterly Faculty", "3200.00", 90, "FACULTY");
		MembershipPlan monthlyStaff = plan("Monthly Staff", "1000.00", 30, "STAFF");
		MembershipPlan monthlyAlumni = plan("Monthly Alumni", "1500.00", 30, "ALUMNI");
		plan("Day Pass", "150.00", 1, "ALL");

		Member juan = member("Juan Dela Cruz", "212345", "+639000000001", "juan@example.com", "STUDENT", today.minusDays(60));
		Member maria = member("Maria Santos", "213456", "+639000000002", "maria@example.com", "STUDENT", today.minusDays(23));
		Member jose = member("Jose Reyes", "100234", "+639000000003", null, "FACULTY", today.minusDays(29));
		Member ana = member("Ana Lim", "100567", "+639000000004", "ana@example.com", "STAFF", today.minusDays(40));
		Member carlo = member("Carlo Garcia", "185432", "+639000000005", null, "ALUMNI", today.minusDays(10));
		Member bea = member("Bea Tan", "224567", "+639000000006", "bea@example.com", "STUDENT", today.minusDays(2));
		Member miguel = member("Miguel Cruz", "100890", "+639000000007", null, "FACULTY", today.minusDays(40));

		// active, fully paid, regular visitor
		Subscription juanSub = subscription(juan, semesterStudent, today.minusDays(30), Subscription.ACTIVE);
		payment(juanSub, "3500.00", "GCASH", "GC100234", today.minusDays(30).atTime(9, 0));
		for (int d = 1; d <= 12; d++) {
			checkIn(juan, juanSub, today.minusDays(d * 2).atTime(d % 3 == 0 ? 17 : 7, 15));
		}

		// ends in 7 days -> 7-day reminder
		Subscription mariaSub = subscription(maria, monthlyStudent, today.minusDays(23), Subscription.ACTIVE);
		payment(mariaSub, "800.00", "CASH", null, today.minusDays(23).atTime(10, 30));
		checkIn(maria, mariaSub, today.minusDays(3).atTime(17, 40));

		// ends tomorrow, half paid -> both reminders and shows up as unpaid
		Subscription joseSub = subscription(jose, monthlyFaculty, today.minusDays(29), Subscription.ACTIVE);
		payment(joseSub, "600.00", "CARD", "CARD5521", today.minusDays(29).atTime(12, 0));
		checkIn(jose, joseSub, today.minusDays(1).atTime(6, 50));

		// expired 10 days ago -> check-in refused
		Subscription anaSub = subscription(ana, monthlyStaff, today.minusDays(40), Subscription.EXPIRED);
		payment(anaSub, "1000.00", "CASH", null, today.minusDays(40).atTime(8, 0));

		// frozen 5 days ago -> check-in refused, unfreeze adds days back
		Subscription carloSub = subscription(carlo, monthlyAlumni, today.minusDays(10), Subscription.ACTIVE);
		carloSub.setStatus(Subscription.FROZEN);
		carloSub.setFrozenOn(today.minusDays(5));
		subscriptionRepo.save(carloSub);
		payment(carloSub, "1500.00", "GCASH", "GC100891", today.minusDays(10).atTime(15, 0));

		// nothing paid yet -> check-in refused, shows up as unpaid
		subscription(bea, monthlyStudent, today.minusDays(2), Subscription.ACTIVE);

		// paid but last visit 20 days ago -> inactivity message
		Subscription miguelSub = subscription(miguel, quarterlyFaculty, today.minusDays(40), Subscription.ACTIVE);
		payment(miguelSub, "3200.00", "CASH", null, today.minusDays(40).atTime(18, 0));
		checkIn(miguel, miguelSub, today.minusDays(20).atTime(18, 20));

		System.out.println("BlueFit test data loaded");
	}

	private MembershipPlan plan(String name, String price, int days, String affiliation) {
		return planRepo.save(new MembershipPlan(name, new BigDecimal(price), days, affiliation));
	}

	private Member member(String name, String schoolId, String phone, String email, String affiliation,
			LocalDate joinedOn) {
		return memberRepo.save(new Member(name, schoolId, phone, email, affiliation, joinedOn));
	}

	private Subscription subscription(Member member, MembershipPlan plan, LocalDate start, String status) {
		return subscriptionRepo.save(new Subscription(member, plan, plan.getPrice(), start,
				start.plusDays(plan.getDurationDays()), status));
	}

	private void payment(Subscription s, String amount, String method, String referenceNo, LocalDateTime paidAt) {
		paymentRepo.save(new Payment(s, new BigDecimal(amount), method, referenceNo, paidAt));
	}

	private void checkIn(Member member, Subscription s, LocalDateTime at) {
		checkInRepo.save(new CheckIn(member, s, at));
	}

}
