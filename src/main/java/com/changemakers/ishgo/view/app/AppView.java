package com.changemakers.ishgo.view.app;


import com.changemakers.ishgo.$;
import com.changemakers.ishgo.entity.company.Company;
import com.changemakers.ishgo.entity.company.CompanyStatus;
import com.changemakers.ishgo.entity.company.Industry;
import com.changemakers.ishgo.entity.ref.Locale;
import com.changemakers.ishgo.entity.vacancy.Category;
import com.changemakers.ishgo.entity.vacancy.Vacancy;
import com.changemakers.ishgo.entity.vacancy.VacancyStatus;
import com.changemakers.ishgo.service.*;
import com.changemakers.ishgo.view.fragment.AppFragment;
import com.changemakers.ishgo.view.login.LoginView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import io.jmix.flowui.Fragments;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.ViewNavigators;
import io.jmix.flowui.component.select.JmixSelect;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "app")
@ViewController("AppView")
@ViewDescriptor("app-view.xml")
@AnonymousAllowed
public class AppView extends StandardView {
    private Vacancy selectedVacancy;

    @ViewComponent
    private JmixSelect<Object> localeField;
    @ViewComponent
    private HorizontalLayout vacancyTab;
    @ViewComponent
    private HorizontalLayout categoryTab;
    @ViewComponent
    private HorizontalLayout industryTab;
    @ViewComponent
    private HorizontalLayout companyTab;
    @ViewComponent
    private HorizontalLayout footer;
    @ViewComponent
    private VerticalLayout vacanciesBox;
    @ViewComponent
    private VerticalLayout vacancyBox;
    @ViewComponent
    private H2 vacancyPosition;
    @ViewComponent
    private Span vacancyCompany;
    @ViewComponent
    private Span vacancyIndustry;
    @ViewComponent
    private Span vacancyRegion;
    @ViewComponent
    private Span vacancyCategory;
    @ViewComponent
    private Div vacancyMain;
    @ViewComponent
    private JmixButton loginBtn;
    @ViewComponent
    private MessageBundle messageBundle;
    @ViewComponent
    private JmixButton applyBtn;

    @Autowired
    private UserService userService;
    @Autowired
    private VacancyService vacancyService;
    @Autowired
    private Fragments fragments;
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private ReferenceService referenceService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private ViewNavigators viewNavigators;
    @Autowired
    private CandidateService candidateService;
    @Autowired
    private Notifications notifications;

    @Subscribe
    public void onInit(final InitEvent event) {
        boolean anonymous = userService.isAnonymous();
        loginBtn.setVisible(anonymous);
        footer.setVisible(!anonymous);
        localeField.setValue(Locale.EN);
        applyBtn.setVisible(anonymous || userService.isJobSeeker());

        buildVacancies(vacancyService.getVacanciesByStatus(VacancyStatus.ACTIVE));
    }

    @Subscribe(id = "loginBtn", subject = "clickListener")
    public void onLoginBtnClick(final ClickEvent<JmixButton> event) {
        viewNavigators.view(this, LoginView.class).navigate();
    }

    @Subscribe(id = "applyBtn", subject = "clickListener")
    public void onApplyBtnClick(final ClickEvent<JmixButton> event) {
        if (userService.isAnonymous()) {
            viewNavigators.view(this, LoginView.class)
                    .withBackwardNavigation(true)
                    .navigate();
            return;
        }

        boolean applied = candidateService.applyVacancy(userService.currentUser(), selectedVacancy);
        if (applied) makeApplied();
        notifications.create(
                        messageBundle.getMessage(applied ? "successful" : "failure"),
                        messageBundle.getMessage(applied ? "applied.vacancy" : "contact.admin")
                )
                .withType(applied ? Notifications.Type.SUCCESS : Notifications.Type.ERROR)
                .withPosition(Notification.Position.MIDDLE)
                .show();
    }

    @Subscribe(id = "vacancyTab", subject = "clickListener")
    public void onVacancyTabClick(final ClickEvent<HorizontalLayout> event) {
        buildVacancies(vacancyService.getVacanciesByStatus(VacancyStatus.ACTIVE));
    }

    @Subscribe(id = "categoryTab", subject = "clickListener")
    public void onCategoryTabClick(final ClickEvent<HorizontalLayout> event) {
        buildCategories();
    }

    @Subscribe(id = "industryTab", subject = "clickListener")
    public void onIndustryTabClick(final ClickEvent<HorizontalLayout> event) {
        buildIndustries();
    }

    @Subscribe(id = "companyTab", subject = "clickListener")
    public void onCompanyTabClick(final ClickEvent<HorizontalLayout> event) {
        buildCompanies();
    }

    private void buildCompanies() {
        selectTab(companyTab);
        vacanciesBox.removeAll();

        for (Company company : companyService.getCompaniesByStatus(CompanyStatus.ACTIVE)) {
            AppFragment fragment = fragments.create(this, AppFragment.class);
            fragment.setName(company.getName());
            fragment.setLogo(company.getLogo());
            fragment.setAbout(company.getAbout());
            fragment.setDate(messageBundle.formatMessage("count.vacancies", vacancyService.countVacancies(company)));

            Div div = wrapDiv(fragment);
            div.addClickListener(event -> buildVacancies(vacancyService.getVacanciesByCompany(company)));
            vacanciesBox.add(div);
        }
    }

    private void buildIndustries() {
        selectTab(industryTab);
        vacanciesBox.removeAll();

        for (Industry industry : referenceService.getIndustries()) {
            AppFragment fragment = fragments.create(this, AppFragment.class);
            fragment.setName(industry.getName());
            fragment.setLogo(industry.getIcon());
            fragment.setAbout(industry.getDescription());
            fragment.setDate(messageBundle.formatMessage("count.vacancies", vacancyService.countVacancies(industry)));

            Div div = wrapDiv(fragment);
            div.addClickListener(event -> buildVacancies(vacancyService.getVacanciesByIndustry(industry)));
            vacanciesBox.add(div);
        }
    }

    private void buildCategories() {
        selectTab(categoryTab);
        vacanciesBox.removeAll();

        for (Category category : referenceService.getCategories()) {
            AppFragment fragment = fragments.create(this, AppFragment.class);
            fragment.setName(category.getName());
            fragment.setLogo(category.getIcon());
            fragment.setAbout(category.getDescription());
            fragment.setDate(messageBundle.formatMessage("count.vacancies", vacancyService.countVacancies(category)));

            Div div = wrapDiv(fragment);
            div.addClickListener(event -> buildVacancies(vacancyService.getVacanciesByCategory(category)));
            vacanciesBox.add(div);
        }
    }

    private void buildVacancies(List<Vacancy> vacancies) {
        selectTab(vacancyTab);
        vacanciesBox.removeAll();

        for (Vacancy vacancy : vacancies) {
            AppFragment fragment = fragments.create(this, AppFragment.class);
            fragment.setName(vacancy.getPosition());
            fragment.setDate($.dateFormat(vacancy.getCreatedDate(), "dd MMM yyyy"));
            if (vacancy.getCompany() != null) {
                fragment.setLogo(vacancy.getCompany().getLogo());
                fragment.setAbout(vacancy.getCompany().getName());
            }

            Div div = wrapDiv(fragment);
            div.addClickListener(event -> buildVacancyDetail(vacancy));
            vacanciesBox.add(div);
        }
    }

    private void buildVacancyDetail(Vacancy vacancy) {
        this.selectedVacancy = vacancy;
        vacancyBox.setVisible(true);
        vacancyMain.removeAll();

        vacancyPosition.setText(vacancy.getPosition());
        vacancyMain.add(new Html("<main>%s</main>".formatted(vacancy.getRequirement())));

        if (vacancy.getCompany() != null) {
            vacancyCompany.setText(vacancy.getCompany().getName());
            if (vacancy.getCompany().getIndustry() != null)
                vacancyIndustry.setText(vacancy.getCompany().getIndustry().getName());
        }
        if (vacancy.getRegion() != null)
            vacancyRegion.setText(vacancy.getRegion().getName());
        if (vacancy.getCategory() != null)
            vacancyCategory.setText(vacancy.getCategory().getName());

        if (candidateService.appliedVacancy(userService.currentUser(), vacancy))
            makeApplied();
    }

    private Div wrapDiv(AppFragment fragment) {
        Div div = uiComponents.create(Div.class);
        div.addClassName("vacancy");
        div.add(fragment);
        return div;
    }

    private void makeApplied() {
        applyBtn.setText(messageBundle.getMessage("applied"));
        applyBtn.setEnabled(false);
    }

    private void selectTab(HorizontalLayout tab) {
        vacancyTab.removeClassName("selected-tab");
        categoryTab.removeClassName("selected-tab");
        industryTab.removeClassName("selected-tab");
        companyTab.removeClassName("selected-tab");
        tab.addClassName("selected-tab");
    }
}