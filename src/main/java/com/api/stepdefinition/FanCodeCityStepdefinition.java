package com.api.stepdefinition;

import static org.junit.Assert.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import com.api.utils.TestContext;
import io.cucumber.java.en.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FanCodeCityStepdefinition {
	private TestContext context;
	private static final Logger LOG = LogManager.getLogger(FanCodeCityStepdefinition.class);
	
	public FanCodeCityStepdefinition(TestContext context) {
		this.context = context;
	}

	@Given("User has the {string} tasks")
	public void userHasTheTasks(String endpoint) {
		context.session.put("endpoint", endpoint);
		context.response = context.requestSetup().when().get(context.session.get("endpoint").toString());

		// Get all tasks
		List<Map<String, Object>> tasks = context.response.jsonPath().getList("$");

		// Initialize counters and HashMap
		int totalTasks = tasks.size();
		int completedTasks = 0;

		Map<Integer, Integer> userTasksCount = new HashMap<>();
		Map<Integer, Integer> userCompletedTasksCount = new HashMap<>();

		// Iterate through tasks to count completed tasks and tasks per user
		for (Map<String, Object> task : tasks) {
			int userId = (int) task.get("userId");
			boolean completed = (boolean) task.get("completed");

			userTasksCount.put(userId, userTasksCount.getOrDefault(userId, 0) + 1);
			if (completed) {
				completedTasks++;
				userCompletedTasksCount.put(userId, userCompletedTasksCount.getOrDefault(userId, 0) + 1);
			}
		}

		// Log total tasks and completed tasks
		LOG.info("Total Tasks: " + totalTasks);
		LOG.info("Completed Tasks: " + completedTasks);

		// Assert that we have tasks and at least one completed task
		assertNotNull("No tasks found!", tasks);
		assertNotNull("No completed tasks found!", completedTasks);

		// Store user completion percentage in context
		Map<Integer, Float> userCompletionPercentage = new HashMap<>();
		for (Map.Entry<Integer, Integer> entry : userTasksCount.entrySet()) {
			int userId = entry.getKey();
			int totalUserTasks = entry.getValue();
			int completedUserTasks = userCompletedTasksCount.getOrDefault(userId, 0);
			float completionPercentage = ((float) completedUserTasks / totalUserTasks) * 100;
			userCompletionPercentage.put(userId, completionPercentage);
		}

		context.session.put("totalTasks", totalTasks);
		context.session.put("completedTasks", completedTasks);
		context.session.put("userCompletionPercentage", userCompletionPercentage);

		// Example logging of user completion percentages
		for (Map.Entry<Integer, Float> entry : userCompletionPercentage.entrySet()) {
			LOG.info("User " + entry.getKey() + " Completion Percentage: " + entry.getValue() + "%");
		}

	}

	@And("{string} belongs to the city FanCode")
	public void belongsToTheCityFanCode(String endpoint) {
		context.session.put("endpoint", endpoint);
		context.response = context.requestSetup().when().get(context.session.get("endpoint").toString());

		context.session.put("endpoint", endpoint);
		context.response = context.requestSetup().when().get(context.session.get("endpoint").toString());

		// Get list of users
		List<Map<String, Object>> users = context.response.jsonPath().getList("$");

		// List to store users from FanCode city
		List<Map<String, Object>> fancodeUsers = new ArrayList<>();

		// Iterate through users to find those from FanCode city
		for (Map<String, Object> user : users) {
			Map<String, Object> address = (Map<String, Object>) user.get("address");
			Map<String, Object> geo = (Map<String, Object>) address.get("geo");

			double lat = Double.parseDouble(geo.get("lat").toString());
			double lng = Double.parseDouble(geo.get("lng").toString());

			// Check if user is from FanCode city
			if (lat >= -40 && lat <= 5 && lng >= 5 && lng <= 100) {
				fancodeUsers.add(user);
			}
		}

		// Log and store the users from FanCode city in the context session
		LOG.info("Users from FanCode city: " + fancodeUsers);
		context.session.put("fancodeUsers", fancodeUsers);

	}

	@Then("User Completed task percentage should be greater than {int}%")
	public void userCompletedTaskPercentageShouldBeGreaterThan50(int percentage) {
		// Retrieve the user completion percentages from the context
		Map<Integer, Float> userCompletionPercentage = (Map<Integer, Float>) context.session.get("userCompletionPercentage");

		// Retrieve the users from FanCode city from the context
		List<Map<String, Object>> fancodeUsers = (List<Map<String, Object>>) context.session.get("fancodeUsers");

		// Check each user from FanCode city
		for (Map<String, Object> user : fancodeUsers) {
			int userId = (int) user.get("id");
			double completionPercentage = userCompletionPercentage.getOrDefault(userId, 0.0f);

			assertTrue("User ID " + userId + " completion percentage is not greater than "+percentage+"%", completionPercentage > percentage);
			// Log the result
			LOG.info("User ID " + userId + " from FanCode city has a completion percentage of " + completionPercentage + "%, which is greater than "+percentage+"%");
		}
	}

}
