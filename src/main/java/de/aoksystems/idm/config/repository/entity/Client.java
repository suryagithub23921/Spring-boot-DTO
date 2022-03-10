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
@Table(name = "CLIENT")
public class Client {
	
	@Id
    private Long   id;
	
    private String name;
}
