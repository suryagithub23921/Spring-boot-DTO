package de.aoksystems.idm.config.repository.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("SIMPLE")
public class SimpleEntry extends Entry {
	
	@Column(name = "SIMPLEVALUE")
    private String value;
}
