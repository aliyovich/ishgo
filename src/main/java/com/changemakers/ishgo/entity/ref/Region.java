package com.changemakers.ishgo.entity.ref;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.OnDelete;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JmixEntity
@Table(name = "REGION", indexes = {
        @Index(name = "IDX_REGION_PARENT", columnList = "PARENT_ID")
})
@Entity
@Getter
@Setter
public class Region extends ParentRef {

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @JoinColumn(name = "PARENT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Region parent;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OrderBy("sort")
    @OneToMany(mappedBy = "parent")
    private List<Region> children;
}