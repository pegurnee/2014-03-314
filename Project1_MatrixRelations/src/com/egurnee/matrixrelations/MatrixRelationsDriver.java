package com.egurnee.matrixrelations;

public class MatrixRelationsDriver {

	public static void main(String[] args) {
		MatrixFileReader mfr = new MatrixFileReader();
		for (int i = 0; i < 6; i++) {
			Matrix matrix = mfr.getMatrix(i);
			System.out.println(matrix.getReport());
		}
	}
}