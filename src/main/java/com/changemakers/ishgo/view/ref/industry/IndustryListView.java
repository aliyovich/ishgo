package com.changemakers.ishgo.view.ref.industry;

import com.changemakers.ishgo.entity.company.Industry;
import com.changemakers.ishgo.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "industries", layout = MainView.class)
@ViewController("Industry.list")
@ViewDescriptor("industry-list-view.xml")
@LookupComponent("industriesDataGrid")
@DialogMode(width = "64em")
public class IndustryListView extends StandardListView<Industry> {
}