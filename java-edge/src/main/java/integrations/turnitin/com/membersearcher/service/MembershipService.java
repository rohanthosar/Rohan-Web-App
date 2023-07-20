package integrations.turnitin.com.membersearcher.service;

import java.util.List;
// import java.lang.reflect.Member;
// import java.util.List;
import java.util.concurrent.CompletableFuture;
// import java.util.stream.Collectors;
import java.util.stream.Collectors;

import integrations.turnitin.com.membersearcher.client.MembershipBackendClient;
import integrations.turnitin.com.membersearcher.model.Membership;
import integrations.turnitin.com.membersearcher.model.MembershipList;
// import integrations.turnitin.com.membersearcher.model.UserList;
// import integrations.turnitin.com.membersearcher.model.User;
// import integrations.turnitin.com.membersearcher.model.Membership;
import integrations.turnitin.com.membersearcher.model.UserList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MembershipService {
	@Autowired
	private MembershipBackendClient membershipBackendClient;

	/**
		* Fetches all memberships with associated users using asynchronous calls.
 	*
 	* This method combines two asynchronous operations: fetching a list of users
 	* and fetching a list of memberships from the php-backend service, and then associates each membership with
 	* its corresponding user based on the user ID. The result is a CompletableFuture
 	* that completes with a MembershipList containing memberships and their associated users.
	* 
	* @return A CompletableFuture that completes with the MembershipList containing memberships
 	*         and their associated users once the asynchronous fetch operations are complete.
	*
	
	 */
	public CompletableFuture<MembershipList> fetchAllMembershipsWithUsers() {
		CompletableFuture<UserList> userList = membershipBackendClient.fetchUsers();
		CompletableFuture<MembershipList> membershipList = membershipBackendClient.fetchMemberships();

		return userList.thenCombine(membershipList, (users, memberships) -> {
			List<Membership> updatedMemberships = memberships.getMemberships().stream()
					.flatMap(membership -> users.getUsers().stream()
							.filter(user -> membership.getUserId().equals(user.getId()))
							.findFirst()
							.map(user -> {
								membership.setUser(user);
								return membership;
							})
							.stream()
					)
					.collect(Collectors.toList());

			return new MembershipList().setMemberships(updatedMemberships);
		});
	}



	 
	// public CompletableFuture<MembershipList> fetchAllMembershipsWithUsers() {

	// 	// CompletableFuture<UserList> userList = membershipBackendClient.fetchUsers();
	// 	return membershipBackendClient.fetchMemberships()
	// 			.thenCompose(members -> {
	// 				CompletableFuture<?>[] userCalls = members.getMemberships().stream()
	// 						.map(member -> membershipBackendClient.fetchUser(member.getUserId())
	// 								.thenApply(member::setUser))
	// 						.toArray(CompletableFuture<?>[]::new);
	// 				return CompletableFuture.allOf(userCalls)
	// 						.thenApply(nil -> members);
	// 			});
		// return membershipBackendClient.fetchMemberships()
		// 	.thenCompose(members -> {
		// 		CompletableFuture<?>[] userCalls = members.getMemberships().stream()
		// 				.map(member -> findUser(member, userList))
		// 						.thenApply(member::setUser))
		// 				.toArray(CompletableFuture<?>[]::new);
		// 		return CompletableFuture.allOf(userCalls)
		// 				.thenApply(nil -> members);
		// 	});
	}
	
	//
	// Helper method to find the corresponding User for a Membership
    // private static CompletableFuture<User> findUser(CompletableFuture<Membership> member, List<CompletableFuture<User>> userList) {
    //     return member.thenCompose(membership -> userList.stream()
    //             .filter(userFuture -> userFuture.thenApply(User::getId).join().equals(membership.getUserId()))
    //             .findFirst()
    //             .orElse(CompletableFuture.completedFuture(null)));
    // }

	// Helper method to update the Membership with the User information
    // private static Membership updateMember(Membership membership, User user) {
    //     if (user != null) {
    //         membership.setUser(user);
    //     }
    //     return membership;
    // }

