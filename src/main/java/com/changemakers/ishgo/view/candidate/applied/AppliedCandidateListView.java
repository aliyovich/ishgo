package com.changemakers.ishgo.view.candidate.applied;

import com.changemakers.ishgo.entity.candidate.AppliedCandidate;
import com.changemakers.ishgo.entity.candidate.ApplyStatus;
import com.changemakers.ishgo.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.core.Messages;
import io.jmix.flowui.Dialogs;
import io.jmix.flowui.action.DialogAction;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.kit.action.ActionVariant;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "appliedCandidates", layout = MainView.class)
@ViewController("AppliedCandidate.list")
@ViewDescriptor("applied-candidate-list-view.xml")
@LookupComponent("appliedCandidatesDataGrid")
@DialogMode(width = "64em")
public class AppliedCandidateListView extends StandardListView<AppliedCandidate> {

    @Autowired
    private Dialogs dialogs;
    @Autowired
    private DataManager dataManager;
    @Autowired
    private Messages messages;

    @ViewComponent
    private MessageBundle messageBundle;
    @ViewComponent
    private DataGrid<AppliedCandidate> appliedCandidatesDataGrid;
    @ViewComponent
    private CollectionLoader<AppliedCandidate> appliedCandidatesDl;

    @Subscribe("appliedCandidatesDataGrid.reject")
    public void onAppliedCandidatesDataGridReject(final ActionPerformedEvent event) {
        changeStatus(ApplyStatus.REJECTED);
    }

    @Subscribe("appliedCandidatesDataGrid.accept")
    public void onAppliedCandidatesDataGridAccept(final ActionPerformedEvent event) {
        changeStatus(ApplyStatus.ACCEPTED);
    }

    private void changeStatus(ApplyStatus status) {
        AppliedCandidate appliedCandidate = appliedCandidatesDataGrid.getSingleSelectedItem();
        if (appliedCandidate == null) return;

        dialogs.createOptionDialog()
                .withHeader(messageBundle.getMessage("confirm"))
                .withText(confirmationStatus(status))
                .withActions(
                        new DialogAction(DialogAction.Type.NO),
                        new DialogAction(DialogAction.Type.YES)
                                .withVariant(ActionVariant.PRIMARY)
                                .withHandler(e -> {
                                    appliedCandidate.setStatus(status);
                                    dataManager.save(appliedCandidate);
                                    appliedCandidatesDl.load();
                                })
                )
                .open();
    }

    private String confirmationStatus(ApplyStatus status) {
        return switch (status) {
            case REJECTED -> messageBundle.getMessage("reject.candidate.confirm");
            case ACCEPTED -> messageBundle.getMessage("accept.candidate.confirm");
            default -> messages.getMessage(status);
        };
    }
}