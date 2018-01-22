package com.sample.ZKSpringJPA.entity.request;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;

@MappedSuperclass
public class Form implements RequestForm {

    @Getter
    @Setter
    @JoinColumn(name="request_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    private Request request;
}
