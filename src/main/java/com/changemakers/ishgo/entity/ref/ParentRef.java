package com.changemakers.ishgo.entity.ref;

import com.changemakers.ishgo.entity.core.HasName;
import com.changemakers.ishgo.entity.core.StandardEntity;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@JmixEntity
@MappedSuperclass
@Getter
@Setter
public abstract class ParentRef extends StandardEntity implements HasName, HasName.HasShortName {

    @InstanceName
    @Column(name = "NAME", length = 512)
    private String name;

    @Column(name = "SHORT_NAME")
    private String shortName;

    @Column(name = "SORT")
    private Integer sort;

    @Column(name = "ACTIVE")
    private Boolean active = Boolean.TRUE;

    public String getShortName() {
        if (shortName == null && name != null) {
            shortName = StringUtils.left(name, 255);
        }
        return shortName;
    }
}