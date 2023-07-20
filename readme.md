# DO NOT FORK!
**In the interest of fairness for everyone, please DO NOT fork this repo through Github. This is to avoid accidental sharing of the solution. If you are taking the test, please select "Use this template" to create your own repo and get started instead.**

# Background
Welcome to the Turnitin Code Test, congratulations on making it this far!

This test is a fictitious application made up of multiple components to represent the types of situations or problems you will encounter and have to solve within our team.

The applications is a simple web application that fetches a list of users and memberships and allows the user to search the memberships and view user details.

There are three components within the application, each with a different purpose and written in a different language.
* **react-frontend** - A simple react frontend written in TypeScript.
* **java-edge** - An edge service written as a Java Spring application that provides a rest API that can be called by the front end, and calls the backend service to fetch membership and user data.
* **php-backend** - A backend php service that provides user and membership data via an API. This data is stored in a Postgresql database.

To orchestrate running all parts of this service, docker and docker compose are used. See https://docs.docker.com/desktop/ for information on installing docker if you do not have it already.

# Tasks:
* Currently the java-edge service is making an API call per membership to fetch the user details. Refactor the java-edge service to use the `/api.php/users` endpoint to fetch all the users in one request rather than fetching the users individually.
  * Make sure to fix any broken tests.
  * Make sure any docs are updated appropriately.
  * The response from the java-edge service should not change.
* Searching for members by email is currently case sensitive. Update the search in the react-frontend to be case insensitive.
* The close (x) button on the user details modal doesn't currently function properly. Identify the problem and make the fix.

# Setup

1. Make sure you have installed Docker and Docker Compose
2. Run the following command from the root of the code test repo
```bash
docker-compose up --build
```
3. Go to http://localhost:8043 to run the application
4. If you have made changes to the code and want to rebuild the app with those latest changes, first stop the docker container (Ctrl+c) or `docker-compose down` then re-run the command from step 2

# Tests

To run the tests for the java-edge service, you can run the following command from the root of the code test repo
```bash
docker-compose -f docker-compose.test.yml up --build
```


# Changes
  Task 1
   1. The method's purpose is "Fetches all memberships with associated users using asynchronous calls."
   2. It explains how the method works: combining two asynchronous operations (fetching users and       memberships) and associating memberships with their corresponding users based on the user ID.
   3. The result type is mentioned: "A CompletableFuture that completes with the MembershipList containing memberships and their associated users once the asynchronous fetch operations are complete."

  Task 2
   1. updateSearch: (event: ChangeEvent<HTMLInputElement>) => A function that is triggered on every change in the search input field. 
   2. setSearch(event.target.value.toLowerCase()); It updates the search state with the lowercase version of the user's search term, ensuring a case-insensitive search. 


  Task 3
   1. <ModalHeader toggle={closeDetailsModal}>User Details</ModalHeader> - The header of the details modal with a "User Details" title. The closeDetailsModal function is called when the user clicks on the close (x) button to close the modal.

  TestFetchAllMemberships()
   
  This test verifies that the MembershipService correctly fetches memberships and users asynchronously and associates them.
  The steps performed by the test are outlined:
     1. Call the fetchAllMembershipsWithUsers() method of the MembershipService to fetch all memberships with associated users using asynchronous calls.
     2.  Wait for the CompletableFuture to complete and obtain the MembershipList result.
     3. Assert that the number of memberships in the result is equal to 2, indicating that two memberships with associated users are fetched.
     4. Assert that the first membership's associated user is equal to userOne, and the second membership's associated user is equal to userTwo.
  The @Test annotation indicates that this method is a test case, and the throws Exception clause specifies that the test may throw an exception if there is an error during the test execution.











