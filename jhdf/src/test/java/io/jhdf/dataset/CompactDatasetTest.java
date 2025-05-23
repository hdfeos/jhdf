/*
 * This file is part of jHDF. A pure Java library for accessing HDF5 files.
 *
 * https://jhdf.io
 *
 * Copyright (c) 2025 James Mudd
 *
 * MIT License see 'LICENSE' file
 */
package io.jhdf.dataset;

import io.jhdf.HdfFile;
import io.jhdf.Utils;
import io.jhdf.api.Dataset;
import io.jhdf.exceptions.UnsupportedHdfException;
import io.jhdf.filter.PipelineFilterWithData;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.Executable;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static io.jhdf.TestUtils.loadTestHdfFile;
import static io.jhdf.Utils.flatten;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

class CompactDatasetTest {

	private static final String HDF5_TEST_EARLIEST_FILE_NAME = "test_compact_datasets_earliest.hdf5";
	private static final String HDF5_TEST_LATEST_FILE_NAME = "test_compact_datasets_latest.hdf5";

	private static HdfFile earliestHdfFile;
	private static HdfFile latestHdfFile;

	@BeforeAll
	static void setup() {
		earliestHdfFile = loadTestHdfFile(HDF5_TEST_EARLIEST_FILE_NAME);
		latestHdfFile = loadTestHdfFile(HDF5_TEST_LATEST_FILE_NAME);
	}

	@AfterAll
	static void tearDown() {
		earliestHdfFile.close();
		latestHdfFile.close();
	}

	@TestFactory
	Collection<DynamicNode> chunkedDatasetReadTests() {
		// List of all the datasetPaths
		return Arrays.asList(
			dynamicContainer(HDF5_TEST_EARLIEST_FILE_NAME, Arrays.asList(
				dynamicTest("float16", createTest(earliestHdfFile, "/float/float16")),
				dynamicTest("float32", createTest(earliestHdfFile, "/float/float32")),
				dynamicTest("float64", createTest(earliestHdfFile, "/float/float64")),
				dynamicTest("int8", createTest(earliestHdfFile, "/int/int8")),
				dynamicTest("int16", createTest(earliestHdfFile, "/int/int16")),
				dynamicTest("int32", createTest(earliestHdfFile, "/int/int32")))),

			dynamicContainer(HDF5_TEST_EARLIEST_FILE_NAME, Arrays.asList(
				dynamicTest("float16", createTest(latestHdfFile, "/float/float16")),
				dynamicTest("float32", createTest(latestHdfFile, "/float/float32")),
				dynamicTest("float64", createTest(latestHdfFile, "/float/float64")),
				dynamicTest("int8", createTest(latestHdfFile, "/int/int8")),
				dynamicTest("int16", createTest(latestHdfFile, "/int/int16")),
				dynamicTest("int32", createTest(latestHdfFile, "/int/int32"))))
		);
	}

	private Executable createTest(HdfFile hdfFile, String datasetPath) {
		return () -> {
			Dataset dataset = hdfFile.getDatasetByPath(datasetPath);

			assertThrows(UnsupportedHdfException.class, () -> dataset.getData(new long[]{0}, new int[]{3}));

			// Check filters
			List<PipelineFilterWithData> filters = dataset.getFilters();
			assertThat(filters, is(empty()));

			Object data = dataset.getData();
			assertThat(Utils.getDimensions(data), is(equalTo(new int[]{10})));
			Object[] flatData = flatten(data);
			for (int i = 0; i < flatData.length; i++) {
				// Do element comparison as there are all different primitive numeric types
				// convert to double
				assertThat(Double.valueOf(flatData[i].toString()), is(equalTo((double) i)));
			}
		};
	}


}
