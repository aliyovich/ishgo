package com.changemakers.ishgo.view.company;

import com.changemakers.ishgo.entity.User;
import com.changemakers.ishgo.entity.company.Company;
import com.changemakers.ishgo.service.UserService;
import com.changemakers.ishgo.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.combobox.EntityComboBox;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "companies/:id", layout = MainView.class)
@ViewController("Company.detail")
@ViewDescriptor("company-detail-view.xml")
@EditedEntityContainer("companyDc")
public class CompanyDetailView extends StandardDetailView<Company> {

    @Autowired
    private UserService userService;

    @ViewComponent
    private EntityComboBox<User> adminField;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        User user = userService.currentUser();
        adminField.setReadOnly(user.getType() != null);
    }
}