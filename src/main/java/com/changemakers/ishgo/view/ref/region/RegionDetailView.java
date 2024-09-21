package com.changemakers.ishgo.view.ref.region;

import com.changemakers.ishgo.entity.ref.Region;
import com.changemakers.ishgo.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "regions/:id", layout = MainView.class)
@ViewController("Region.detail")
@ViewDescriptor("region-detail-view.xml")
@EditedEntityContainer("regionDc")
@DialogMode(width = "40em", height = "AUTO")
public class RegionDetailView extends StandardDetailView<Region> {
}