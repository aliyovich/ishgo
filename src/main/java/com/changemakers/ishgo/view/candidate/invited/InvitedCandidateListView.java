package com.changemakers.ishgo.view.candidate.invited;

import com.changemakers.ishgo.entity.User;
import com.changemakers.ishgo.entity.candidate.InvitedCandidate;
import com.changemakers.ishgo.service.UserService;
import com.changemakers.ishgo.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "invitedCandidates", layout = MainView.class)
@ViewController("InvitedCandidate.list")
@ViewDescriptor("invited-candidate-list-view.xml")
@LookupComponent("invitedCandidatesDataGrid")
@DialogMode(width = "64em")
public class InvitedCandidateListView extends StandardListView<InvitedCandidate> {

    @Autowired
    private UserService userService;

    @ViewComponent
    private CollectionLoader<InvitedCandidate> invitedCandidatesDl;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        User user = userService.currentUser();
        if (user == null) return;

        invitedCandidatesDl.setParameter("company", user.getCompany());
        invitedCandidatesDl.load();
    }
}