package io.jhdf;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.io.IOException;
import java.net.URI;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SymbolTableEntryTest {
	private HdfFileChannel hdfFc;

	@BeforeEach
	public void setUp() throws Exception {
		final URI testFileUri = this.getClass().getResource("test_file.hdf5").toURI();
		FileChannel fc = FileChannel.open(Paths.get(testFileUri), StandardOpenOption.READ);
		Superblock sb = Superblock.readSuperblock(fc, 0);
		hdfFc = new HdfFileChannel(fc, sb);
	}

	@AfterEach
	public void after() throws IOException {
		hdfFc.close();
	}

	@Test
	public void testSymbolTableEntry() {
		SymbolTableEntry ste = new SymbolTableEntry(hdfFc, 56);
		assertThat(ste.getLinkNameOffset(), is(equalTo(0)));
		assertThat(ste.getObjectHeaderAddress(), is(equalTo(96L)));
		assertThat(ste.getCacheType(), is(equalTo(1)));
		assertThat(ste.getBTreeAddress(), is(equalTo(136L)));
		assertThat(ste.getNameHeapAddress(), is(equalTo(680L)));
		assertThat(ste.toString(), is(equalTo(
				"SymbolTableEntry [address=0x38, linkNameOffset=0, objectHeaderAddress=0x60, cacheType=1, bTreeAddress=0x88, nameHeapAddress=0x2a8, linkValueOffset=-1]")));
	}
}
