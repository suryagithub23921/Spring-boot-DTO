package de.aoksystems.idm.config.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@EqualsAndHashCode
@Table(name = "FILECONTENT")
public class FileContent  {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_CONTENT")
    @SequenceGenerator(
            name = "SEQUENCE_CONTENT",
            sequenceName = "SQ_CONTENT_ID"
    )
	private Long id;
	
	@Column(name = "CONTENT")
    private byte[] binary;
    
	@Version
    private Long version;
}
