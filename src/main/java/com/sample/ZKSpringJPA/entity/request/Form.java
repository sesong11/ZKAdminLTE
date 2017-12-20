package com.sample.ZKSpringJPA.entity.request;

import com.sample.ZKSpringJPA.entity.employment.Branch;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@MappedSuperclass
public class Form implements RequestForm {

    @Getter
    @Setter
    @JoinColumn(name="request_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Request request;
}
