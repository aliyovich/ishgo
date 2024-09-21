package com.changemakers.ishgo.view.user;

import com.changemakers.ishgo.entity.User;
import com.changemakers.ishgo.entity.UserType;
import com.changemakers.ishgo.service.UserService;
import com.changemakers.ishgo.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "users", layout = MainView.class)
@ViewController("User.list")
@ViewDescriptor("user-list-view.xml")
@LookupComponent("usersDataGrid")
@DialogMode(width = "64em")
public class UserListView extends StandardListView<User> {

    @Autowired
    private UserService userService;

    @ViewComponent
    private CollectionLoader<User> usersDl;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        User user = userService.currentUser();
        if (user == null || user.getType() != UserType.EMPLOYER) return;

        usersDl.setParameter("company", user.getCompany());
        usersDl.load();
    }
}