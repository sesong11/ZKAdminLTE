package com.sample.zkspring.entity.employment;

import lombok.Getter;
import lombok.Setter;

public enum HistoryType {
    JOIN(1, "Join"),
    ENDPROBATION(2, "End Probation"),
    DEMOTED(3, "Demoted"),
    PROMOTED(4, "Promoted"),
    RETIRED(5, "Retired");

    @Getter @Setter
    private String name;

    @Getter @Setter
    private int value;

    HistoryType(final int value, final String name) {
        this.value = value;
        this.name = name;
    }
}
