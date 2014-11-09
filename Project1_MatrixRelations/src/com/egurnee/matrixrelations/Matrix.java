package com.egurnee.matrixrelations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Matrix {
	public static final int DEFAULT_LIMIT = 8;
	private ArrayList<ArrayList<Integer>> equivalenceClasses;
	private final int limit;
	private final Matrix[] matrixMultiples;
	private boolean[][] theMatrix;
	private Matrix transitiveClosure;

	public Matrix() {
		this(Matrix.DEFAULT_LIMIT);
	}

	public Matrix(int limit) {
		this.limit = limit;
		this.theMatrix = new boolean[this.limit][this.limit];
		this.matrixMultiples = new Matrix[this.limit];
		this.transitiveClosure = null;
	}

	public void applyMatrix(boolean[][] theMatrix) {
		this.theMatrix = new boolean[theMatrix.length][];
		for (int r = 0; r < this.theMatrix.length; r++) {
			this.theMatrix[r] = new boolean[theMatrix[r].length];
			for (int c = 0; c < this.theMatrix[r].length; c++) {
				this.theMatrix[r][c] = theMatrix[r][c];
			}
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		Matrix other = (Matrix) obj;
		if (this.limit != other.limit) {
			return false;
		}
		if (!Arrays.deepEquals(this.theMatrix, other.theMatrix)) {
			return false;
		}
		return true;
	}

	public boolean get(int r, int c) {
		return this.theMatrix[r][c];
	}

	public int getLimit() {
		return this.limit;
	}

	public String getReport() {
		String toReturn = "";
		toReturn += "The relation with maxtrix: \n";
		toReturn += this.toString();
		toReturn += "\nis " + (this.isReflexive() ? "" : "not ") + "reflexive,";
		toReturn += "\nis " + (this.isSymmetrical() ? "" : "not ")
				+ "symmetric,";
		toReturn += "\nis " + (this.isTransitive() ? "" : "not ")
				+ "transitive,";
		toReturn += "\nis " + (this.isAntisymmetrical() ? "" : "not ")
				+ "antisymmetric,";
		toReturn += "\nis " + (this.isEquivalenceRelation() ? "" : "not ")
				+ "an equivalence relation";
		if (this.isEquivalenceRelation()) {
			toReturn += " with the equivalence classes:\n";
			for (ArrayList<Integer> equivalenceClass : this.equivalenceClasses) {
				String classStr = "{";
				Iterator<Integer> i = equivalenceClass.iterator();
				classStr += i.next();
				while (i.hasNext()) {
					classStr += ", " + i.next();
				}
				classStr += "} ";
				toReturn += classStr;
			}
		}
		if (!this.isTransitive()) {
			toReturn += "\nThe matrix of its transitive closure is:\n";
			toReturn += this.getTransitiveClosure();
		}
		return toReturn + "\n";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + this.limit;
		result = (prime * result) + Arrays.hashCode(this.theMatrix);
		return result;
	}

	public boolean isAntisymmetrical() {
		for (int r = 0; r < this.theMatrix.length; r++) {
			for (int c = 0; c < this.theMatrix[r].length; c++) {
				if (this.symmetricalPairsAreBothOnes(r, c)) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean isEquivalenceRelation() {
		if (this.isReflexive() && this.isSymmetrical() && this.isTransitive()) {
			this.getEquivalenceClasses();
			return true;
		} else {
			return false;
		}
	}

	public boolean isReflexive() {
		for (int i = 0; i < this.theMatrix.length; i++) {
			if (!this.theMatrix[i][i]) {
				return false;
			}
		}
		return true;
	}

	public boolean isSymmetrical() {
		for (int r = 0; r < this.theMatrix.length; r++) {
			for (int c = r + 1; c < this.theMatrix[r].length; c++) {
				if (this.symmetricalPairsAreNotEqual(r, c)) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean isTransitive() {
		return this.getTransitiveClosure().equals(this);
	}

	public boolean setOne(int r, int c) {
		return this.set(r, c, true);
	}

	public boolean setZero(int r, int c) {
		return this.set(r, c, false);
	}

	@Override
	public String toString() {
		String toReturn = "";
		for (int r = 0; r < this.theMatrix.length; r++) {
			for (int c = 0; c < this.theMatrix[r].length; c++) {
				toReturn += (this.theMatrix[r][c] ? "1" : "0") + " ";
			}
			toReturn += "\n";
		}
		return toReturn;
	}

	private void buildToThePowerOfArray(int power) {
		this.matrixMultiples[0] = new Matrix(this.limit);
		this.matrixMultiples[0].applyMatrix(this.theMatrix);

		for (int i = 1; i < power; i++) {
			this.matrixMultiples[i] = this.matrixMultiply(
					this.matrixMultiples[i - 1], this);
		}
	}

	private boolean findMatrixMultiple(Matrix firstMatrix, Matrix secondMatrix,
			int r, int c) {
		for (int i = 0; i < this.limit; i++) {
			if (firstMatrix.get(r, i) && secondMatrix.get(i, c)) {
				return true;
			}
		}
		return false;
	}

	private void getEquivalenceClasses() {
		this.equivalenceClasses = new ArrayList<>();
		boolean[] usedNums = new boolean[this.limit];
		for (int i = 0; i < usedNums.length; i++) {
			if (!usedNums[i]) {
				usedNums[i] = true;
				ArrayList<Integer> equivalenceClass = new ArrayList<>();
				equivalenceClass.add(i + 1);
				for (int c = 0; c < usedNums.length; c++) {
					if ((i != c) && this.theMatrix[i][c]) {
						usedNums[c] = true;
						equivalenceClass.add(c + 1);
					}
				}
				this.equivalenceClasses.add(equivalenceClass);
			}
		}
	}

	private Matrix getTransitiveClosure() {
		if (this.transitiveClosure == null) {
			this.buildToThePowerOfArray(this.limit);
			Matrix toReturn = new Matrix(this.limit);
			for (int i = 0; i < this.matrixMultiples.length; i++) {
				toReturn = this.matrixSum(toReturn, this.matrixMultiples[i]);
			}
			this.transitiveClosure = toReturn;
			return toReturn;
		} else {
			return this.transitiveClosure;
		}
	}

	private Matrix matrixMultiply(Matrix firstMatrix, Matrix secondMatrix) {
		boolean[][] matrixToApply = new boolean[this.limit][this.limit];
		for (int r = 0; r < matrixToApply.length; r++) {
			for (int c = 0; c < matrixToApply[r].length; c++) {
				matrixToApply[r][c] = this.findMatrixMultiple(firstMatrix,
						secondMatrix, r, c);
			}
		}
		Matrix toReturn = new Matrix(this.limit);
		toReturn.applyMatrix(matrixToApply);
		return toReturn;
	}

	private Matrix matrixSum(Matrix... matricies) {
		boolean[][] toReturn = new boolean[this.limit][this.limit];
		for (int i = 0; i < matricies.length; i++) {
			for (int r = 0; r < this.theMatrix.length; r++) {
				for (int c = 0; c < this.theMatrix[r].length; c++) {
					toReturn[r][c] = toReturn[r][c] || matricies[i].get(r, c);
				}
			}
		}
		Matrix matrix = new Matrix(this.limit);
		matrix.applyMatrix(toReturn);
		return matrix;
	}

	private boolean set(int r, int c, boolean isOne) {
		try {
			this.theMatrix[r][c] = isOne;
			return true;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	private boolean symmetricalPairsAreBothOnes(int r, int c) {
		return (r != c) && this.theMatrix[r][c] && this.theMatrix[c][r];
	}

	private boolean symmetricalPairsAreNotEqual(int r, int c) {
		return this.theMatrix[r][c] != this.theMatrix[c][r];
	}
}
