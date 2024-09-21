package com.changemakers.ishgo.service;


import com.changemakers.ishgo.entity.company.Company;
import com.changemakers.ishgo.entity.company.CompanyStatus;
import com.changemakers.ishgo.entity.company.Industry;
import com.changemakers.ishgo.entity.vacancy.Category;
import io.jmix.core.DataManager;
import io.jmix.core.Sort;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.core.security.Authenticated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: abdul
 * Since: 9/21/2024 8:46 PM
 */
@Service
public class ReferenceService {

    @Autowired
    private DataManager dataManager;

    @Authenticated
    public List<Category> getCategories() {
        return dataManager.load(Category.class)
                .condition(PropertyCondition.equal("active", true))
                .sort(Sort.by("name"))
                .list();
    }

    @Authenticated
    public List<Industry> getIndustries() {
        return dataManager.load(Industry.class)
                .condition(PropertyCondition.equal("active", true))
                .sort(Sort.by("name"))
                .list();
    }

    @Authenticated
    public List<Company> getCompanies() {
        return dataManager.load(Company.class)
                .condition(PropertyCondition.equal("status", CompanyStatus.ACTIVE))
                .sort(Sort.by("name"))
                .list();
    }
}
