package com.changemakers.ishgo.listener;

import com.changemakers.ishgo.entity.vacancy.Vacancy;
import com.changemakers.ishgo.service.VacancyService;
import io.jmix.core.event.EntityChangedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class VacancyEventListener {

    @Autowired
    private VacancyService vacancyService;

    @EventListener
    public void onVacancyChangedBeforeCommit(final EntityChangedEvent<Vacancy> event) {
        vacancyService.clearCache();
    }
}