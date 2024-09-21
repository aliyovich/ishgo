package com.changemakers.ishgo.service;


import com.changemakers.ishgo.entity.company.Company;
import com.changemakers.ishgo.entity.company.CompanyStatus;
import io.jmix.core.DataManager;
import io.jmix.core.security.Authenticated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: abdul
 * Since: 9/21/2024 6:02 PM
 */
@Service
public class CompanyService {

    @Autowired
    private DataManager dataManager;

    @Authenticated
    public boolean existsCompany(String name) {
        return dataManager.loadValue("select count(e) from Company e where lower(e.name)=:name", Long.class)
                .parameter("name", name.toLowerCase())
                .one() > 0;
    }

    @Authenticated
    public List<Company> getCompaniesByStatus(CompanyStatus status) {
        return dataManager.load(Company.class)
                .query("select e from Company e where e.status=:status order by e.name")
                .parameter("status", status)
                .list();
    }
}
