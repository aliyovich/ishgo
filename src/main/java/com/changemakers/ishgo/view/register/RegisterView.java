package com.changemakers.ishgo.view.register;


import com.changemakers.ishgo.$;
import com.changemakers.ishgo.entity.UserType;
import com.changemakers.ishgo.service.CompanyService;
import com.changemakers.ishgo.service.UserService;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import io.jmix.core.security.AccessDeniedException;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.radiobuttongroup.JmixRadioButtonGroup;
import io.jmix.flowui.component.select.JmixSelect;
import io.jmix.flowui.component.textfield.JmixPasswordField;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.kit.component.KeyCombination;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import io.jmix.securityflowui.authentication.AuthDetails;
import io.jmix.securityflowui.authentication.LoginViewSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;

@Route(value = "register")
@ViewController("RegisterView")
@ViewDescriptor("register-view.xml")
@AnonymousAllowed
@DialogMode(width = "AUTO", height = "AUTO", closeOnEsc = true, closeOnOutsideClick = true)
public class RegisterView extends StandardView {
    private static final Logger log = LoggerFactory.getLogger(RegisterView.class);

    @ViewComponent
    private MessageBundle messageBundle;
    @ViewComponent
    private JmixButton submitBtn;
    @ViewComponent
    private JmixRadioButtonGroup<UserType> userTypeField;
    @ViewComponent
    private TypedTextField<String> companyField;
    @ViewComponent
    private TypedTextField<String> usernameField;
    @ViewComponent
    private JmixPasswordField passwordField;

    @Autowired
    private Notifications notifications;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private UserService userService;
    @Autowired
    private LoginViewSupport loginViewSupport;

    @Subscribe
    public void onInit(final InitEvent event) {
        submitBtn.setShortcutCombination(KeyCombination.create(Key.ENTER));
    }

    @Subscribe("userTypeField")
    public void onUserTypeFieldComponentValueChange(final AbstractField.ComponentValueChangeEvent<JmixRadioButtonGroup<UserType>, UserType> event) {
        companyField.setVisible(userTypeField.getValue() == UserType.EMPLOYER);
    }

    @Subscribe(id = "submitBtn", subject = "clickListener")
    public void onSubmitBtnClick(final ClickEvent<JmixButton> event) {
        UserType userType = userTypeField.getValue();
        if (userType == null) {
            notifyError(messageBundle.getMessage("select.user.type"));
            return;
        }

        String username = usernameField.getValue();
        String password = passwordField.getValue();
        if ($.isEmpty(username) || $.isEmpty(password)) {
            notifyError(messageBundle.getMessage("pls.fill.form"));
            return;
        }

        String company = companyField.getValue();
        if (!switch (userType) {
            case EMPLOYER -> !$.isEmpty(company);
            case JOB_SEEKER -> true;
        }) {
            notifyError(messageBundle.getMessage("enter.company"));
            return;
        }

        if (companyService.existsCompany(company)) {
            notifyError(messageBundle.formatMessage("company.exist", company));
            return;
        }

        if (userService.existsUser(username)) {
            notifyError(messageBundle.formatMessage("user.exist", username));
            return;
        }

        userService.createUser(username, password, userType, company);
        login(username, password);
    }

    private void login(String username, String password) {
        try {
            loginViewSupport.authenticate(AuthDetails.of(username, password));
        } catch (final BadCredentialsException | DisabledException | LockedException | AccessDeniedException e) {
            log.warn("Login failed for user '{}': {}", username, e.toString());
        }
    }

    private void notifyError(String msg) {
        notifications.create(msg)
                .withType(Notifications.Type.ERROR)
                .withPosition(Notification.Position.TOP_CENTER)
                .show();
    }
}