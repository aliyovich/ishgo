package com.changemakers.ishgo.entity.candidate;

import com.changemakers.ishgo.entity.User;
import com.changemakers.ishgo.entity.core.StandardEntity;
import com.changemakers.ishgo.entity.vacancy.Vacancy;
import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@JmixEntity
@Table(name = "APPLIED_CANDIDATE", indexes = {
        @Index(name = "IDX_APPLIED_CANDIDATE_CANDIDATE", columnList = "CANDIDATE_ID"),
        @Index(name = "IDX_APPLIED_CANDIDATE_VACANCY", columnList = "VACANCY_ID")
})
@Entity
@Getter
@Setter
public class AppliedCandidate extends StandardEntity {

    @OnDeleteInverse(DeletePolicy.UNLINK)
    @JoinColumn(name = "CANDIDATE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private User candidate;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "VACANCY_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Vacancy vacancy;

    @Column(name = "STATUS")
    private String status;

    public ApplyStatus getStatus() {
        return status == null ? null : ApplyStatus.fromId(status);
    }

    public void setStatus(ApplyStatus status) {
        this.status = status == null ? null : status.getId();
    }
}