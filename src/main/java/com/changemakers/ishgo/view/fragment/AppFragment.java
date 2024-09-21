package com.changemakers.ishgo.view.fragment;

import com.changemakers.ishgo.$;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.server.StreamResource;
import io.jmix.core.FileRef;
import io.jmix.core.FileStorage;
import io.jmix.flowui.component.image.JmixImage;
import io.jmix.flowui.fragment.Fragment;
import io.jmix.flowui.fragment.FragmentDescriptor;
import io.jmix.flowui.view.ViewComponent;
import org.springframework.beans.factory.annotation.Autowired;

@FragmentDescriptor("app-fragment.xml")
public class AppFragment extends Fragment<HorizontalLayout> {
    private static final String EMPTY = "--------";

    @Autowired
    private FileStorage fileStorage;

    @ViewComponent
    private JmixImage<FileRef> logo;
    @ViewComponent
    private Span name;
    @ViewComponent
    private Span about;
    @ViewComponent
    private Span date;

    public void setLogo(FileRef logo) {
        if (logo == null) return;
        this.logo.setSrc(new StreamResource(logo.getFileName(), () -> fileStorage.openStream(logo)));
    }

    public void setName(String name) {
        this.name.setText($.isEmpty(name) ? EMPTY : name);
    }

    public void setAbout(String about) {
        this.about.setText($.isEmpty(about) ? EMPTY : about);
    }

    public void setDate(String date) {
        this.date.setText(date);
    }
}