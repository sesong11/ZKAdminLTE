package com.sample.ZKSpringJPA.utils;

import lombok.Getter;

public class StandardFormat {

    @Getter
    private final static String standardDateFormat = "dd-MM-yyyy";

    @Getter
    private final static String standardDateTimeFormat = "dd-MM-yyyy HH:mm";

    @Getter final static String standardDoubleFormat = "#,##0.00";
}
