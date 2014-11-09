package com.egurnee.matrixrelations;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MatrixFileReader {
	public static final String DEFAULT_FILENAME = "relations.txt";
	private final String filename;
	private Scanner input;
	private int matrixSize;
	private int numOfMatricies;
	private Matrix[] theMatricies;

	public MatrixFileReader() {
		this(MatrixFileReader.DEFAULT_FILENAME);
	}

	public MatrixFileReader(String filename) {
		this.filename = filename;
		this.init();
	}

	public Matrix getMatrix(int maxtrixPosition) {
		try {
			return this.theMatricies[maxtrixPosition];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}

	public boolean init() {
		try {
			this.input = new Scanner(new File(this.filename));

			this.numOfMatricies = this.input.nextInt();
			this.matrixSize = this.input.nextInt();

			this.theMatricies = new Matrix[this.numOfMatricies];
			for (int i = 0; i < this.theMatricies.length; i++) {
				this.theMatricies[i] = new Matrix(this.matrixSize);
			}

			for (int i = 0; i < this.numOfMatricies; i++) {
				boolean[][] matrixToApply = new boolean[this.matrixSize][this.matrixSize];
				for (int r = 0; r < this.matrixSize; r++) {
					for (int c = 0; c < this.matrixSize; c++) {
						matrixToApply[r][c] = this.input.nextInt() == 1;
					}
				}
				this.theMatricies[i].applyMatrix(matrixToApply);
			}
		} catch (FileNotFoundException e) {
			return false;
		}
		return true;
	}

}
