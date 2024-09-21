package com.changemakers.ishgo.entity.company;

import com.changemakers.ishgo.entity.ref.ParentRef;
import io.jmix.core.FileRef;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@JmixEntity
@Table(name = "INDUSTRY")
@Entity
@Getter
@Setter
public class Industry extends ParentRef {

    @Column(name = "DESCRIPTION")
    @Lob
    private String description;

    @Column(name = "ICON", length = 1024)
    private FileRef icon;
}