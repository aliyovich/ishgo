package com.changemakers.ishgo.view.vacancy;

import com.changemakers.ishgo.entity.User;
import com.changemakers.ishgo.entity.vacancy.Vacancy;
import com.changemakers.ishgo.service.UserService;
import com.changemakers.ishgo.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "vacancies/:id", layout = MainView.class)
@ViewController("Vacancy.detail")
@ViewDescriptor("vacancy-detail-view.xml")
@EditedEntityContainer("vacancyDc")
public class VacancyDetailView extends StandardDetailView<Vacancy> {

    @Autowired
    private UserService userService;

    @ViewComponent
    private CollectionLoader<User> responsibleDl;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        User user = userService.currentUser();
        getEditedEntity().setCompany(user.getCompany());
        responsibleDl.setParameter("company", user.getCompany());
        responsibleDl.load();
    }
}