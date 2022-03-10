package de.aoksystems.idm.config.repository.entity;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode
@Entity
@Table(name = "ENTRY")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DISCRIMINATOR", discriminatorType = DiscriminatorType.STRING)
public abstract class Entry {
	
	@ToString.Include
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_ENTRY")
    @SequenceGenerator(
            name = "SEQUENCE_ENTRY",
            sequenceName = "SQ_ENTRY_ID"
    )
	private Long        id;
	
	@ToString.Include
    private String      key;
    
	private Boolean     valid;
    
	@ManyToOne
    @JoinColumn(name = "CHARTYPE_ID")
    private Character   character;
    
	@ManyToOne
    private Client      client;
    
	@Version
	private Long        version;
}
