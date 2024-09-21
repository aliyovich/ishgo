package com.changemakers.ishgo.view.ref.category;

import com.changemakers.ishgo.entity.vacancy.Category;
import com.changemakers.ishgo.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;

@Route(value = "categories/:id", layout = MainView.class)
@ViewController("Category.detail")
@ViewDescriptor("category-detail-view.xml")
@EditedEntityContainer("categoryDc")
@DialogMode(width = "40em", height = "AUTO")
public class CategoryDetailView extends StandardDetailView<Category> {
}