package integrations.turnitin.com.membersearcher;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.fasterxml.jackson.databind.ObjectMapper;
import integrations.turnitin.com.membersearcher.client.MembershipBackendClient;
import integrations.turnitin.com.membersearcher.model.Membership;
import integrations.turnitin.com.membersearcher.model.MembershipList;
import integrations.turnitin.com.membersearcher.model.User;
import integrations.turnitin.com.membersearcher.model.UserList;
import integrations.turnitin.com.membersearcher.service.MembershipService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MembershipServiceTest {
	@InjectMocks
	private MembershipService membershipService;

	@Mock
	private MembershipBackendClient membershipBackendClient;

	@Mock
	private ObjectMapper objectMapper;

	private MembershipList members;

	private UserList users;

	private User userOne;

	private User userTwo;

	@BeforeEach
	public void init() {
		members = new MembershipList()
				.setMemberships(List.of(
						new Membership()
								.setId("a")
								.setRole("instructor")
								.setUserId("1"),
						new Membership()
								.setId("b")
								.setRole("student")
								.setUserId("2")
				));
		userOne = new User()
				.setId("1")
				.setName("test one")
				.setEmail("test1@example.com");
		userTwo = new User()
				.setId("2")
				.setName("test two")
				.setEmail("test2@example.com");
		users = new UserList().setUsers(List.of(userOne, userTwo));
		when(membershipBackendClient.fetchUsers()).thenReturn(CompletableFuture.completedFuture(users));
		when(membershipBackendClient.fetchMemberships()).thenReturn(CompletableFuture.completedFuture(members));
	}

	/**
 * Test case for fetching all memberships with associated users from the MembershipService.
 * This test verifies that the MembershipService correctly fetches memberships and users
 * asynchronously and associates them.
 *
 * The test performs the following steps:
 * 1. Calls the fetchAllMembershipsWithUsers() method of the MembershipService to fetch
 *    all memberships with associated users using asynchronous calls.
 * 2. Waits for the CompletableFuture to complete and obtain the MembershipList result.
 * 3. Asserts that the number of memberships in the result is equal to 2, indicating that
 *    two memberships with associated users are fetched.
 * 4. Asserts that the first membership's associated user is equal to userOne, and the
 *    second membership's associated user is equal to userTwo. 
 */

	@Test
	public void TestFetchAllMemberships() throws Exception {

		MembershipList members = membershipService.fetchAllMembershipsWithUsers().get();
		assertThat(members.getMemberships().size()).isEqualTo(2);
		assertThat(members.getMemberships().get(0).getUser()).isEqualTo(userOne);
		assertThat(members.getMemberships().get(1).getUser()).isEqualTo(userTwo);
	}
}