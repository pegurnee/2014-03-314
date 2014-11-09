package com.egurnee.matrixrelations;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

public class MatrixTest extends TestCase {
	private boolean[][] expectedMatrix;
	private final int middle = Matrix.DEFAULT_LIMIT / 2;
	private Matrix theMatrix;

	@Override
	@Before
	public void setUp() throws Exception {
		this.theMatrix = new Matrix();
		this.expectedMatrix = new boolean[8][8];
	}

	@Test
	public void testApplyMatrix() throws Exception {
		fail();
	}

	@Test
	public void testGetLimit() throws Exception {
		int expectedLimit = Matrix.DEFAULT_LIMIT;
		assertSame(expectedLimit, this.theMatrix.getLimit());
		expectedLimit = 10;
		this.theMatrix = new Matrix(expectedLimit);
		assertSame(expectedLimit, this.theMatrix.getLimit());
		expectedLimit = this.middle;
		this.theMatrix = new Matrix(expectedLimit);
		assertSame(expectedLimit, this.theMatrix.getLimit());
	}

	@Test
	public void testGettersSettersAndConstructors() throws Exception {
		this.theMatrix.setOne(0, 0);
		this.theMatrix.setZero(0, 1);
		assertTrue(this.theMatrix.get(0, 0));
		assertFalse(this.theMatrix.get(0, 1));
	}

	@Test
	public void testIsAntisymmetrical() throws Exception {
		assertTrue(this.theMatrix.isAntisymmetrical());
		this.expectedMatrix[0][1] = true;
		this.expectedMatrix[1][0] = true;
		this.theMatrix.applyMatrix(this.expectedMatrix);
		assertFalse(this.theMatrix.isAntisymmetrical());
		this.expectedMatrix[1][0] = false;
		this.theMatrix.applyMatrix(this.expectedMatrix);
		assertTrue(this.theMatrix.isAntisymmetrical());
		this.expectedMatrix[2][2] = true;
		this.theMatrix.applyMatrix(this.expectedMatrix);
		assertTrue(this.theMatrix.isAntisymmetrical());
	}

	@Test
	public void testIsReflexive() throws Exception {
		assertFalse(this.theMatrix.isReflexive());

		for (int i = 0; i < this.expectedMatrix.length; i++) {
			for (int j = 0; j < this.expectedMatrix[i].length; j++) {
				this.expectedMatrix[i][j] = true;
			}
		}
		this.theMatrix.applyMatrix(this.expectedMatrix);

		assertTrue(this.theMatrix.isReflexive());

		for (int i = 0; i < this.expectedMatrix.length; i++) {
			for (int j = 0; j < this.expectedMatrix[i].length; j++) {
				this.expectedMatrix[i][j] = false;
			}
		}

		this.theMatrix.applyMatrix(this.expectedMatrix);
		assertFalse(this.theMatrix.isReflexive());

		for (int i = 0; i < this.expectedMatrix.length; i++) {
			this.expectedMatrix[i][i] = true;
		}

		this.theMatrix.applyMatrix(this.expectedMatrix);
		assertTrue(this.theMatrix.isReflexive());
	}

	@Test
	public void testIsSymmetrical() throws Exception {
		assertTrue(this.theMatrix.isSymmetrical());
		this.expectedMatrix[0][1] = true;
		this.expectedMatrix[1][0] = true;
		this.theMatrix.applyMatrix(this.expectedMatrix);
		assertTrue(this.theMatrix.isSymmetrical());
		this.expectedMatrix[0][2] = true;
		this.theMatrix.applyMatrix(this.expectedMatrix);
		assertFalse(this.theMatrix.isSymmetrical());
		this.expectedMatrix[2][0] = true;
		this.theMatrix.applyMatrix(this.expectedMatrix);
		assertTrue(this.theMatrix.isSymmetrical());

	}

	@Test
	public void testMatrix() throws Exception {
		fail();
	}

	@Test
	public void testSetOne() throws Exception {
		assertTrue(this.theMatrix.setOne(0, 0));
		assertFalse(this.theMatrix.setOne(Matrix.DEFAULT_LIMIT + this.middle,
				this.middle));
		assertFalse(this.theMatrix.setOne(this.middle, Matrix.DEFAULT_LIMIT
				+ this.middle));
		assertTrue(this.theMatrix.setOne(this.middle, this.middle));
	}

	@Test
	public void testSetZero() throws Exception {
		assertTrue(this.theMatrix.setZero(0, 0));
		assertFalse(this.theMatrix.setZero(Matrix.DEFAULT_LIMIT + this.middle,
				this.middle));
		assertFalse(this.theMatrix.setZero(this.middle, Matrix.DEFAULT_LIMIT
				+ this.middle));
		assertTrue(this.theMatrix.setZero(this.middle, this.middle));
	}
}
