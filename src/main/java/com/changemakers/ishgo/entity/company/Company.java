package com.changemakers.ishgo.entity.company;

import com.changemakers.ishgo.entity.User;
import com.changemakers.ishgo.entity.ref.ParentRef;
import io.jmix.core.FileRef;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@JmixEntity
@Table(name = "COMPANY")
@Entity
@Getter
@Setter
public class Company extends ParentRef {

    @JoinColumn(name = "ADMIN_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private User admin;

    @JoinColumn(name = "INDUSTRY_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Industry industry;

    @Column(name = "ABOUT")
    @Lob
    private String about;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "LOGO", length = 1024)
    private FileRef logo;

    @Column(name = "STATUS")
    private String status;

    public CompanyStatus getStatus() {
        return status == null ? null : CompanyStatus.fromId(status);
    }

    public void setStatus(CompanyStatus status) {
        this.status = status == null ? null : status.getId();
    }
}