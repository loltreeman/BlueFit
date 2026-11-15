package app.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasksComponent {

	@Autowired
	private SubscriptionComponent subscriptionComponent;

	@Autowired
	private NotificationComponent notificationComponent;

	// every minute for the demo; the notification log stops repeats, so this would be daily in production
	@Scheduled(fixedRate = 60000, initialDelay = 30000)
	public void runDailyTasks() {
		System.out.println("Running scheduled tasks");

		try {
			subscriptionComponent.updateStatuses();
			notificationComponent.sendRenewalReminders();
			notificationComponent.sendInactivityMessages();
			notificationComponent.sendOwnerSummary();
		} catch (Exception e) {
			System.out.println("Scheduled tasks failed: " + e.getMessage());
		}
	}

}
