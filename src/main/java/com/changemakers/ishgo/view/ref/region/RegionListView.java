package com.changemakers.ishgo.view.ref.region;

import com.changemakers.ishgo.entity.ref.Region;

import com.changemakers.ishgo.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "regions", layout = MainView.class)
@ViewController("Region.list")
@ViewDescriptor("region-list-view.xml")
@LookupComponent("regionsDataGrid")
@DialogMode(width = "64em")
public class RegionListView extends StandardListView<Region> {
}