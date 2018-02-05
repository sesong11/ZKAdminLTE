package com.sample.zkspring.utils;

import lombok.Getter;

public final class StandardFormat {

	private StandardFormat() {}
	
    @Getter
    private static final String standardDateFormat = "dd-MM-yyyy";

    @Getter static final String standardDoubleFormat = "#,##0.00";
}
