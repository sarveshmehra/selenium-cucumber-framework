package com.bisnode.common.login.rest;

import com.bisnode.api.scim.Email;
import com.bisnode.api.scim.LinkedAccount;
import com.bisnode.api.scim.User;
import configs.AppUrls;
import org.apache.commons.lang.Validate;

import java.util.ArrayList;

public class BisnodeIdApi extends RestScimBase{

    public BisnodeIdApi() {
        super(AppUrls.BISNODEID_API_URL, "common-login-integration-test", "c9iD7NTUh0CjHj7s0gPe4gcQhr6601Kl9hU4GT9MreDgKkVogBR2qUgAUgcFaAv6", "bisnodeid");
    }

    public void createUser(boolean active, String userName, String password, String email){
        User user = new User();
        user.setActive(active);
        user.setUserName(userName);
        user.setPassword(password);
        user.setUserType("WEB");
        ArrayList<Email> emails = new ArrayList<>();
        Email primaryEmail = new Email();
        primaryEmail.setValue(email);
        primaryEmail.setPrimary(true);
        emails.add(primaryEmail);
        user.setEmails(emails);

        create(user);
    }

    public void validateOrCreate(User user){
        if(user.getLinkedAccounts() != null){
            for(LinkedAccount account : user.getLinkedAccounts() ){
                deleteBySystemId(account.getValue(), account.getDomain());
            }
        }

        filterBy(user.getUserName()).forEach(this::delete);
        create(user);
    }

    private void deleteBySystemId(String id, String system) {
        // TODO: Should search by system and id instead
        // TODO: Disabling for now, takes much too long time to fetch ALL users
        //for (User user : listAllUsers()){
        //    if (hasLegacyId(user, id, system)){
        //        delete(user);
        //    }
        //}
    }

    public void deleteUser(String username) {
        for (User user : new BisnodeIdApi().filterBy(username)) {
            if (user.getUserName().equals(username)) {
                delete(user);
            }
        }
    }

    /**
     * Look for an existing legacy id
     * @param user - the user to check if it has the id
     * @param legacyId - the id to look for
     * @param system - the system to look in
     * @return true if it exists, false if it doesn't
     */
    private boolean hasLegacyId(User user, String legacyId, String system) {
        Validate.notNull(legacyId);
        if (user.getLinkedAccounts() == null){
            return false;
        }

        for(LinkedAccount account : user.getLinkedAccounts() ){
            if(system.equals(account.getDomain()) && legacyId.equals(account.getValue())){
                return true;
            }
        }
        return false;
    }
}
