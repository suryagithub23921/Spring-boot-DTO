package de.aoksystems.idm.config.repository.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Entity
@DiscriminatorValue("FILE")
public class FileEntry extends Entry {
	
	@Column(name = "FILEEXTENSION")
    private String      extension;
    
	@Column(name = "FILELOCATION")
    private String      location;
   
    @OneToOne(cascade = CascadeType.ALL)
    private FileContent content;
}
