package com.changemakers.ishgo.view.ref.category;

import com.changemakers.ishgo.entity.vacancy.Category;
import com.changemakers.ishgo.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "categories", layout = MainView.class)
@ViewController("Category.list")
@ViewDescriptor("category-list-view.xml")
@LookupComponent("categoriesDataGrid")
@DialogMode(width = "64em")
public class CategoryListView extends StandardListView<Category> {
}