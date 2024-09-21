package com.changemakers.ishgo.entity.vacancy;

import com.changemakers.ishgo.entity.User;
import com.changemakers.ishgo.entity.company.Company;
import com.changemakers.ishgo.entity.core.StandardEntity;
import com.changemakers.ishgo.entity.ref.Region;
import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@JmixEntity
@Table(name = "VACANCY", indexes = {
        @Index(name = "IDX_VACANCY_CATEGORY", columnList = "CATEGORY_ID"),
        @Index(name = "IDX_VACANCY_REGION", columnList = "REGION_ID"),
        @Index(name = "IDX_VACANCY_RESPONSIBLE", columnList = "RESPONSIBLE_ID"),
        @Index(name = "IDX_VACANCY_COMPANY", columnList = "COMPANY_ID")
})
@Entity
@Getter
@Setter
public class Vacancy extends StandardEntity {

    @Column(name = "CODE")
    private String code;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "COMPANY_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

    @InstanceName
    @Column(name = "POSITION_", length = 512)
    private String position;

    @Column(name = "REQUIREMENT")
    @Lob
    private String requirement;

    @JoinColumn(name = "CATEGORY_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    @JoinColumn(name = "REGION_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Region region;

    @JoinColumn(name = "RESPONSIBLE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private User responsible;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "EMPLOYMENT_TYPE")
    private String employmentType;

    @Column(name = "SALARY_FROM", precision = 25, scale = 2)
    private BigDecimal salaryFrom;

    @Column(name = "SALARY_TO", precision = 25, scale = 2)
    private BigDecimal salaryTo;

    public EmploymentType getEmploymentType() {
        return employmentType == null ? null : EmploymentType.fromId(employmentType);
    }

    public void setEmploymentType(EmploymentType employmentType) {
        this.employmentType = employmentType == null ? null : employmentType.getId();
    }

    public VacancyStatus getStatus() {
        return status == null ? null : VacancyStatus.fromId(status);
    }

    public void setStatus(VacancyStatus status) {
        this.status = status == null ? null : status.getId();
    }
}