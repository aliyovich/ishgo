package com.changemakers.ishgo.view.candidate.invited;

import com.changemakers.ishgo.entity.candidate.InvitedCandidate;
import com.changemakers.ishgo.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "invitedCandidates", layout = MainView.class)
@ViewController("InvitedCandidate.list")
@ViewDescriptor("invited-candidate-list-view.xml")
@LookupComponent("invitedCandidatesDataGrid")
@DialogMode(width = "64em")
public class InvitedCandidateListView extends StandardListView<InvitedCandidate> {
}