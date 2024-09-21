package com.changemakers.ishgo.service;


import com.changemakers.ishgo.config.RedisCacheNames;
import com.changemakers.ishgo.entity.company.Company;
import com.changemakers.ishgo.entity.company.Industry;
import com.changemakers.ishgo.entity.vacancy.Category;
import com.changemakers.ishgo.entity.vacancy.Vacancy;
import com.changemakers.ishgo.entity.vacancy.VacancyStatus;
import io.jmix.core.DataManager;
import io.jmix.core.security.Authenticated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: abdul
 * Since: 9/21/2024 1:20 PM
 */
@Service
public class VacancyService {

    @Autowired
    private DataManager dataManager;
    @Autowired
    private RedisCacheManager cacheManager;

    public void clearCache() {
        Cache vacancyCache = cacheManager.getCache(RedisCacheNames.VACANCY_CACHE);
        if (vacancyCache != null) vacancyCache.clear();
    }

    @Authenticated
    @CachePut(value = RedisCacheNames.VACANCY_CACHE, key = "#status.id")
    public List<Vacancy> getVacanciesByStatus(VacancyStatus status) {
        return dataManager.load(Vacancy.class)
                .query("select e from Vacancy e where e.status=:status order by e.createdDate desc")
                .parameter("status", status)
                .list();
    }

    @Authenticated
    @CachePut(value = RedisCacheNames.VACANCY_CACHE, key = "#category.id")
    public List<Vacancy> getVacanciesByCategory(Category category) {
        return dataManager.load(Vacancy.class)
                .query("select e from Vacancy e where e.status=:status and e.category=:category order by e.createdDate desc")
                .parameter("status", VacancyStatus.ACTIVE)
                .parameter("category", category)
                .list();
    }

    @Authenticated
    @CachePut(value = RedisCacheNames.VACANCY_CACHE, key = "#industry.id")
    public List<Vacancy> getVacanciesByIndustry(Industry industry) {
        return dataManager.load(Vacancy.class)
                .query("select e from Vacancy e where e.status=:status and e.company.industry=:industry order by e.createdDate desc")
                .parameter("status", VacancyStatus.ACTIVE)
                .parameter("industry", industry)
                .list();
    }

    @Authenticated
    @CachePut(value = RedisCacheNames.VACANCY_CACHE, key = "#company.id")
    public List<Vacancy> getVacanciesByCompany(Company company) {
        return dataManager.load(Vacancy.class)
                .query("select e from Vacancy e where e.status=:status and e.company=:company order by e.createdDate desc")
                .parameter("status", VacancyStatus.ACTIVE)
                .parameter("company", company)
                .list();
    }

    @Authenticated
    public Long countVacancies(Category category) {
        return dataManager.loadValue("select count(e) from Vacancy e where e.category=:category", Long.class)
                .parameter("category", category)
                .one();
    }

    @Authenticated
    public Long countVacancies(Industry industry) {
        return dataManager.loadValue("select count(e) from Vacancy e where e.company.industry=:industry", Long.class)
                .parameter("industry", industry)
                .one();
    }

    @Authenticated
    public Long countVacancies(Company company) {
        return dataManager.loadValue("select count(e) from Vacancy e where e.company=:company", Long.class)
                .parameter("company", company)
                .one();
    }
}
