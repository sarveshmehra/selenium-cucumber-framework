package stepDefinitions;

import com.bisnode.api.scim.User;
import com.bisnode.common.login.rest.BisnodeIdApi;
import com.bisnode.login.simplescimclient.testing.UserBuilder;
import cucumber.api.java.en.Given;
import runner.TestContext;
import java.util.UUID;

public class CreateUserSteps {

    TestContext testContext;
    public CreateUserSteps(TestContext context){
        testContext = context;
    }

    @Given("^A user exists to support forgot password$")
    public void a_user_exists_to_support_forgot_password() {
        UUID uuid = UUID.randomUUID();
        deleteAndCreateUser(uuid.toString(), testContext.getWebDriverManager().browserSpecificUser());
    }

    private void deleteAndCreateUser(String userId, String userName) {

        User existingUser = new BisnodeIdApi().getUser(userName);
        if (existingUser != null)
            new BisnodeIdApi().delete(existingUser);
        existingUser = new BisnodeIdApi().getUser(userName);
        assert existingUser == null;

        User user = UserBuilder.user()
                .withUserName(userName)
                .withPassword("Secret123")
                .withPrimaryPhoneNumber("+672351111")
                .withUserType("WEB")
                .withExternalId("demo_id", userName.replace("@bisnode.com", ""))
                .thatIsActive()
                .build();

        user.setId(userId);
        new BisnodeIdApi().validateOrCreate(user);
        User createdUser = new BisnodeIdApi().getUser(userName);
        assert createdUser != null;
    }
}


