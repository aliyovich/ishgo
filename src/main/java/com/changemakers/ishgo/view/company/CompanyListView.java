package com.changemakers.ishgo.view.company;

import com.changemakers.ishgo.entity.User;
import com.changemakers.ishgo.entity.UserType;
import com.changemakers.ishgo.entity.company.Company;
import com.changemakers.ishgo.service.UserService;
import com.changemakers.ishgo.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "companies", layout = MainView.class)
@ViewController("Company.list")
@ViewDescriptor("company-list-view.xml")
@LookupComponent("companiesDataGrid")
@DialogMode(width = "64em")
public class CompanyListView extends StandardListView<Company> {

    @Autowired
    private UserService userService;

    @ViewComponent
    private CollectionLoader<Company> companiesDl;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        User user = userService.currentUser();
        if (user == null || user.getType() != UserType.EMPLOYER) return;

        companiesDl.setParameter("company", user.getCompany());
        companiesDl.load();
    }
}