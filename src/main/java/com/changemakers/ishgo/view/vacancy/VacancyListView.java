package com.changemakers.ishgo.view.vacancy;

import com.changemakers.ishgo.entity.User;
import com.changemakers.ishgo.entity.vacancy.Vacancy;
import com.changemakers.ishgo.service.UserService;
import com.changemakers.ishgo.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "vacancies", layout = MainView.class)
@ViewController("Vacancy.list")
@ViewDescriptor("vacancy-list-view.xml")
@LookupComponent("vacanciesDataGrid")
@DialogMode(width = "64em")
public class VacancyListView extends StandardListView<Vacancy> {

    @Autowired
    private UserService userService;

    @ViewComponent
    private CollectionLoader<Vacancy> vacanciesDl;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        User user = userService.currentUser();
        if (user == null) return;

        vacanciesDl.setParameter("company", user.getCompany());
        vacanciesDl.load();
    }
}