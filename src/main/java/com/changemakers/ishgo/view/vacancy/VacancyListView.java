package com.changemakers.ishgo.view.vacancy;

import com.changemakers.ishgo.entity.vacancy.Vacancy;
import com.changemakers.ishgo.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "vacancies", layout = MainView.class)
@ViewController("Vacancy.list")
@ViewDescriptor("vacancy-list-view.xml")
@LookupComponent("vacanciesDataGrid")
@DialogMode(width = "64em")
public class VacancyListView extends StandardListView<Vacancy> {
}