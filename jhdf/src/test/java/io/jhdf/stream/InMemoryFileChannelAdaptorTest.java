package io.jhdf.stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class InMemoryFileChannelAdaptorTest {

	private static final String HDF5_TEST_FILE_NAME = "test_file.hdf5";
	private static final String HDF5_TEST_FILE_PATH = "/hdf5/" + HDF5_TEST_FILE_NAME;

	private InMemoryFileChannelAdaptor inMemoryFileChannelAdaptor;

	@BeforeEach
	void setUp() throws IOException {
		InputStream inputStream = this.getClass().getResource(HDF5_TEST_FILE_PATH).openStream();
		inMemoryFileChannelAdaptor = new InMemoryFileChannelAdaptor(inputStream);
	}

	@Test
	void read() throws IOException {
		inMemoryFileChannelAdaptor.position();
//		assertThat(hdfFile.getUserBlockSize(), is(equalTo(0L)));
//		assertThat(hdfFile.getAddress(), is(equalTo(96L)));
	}

	@Test
	void testRead() {
	}

	@Test
	void write() {
	}

	@Test
	void testWrite() {
	}

	@Test
	void position() {
	}

	@Test
	void testPosition() {
	}

	@Test
	void size() {
	}

	@Test
	void truncate() {
	}

	@Test
	void force() {
	}

	@Test
	void transferTo() {
	}

	@Test
	void transferFrom() {
	}

	@Test
	void testRead1() {
	}

	@Test
	void testWrite1() {
	}

	@Test
	void map() {
	}

	@Test
	void lock() {
	}

	@Test
	void tryLock() {
	}

	@Test
	void implCloseChannel() {
	}
}