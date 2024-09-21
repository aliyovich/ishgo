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
@Table(name = "INVITED_CANDIDATE", indexes = {
        @Index(name = "IDX_INVITED_CANDIDATE_CANDIDATE", columnList = "CANDIDATE_ID"),
        @Index(name = "IDX_INVITED_CANDIDATE_VACANCY", columnList = "VACANCY_ID"),
        @Index(name = "IDX_INVITED_CANDIDATE_INVITER", columnList = "INVITER_ID")
})
@Entity
@Getter
@Setter
public class InvitedCandidate extends StandardEntity {

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "VACANCY_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Vacancy vacancy;

    @OnDeleteInverse(DeletePolicy.UNLINK)
    @JoinColumn(name = "CANDIDATE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private User candidate;

    @OnDeleteInverse(DeletePolicy.DENY)
    @JoinColumn(name = "INVITER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private User inviter;

    @Column(name = "INVITER_MESSAGE")
    @Lob
    private String inviterMessage;

    @Column(name = "STATUS")
    private String status;

    public ApplyStatus getStatus() {
        return status == null ? null : ApplyStatus.fromId(status);
    }

    public void setStatus(ApplyStatus status) {
        this.status = status == null ? null : status.getId();
    }
}