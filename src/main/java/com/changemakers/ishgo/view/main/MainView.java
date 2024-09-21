package com.changemakers.ishgo.view.main;

import com.changemakers.ishgo.$;
import com.changemakers.ishgo.entity.User;
import com.changemakers.ishgo.entity.UserType;
import com.changemakers.ishgo.entity.company.Company;
import com.changemakers.ishgo.service.UserService;
import com.changemakers.ishgo.view.app.AppView;
import com.changemakers.ishgo.view.company.CompanyDetailView;
import com.changemakers.ishgo.view.user.UserDetailView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.ViewNavigators;
import io.jmix.flowui.app.main.StandardMainView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@Route("")
@ViewController("MainView")
@ViewDescriptor("main-view.xml")
public class MainView extends StandardMainView {

    @Autowired
    private UserService userService;
    @Autowired
    private ViewNavigators viewNavigators;

    @Subscribe
    public void onInit(final InitEvent event) {
        User user = userService.currentUser();
        if (user == null || user.getType() == null) return;

        if ($.isEmpty(user.getFirstName()))
            viewNavigators.detailView(this, User.class)
                    .withViewClass(UserDetailView.class)
                    .editEntity(user)
                    .withBackwardNavigation(true)
                    .navigate();
        else if (user.getCompany() != null && user.getCompany().getIndustry() == null)
            viewNavigators.detailView(this, Company.class)
                    .withViewClass(CompanyDetailView.class)
                    .editEntity(user.getCompany())
                    .withBackwardNavigation(true)
                    .navigate();
        else if (user.getType() == UserType.JOB_SEEKER)
            UI.getCurrent().getPage().setLocation(AppView.class.getAnnotation(Route.class).value());
    }
}
