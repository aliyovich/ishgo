package com.changemakers.ishgo.service;


import com.changemakers.ishgo.entity.User;
import com.changemakers.ishgo.entity.candidate.AppliedCandidate;
import com.changemakers.ishgo.entity.candidate.ApplyStatus;
import com.changemakers.ishgo.entity.vacancy.Vacancy;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: abdul
 * Since: 9/21/2024 9:25 PM
 */
@Service
public class CandidateService {

    @Autowired
    private DataManager dataManager;

    public boolean applyVacancy(User candidate, Vacancy vacancy) {
        if (candidate == null || vacancy == null) return false;
        if (appliedVacancy(candidate, vacancy)) return true;

        AppliedCandidate appliedCandidate = dataManager.create(AppliedCandidate.class);
        appliedCandidate.setStatus(ApplyStatus.NEW);
        appliedCandidate.setCandidate(candidate);
        appliedCandidate.setVacancy(vacancy);
        dataManager.save(appliedCandidate);
        return true;
    }

    public boolean appliedVacancy(User candidate, Vacancy vacancy) {
        if (candidate == null || vacancy == null) return false;
        return dataManager.loadValue("""
                            select count(e) from AppliedCandidate e where e.candidate=:candidate and e.vacancy=:vacancy
                        """, Long.class)
                .parameter("candidate", candidate)
                .parameter("vacancy", vacancy)
                .one() > 0;
    }
}
