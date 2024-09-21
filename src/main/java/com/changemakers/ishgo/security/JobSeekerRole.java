package com.changemakers.ishgo.security;

import com.changemakers.ishgo.entity.User;
import com.changemakers.ishgo.entity.candidate.AppliedCandidate;
import com.changemakers.ishgo.entity.candidate.InvitedCandidate;
import com.changemakers.ishgo.entity.company.Company;
import com.changemakers.ishgo.entity.company.Industry;
import com.changemakers.ishgo.entity.core.StandardEntity;
import com.changemakers.ishgo.entity.ref.ParentRef;
import com.changemakers.ishgo.entity.ref.Region;
import com.changemakers.ishgo.entity.vacancy.Category;
import com.changemakers.ishgo.entity.vacancy.Vacancy;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.security.role.annotation.SpecificPolicy;
import io.jmix.securityflowui.role.annotation.MenuPolicy;
import io.jmix.securityflowui.role.annotation.ViewPolicy;

@ResourceRole(name = "JobSeekerRole", code = JobSeekerRole.CODE)
public interface JobSeekerRole {
    String CODE = "job-seeker-role";

    @MenuPolicy(menuIds = "AppView")
    @ViewPolicy(viewIds = {"AppView", "AppFragment", "MainView", "User.detail"})
    void screens();

    @EntityAttributePolicy(entityClass = AppliedCandidate.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = AppliedCandidate.class, actions = EntityPolicyAction.ALL)
    void appliedCandidate();

    @EntityAttributePolicy(entityClass = Category.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Category.class, actions = EntityPolicyAction.READ)
    void category();

    @EntityAttributePolicy(entityClass = Company.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Company.class, actions = EntityPolicyAction.READ)
    void company();

    @EntityAttributePolicy(entityClass = Industry.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Industry.class, actions = EntityPolicyAction.READ)
    void industry();

    @EntityAttributePolicy(entityClass = InvitedCandidate.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = InvitedCandidate.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE})
    void invitedCandidate();

    @EntityAttributePolicy(entityClass = ParentRef.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = ParentRef.class, actions = EntityPolicyAction.ALL)
    void parentRef();

    @EntityAttributePolicy(entityClass = Region.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Region.class, actions = EntityPolicyAction.READ)
    void region();

    @EntityAttributePolicy(entityClass = StandardEntity.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = StandardEntity.class, actions = EntityPolicyAction.ALL)
    void standardEntity();

    @EntityAttributePolicy(entityClass = User.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = User.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE})
    void user();

    @EntityAttributePolicy(entityClass = Vacancy.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Vacancy.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE})
    void vacancy();

    @SpecificPolicy(resources = "ui.loginToUi")
    void specific();
}