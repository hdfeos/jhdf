/*
 * This file is part of jHDF. A pure Java library for accessing HDF5 files.
 *
 * http://jhdf.io
 *
 * Copyright (c) 2022 James Mudd
 *
 * MIT License see 'LICENSE' file
 */
package io.jhdf.dataset;

import io.jhdf.HdfFile;
import io.jhdf.api.Dataset;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.jhdf.TestUtils.getDimensions;
import static org.apache.commons.lang3.ArrayUtils.subarray;
import static org.apache.commons.lang3.ArrayUtils.toObject;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.is;

class SlicingTest {

	private static HdfFile hdfFile;

	@BeforeAll
	static void beforeAll() {
		hdfFile = HdfFile.fromInputStream(SlicingTest.class.getResourceAsStream("/hdf5/test_file2.hdf5"));
	}

	@Test
	void test3DSliceIntDataset() {
		Dataset dataset = hdfFile.getDatasetByPath("/nD_Datasets/3D_int32");

		int[][][] fullData = (int[][][]) dataset.getData();

		int[][][] slicedData = (int[][][]) dataset.getData(new long[]{0, 2, 30}, new int[]{1, 1, 15});
		assertThat(toObject(getDimensions(slicedData)), is(arrayContaining(1,1,15)));
		assertThat(toObject(slicedData[0][0]), is(subarray(fullData[0][2], 30, 30 + 15)));

		slicedData = (int[][][]) dataset.getData(new long[]{0, 2, 30}, new int[]{1, 3, 15});
		assertThat(toObject(getDimensions(slicedData)), is(arrayContaining(1,3,15)));
		assertThat(toObject(slicedData[0][0]), is(subarray(fullData[0][2], 30, 30 + 15)));
		assertThat(toObject(slicedData[0][1]), is(subarray(fullData[0][3], 30, 30 + 15)));
		assertThat(toObject(slicedData[0][2]), is(subarray(fullData[0][4], 30, 30 + 15)));

		slicedData = (int[][][]) dataset.getData(new long[]{0, 0, 0}, new int[]{2, 5, 10});
		assertThat(toObject(getDimensions(slicedData)), is(arrayContaining(2,5,10)));
		assertThat(toObject(slicedData[0][0]), is(subarray(fullData[0][0], 0, 10)));
		assertThat(toObject(slicedData[0][1]), is(subarray(fullData[0][1], 0, 10)));
		assertThat(toObject(slicedData[0][2]), is(subarray(fullData[0][2], 0, 10)));
		assertThat(toObject(slicedData[0][3]), is(subarray(fullData[0][3], 0, 10)));
		assertThat(toObject(slicedData[0][4]), is(subarray(fullData[0][4], 0, 10)));

		assertThat(toObject(slicedData[1][0]), is(subarray(fullData[1][0], 0, 10)));
		assertThat(toObject(slicedData[1][1]), is(subarray(fullData[1][1], 0, 10)));
		assertThat(toObject(slicedData[1][2]), is(subarray(fullData[1][2], 0, 10)));
		assertThat(toObject(slicedData[1][3]), is(subarray(fullData[1][3], 0, 10)));
		assertThat(toObject(slicedData[1][4]), is(subarray(fullData[1][4], 0, 10)));
	}

	@Test
	void test3DSliceFloatDataset() {
		Dataset dataset = hdfFile.getDatasetByPath("/nD_Datasets/3D_float32");

		float[][][] fullData = (float[][][]) dataset.getData();

		float[][][] slicedData = (float[][][]) dataset.getData(new long[]{0, 2, 17}, new int[]{1, 1, 6});
		assertThat(toObject(getDimensions(slicedData)), is(arrayContaining(1,1,6)));
		assertThat(toObject(slicedData[0][0]), is(subarray(fullData[0][2], 17, 17 + 6)));

		slicedData = (float[][][]) dataset.getData(new long[]{0, 2, 22}, new int[]{1, 3, 19});
		assertThat(toObject(getDimensions(slicedData)), is(arrayContaining(1,3,19)));
		assertThat(toObject(slicedData[0][0]), is(subarray(fullData[0][2], 22, 22 + 19)));
		assertThat(toObject(slicedData[0][1]), is(subarray(fullData[0][3], 22, 22 + 19)));
		assertThat(toObject(slicedData[0][2]), is(subarray(fullData[0][4], 22, 22 + 19)));

		slicedData = (float[][][]) dataset.getData(new long[]{0, 0, 0}, new int[]{2, 5, 10});
		assertThat(toObject(getDimensions(slicedData)), is(arrayContaining(2,5,10)));
		assertThat(toObject(slicedData[0][0]), is(subarray(fullData[0][0], 0, 10)));
		assertThat(toObject(slicedData[0][1]), is(subarray(fullData[0][1], 0, 10)));
		assertThat(toObject(slicedData[0][2]), is(subarray(fullData[0][2], 0, 10)));
		assertThat(toObject(slicedData[0][3]), is(subarray(fullData[0][3], 0, 10)));
		assertThat(toObject(slicedData[0][4]), is(subarray(fullData[0][4], 0, 10)));

		assertThat(toObject(slicedData[1][0]), is(subarray(fullData[1][0], 0, 10)));
		assertThat(toObject(slicedData[1][1]), is(subarray(fullData[1][1], 0, 10)));
		assertThat(toObject(slicedData[1][2]), is(subarray(fullData[1][2], 0, 10)));
		assertThat(toObject(slicedData[1][3]), is(subarray(fullData[1][3], 0, 10)));
		assertThat(toObject(slicedData[1][4]), is(subarray(fullData[1][4], 0, 10)));
	}
}
