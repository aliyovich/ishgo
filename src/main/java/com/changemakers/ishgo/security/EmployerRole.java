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
import io.jmix.core.entity.KeyValueEntity;
import io.jmix.flowui.entity.filter.*;
import io.jmix.flowuidata.entity.FilterConfiguration;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.security.role.annotation.SpecificPolicy;
import io.jmix.securitydata.entity.UserSubstitutionEntity;
import io.jmix.securityflowui.role.annotation.MenuPolicy;
import io.jmix.securityflowui.role.annotation.ViewPolicy;

@ResourceRole(name = "EmployerRole", code = EmployerRole.CODE)
public interface EmployerRole {
    String CODE = "employer-role";

    @MenuPolicy(menuIds = {"Vacancy.list", "AppView", "AppliedCandidate.list", "InvitedCandidate.list", "Company.list", "User.list"})
    @ViewPolicy(viewIds = {"Vacancy.list", "AppView", "AppFragment", "MainView", "Vacancy.detail", "flowui_AddConditionView", "flowui_GroupFilterCondition.detail", "flowui_JpqlFilterCondition.detail", "flowui_PropertyFilterCondition.detail", "inputDialog", "multiValueSelectDialog", "flowui_DateIntervalDialog", "headerPropertyFilterLayout", "AppliedCandidate.list", "InvitedCandidate.list", "Company.detail", "User.detail", "Company.list", "User.list"})
    void screens();

    @EntityAttributePolicy(entityClass = AppliedCandidate.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = AppliedCandidate.class, actions = EntityPolicyAction.ALL)
    void appliedCandidate();

    @EntityAttributePolicy(entityClass = Category.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Category.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE})
    void category();

    @EntityAttributePolicy(entityClass = Company.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Company.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE})
    void company();

    @EntityAttributePolicy(entityClass = Industry.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Industry.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE})
    void industry();

    @EntityAttributePolicy(entityClass = InvitedCandidate.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = InvitedCandidate.class, actions = EntityPolicyAction.ALL)
    void invitedCandidate();

    @EntityAttributePolicy(entityClass = ParentRef.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = ParentRef.class, actions = EntityPolicyAction.ALL)
    void parentRef();

    @EntityAttributePolicy(entityClass = Region.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = Region.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE})
    void region();

    @EntityAttributePolicy(entityClass = StandardEntity.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = StandardEntity.class, actions = EntityPolicyAction.ALL)
    void standardEntity();

    @EntityAttributePolicy(entityClass = User.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = User.class, actions = {EntityPolicyAction.READ, EntityPolicyAction.UPDATE})
    void user();

    @EntityAttributePolicy(entityClass = Vacancy.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Vacancy.class, actions = EntityPolicyAction.ALL)
    void vacancy();

    @EntityAttributePolicy(entityClass = AbstractSingleFilterCondition.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    void abstractSingleFilterCondition();

    @EntityAttributePolicy(entityClass = FilterCondition.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = FilterCondition.class, actions = EntityPolicyAction.ALL)
    void filterCondition();

    @EntityAttributePolicy(entityClass = FilterConfiguration.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = FilterConfiguration.class, actions = EntityPolicyAction.ALL)
    void filterConfiguration();

    @EntityAttributePolicy(entityClass = FilterValueComponent.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = FilterValueComponent.class, actions = EntityPolicyAction.ALL)
    void filterValueComponent();

    @EntityAttributePolicy(entityClass = GroupFilterCondition.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = GroupFilterCondition.class, actions = EntityPolicyAction.ALL)
    void groupFilterCondition();

    @EntityAttributePolicy(entityClass = HeaderFilterCondition.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = HeaderFilterCondition.class, actions = EntityPolicyAction.ALL)
    void headerFilterCondition();

    @EntityAttributePolicy(entityClass = JpqlFilterCondition.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = JpqlFilterCondition.class, actions = EntityPolicyAction.ALL)
    void jpqlFilterCondition();

    @EntityAttributePolicy(entityClass = KeyValueEntity.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = KeyValueEntity.class, actions = EntityPolicyAction.ALL)
    void keyValueEntity();

    @EntityAttributePolicy(entityClass = LogicalFilterCondition.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = LogicalFilterCondition.class, actions = EntityPolicyAction.ALL)
    void logicalFilterCondition();

    @EntityAttributePolicy(entityClass = PropertyFilterCondition.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = PropertyFilterCondition.class, actions = EntityPolicyAction.ALL)
    void propertyFilterCondition();

    @EntityAttributePolicy(entityClass = UserSubstitutionEntity.class, attributes = "*", action = EntityAttributePolicyAction.VIEW)
    @EntityPolicy(entityClass = UserSubstitutionEntity.class, actions = EntityPolicyAction.READ)
    void userSubstitutionEntity();

    @SpecificPolicy(resources = "*")
    void specific();
}