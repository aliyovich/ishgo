package com.changemakers.ishgo.view.ref.industry;

import com.changemakers.ishgo.entity.company.Industry;
import com.changemakers.ishgo.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "industries/:id", layout = MainView.class)
@ViewController("Industry.detail")
@ViewDescriptor("industry-detail-view.xml")
@EditedEntityContainer("industryDc")
@DialogMode(width = "40em", height = "AUTO")
public class IndustryDetailView extends StandardDetailView<Industry> {
}