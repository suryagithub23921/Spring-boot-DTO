package de.aoksystems.idm.config.repository.entity;

import org.junit.jupiter.api.Test;

import de.aoksystems.idm.techbase.testing.EqualsVerifierUtil;

public class EqualEntityUnitTest {
	
	@Test
	public void testEqualsForFileEntry() {
		EqualsVerifierUtil.verify(FileEntry.class);
	}
	
	@Test
	public void testEqualsForSimpleEntry() {
		EqualsVerifierUtil.verify(SimpleEntry.class);
	}
}
