package stepDefinitions;

import com.company.api.scim.User;
import com.company.common.login.rest.companyIdApi;
import com.company.login.simplescimclient.testing.UserBuilder;
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

        User existingUser = new companyIdApi().getUser(userName);
        if (existingUser != null)
            new companyIdApi().delete(existingUser);
        existingUser = new companyIdApi().getUser(userName);
        assert existingUser == null;

        User user = UserBuilder.user()
                .withUserName(userName)
                .withPassword("Secret123")
                .withPrimaryPhoneNumber("+672351111")
                .withUserType("WEB")
                .withExternalId("demo_id", userName.replace("@company.com", ""))
                .thatIsActive()
                .build();

        user.setId(userId);
        new companyIdApi().validateOrCreate(user);
        User createdUser = new companyIdApi().getUser(userName);
        assert createdUser != null;
    }
}


