package de.aoksystems.idm.config.repository.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@EqualsAndHashCode
@Table(name = "CHARACTERTYPE")
public class Character {
	
	@Id
    private Long   id;
	
    private String charactername;
}
