package com.changemakers.ishgo.service;


import com.changemakers.ishgo.entity.User;
import com.changemakers.ishgo.entity.UserType;
import com.changemakers.ishgo.entity.company.Company;
import com.changemakers.ishgo.entity.company.CompanyStatus;
import com.changemakers.ishgo.security.EmployerRole;
import com.changemakers.ishgo.security.JobSeekerRole;
import io.jmix.core.DataManager;
import io.jmix.core.SaveContext;
import io.jmix.core.security.Authenticated;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.security.role.assignment.RoleAssignmentRoleType;
import io.jmix.securitydata.entity.RoleAssignmentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Author: abdul
 * Since: 9/21/2024 1:17 PM
 */
@Service
public class UserService {

    @Autowired
    private DataManager dataManager;
    @Autowired
    private CurrentAuthentication currentAuthentication;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User currentUser() {
        return dataManager.load(User.class)
                .id(((User) currentAuthentication.getUser()).getId())
                .optional()
                .orElse(null);
    }

    public boolean isAnonymous() {
        return currentAuthentication.getUser().getUsername().equals("anonymous");
    }

    public boolean isJobSeeker() {
        User user = currentUser();
        if (user == null) return false;
        return user.getType() == UserType.JOB_SEEKER;
    }

    @Authenticated
    public boolean existsUser(String username) {
        return dataManager.loadValue("select count(e) from User e where lower(e.username)=:username", Long.class)
                .parameter("username", username.toLowerCase())
                .one() > 0;
    }

    @Authenticated
    public void createUser(String username, String password, UserType type, String companyName) {
        SaveContext context = new SaveContext();

        User user = dataManager.create(User.class);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setType(type);

        RoleAssignmentEntity role = dataManager.create(RoleAssignmentEntity.class);
        role.setUsername(user.getUsername());
        role.setRoleType(RoleAssignmentRoleType.RESOURCE);
        role.setRoleCode(type == UserType.EMPLOYER ? EmployerRole.CODE : JobSeekerRole.CODE);
        context.saving(role);

        if (type == UserType.EMPLOYER) {
            Company company = dataManager.create(Company.class);
            company.setStatus(CompanyStatus.ACTIVE);
            company.setName(companyName);
            company.setAdmin(user);
            user.setCompany(company);
            context.saving(company);
        }

        context.saving(user);
        dataManager.save(context);
    }
}
