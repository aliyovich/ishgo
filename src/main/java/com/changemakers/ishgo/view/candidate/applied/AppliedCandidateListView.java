package com.changemakers.ishgo.view.candidate.applied;

import com.changemakers.ishgo.entity.User;
import com.changemakers.ishgo.entity.candidate.AppliedCandidate;
import com.changemakers.ishgo.entity.candidate.ApplyStatus;
import com.changemakers.ishgo.service.UserService;
import com.changemakers.ishgo.view.main.MainView;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.router.Route;
import io.jmix.core.DataManager;
import io.jmix.core.Messages;
import io.jmix.flowui.Dialogs;
import io.jmix.flowui.action.DialogAction;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.tabsheet.JmixTabSheet;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.kit.action.ActionVariant;
import io.jmix.flowui.model.CollectionLoader;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;


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
    @Autowired
    private UserService userService;

    @ViewComponent
    private MessageBundle messageBundle;
    @ViewComponent
    private DataGrid<AppliedCandidate> appliedCandidatesDataGrid;
    @ViewComponent
    private CollectionLoader<AppliedCandidate> appliedCandidatesDl;

    @ViewComponent("tabSheet.rejectTab")
    private Tab tabSheetRejectTab;
    @ViewComponent("tabSheet.acceptTab")
    private Tab tabSheetAcceptTab;
    @ViewComponent("tabSheet.newTab")
    private Tab tabSheetNewTab;

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        User user = userService.currentUser();
        if (user == null) return;

        appliedCandidatesDl.setParameter("company", user.getCompany());
        appliedCandidatesDl.setParameter("status", ApplyStatus.NEW);
        appliedCandidatesDl.load();
    }

    @Subscribe("tabSheet")
    public void onTabSheetSelectedChange(final JmixTabSheet.SelectedChangeEvent event) {
        if (Objects.equals(event.getSelectedTab(), tabSheetRejectTab)) {
            appliedCandidatesDl.setParameter("status", ApplyStatus.REJECTED);
        } else if (Objects.equals(event.getSelectedTab(), tabSheetAcceptTab)) {
            appliedCandidatesDl.setParameter("status", ApplyStatus.ACCEPTED);
        } else if (Objects.equals(event.getSelectedTab(), tabSheetNewTab)) {
            appliedCandidatesDl.setParameter("status", ApplyStatus.NEW);
        } else {
            appliedCandidatesDl.removeParameter("status");
        }
        appliedCandidatesDl.load();
    }

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