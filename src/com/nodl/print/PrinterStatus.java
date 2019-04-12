package com.nodl.print;

public class PrinterStatus {
	private boolean bFeederWithoutPaper;
	private boolean bPaperMissing;
	private boolean bPaperLittle;
	private boolean bPaperBlock;
	
	public PrinterStatus() {
		setbFeederWithoutPaper(false);
		setbPaperMissing(false);
		setbPaperLittle(false);
		setbPaperBlock(false);
	}

	public boolean isFeederWithoutPaper() {
		return bFeederWithoutPaper;
	}

	public void setbFeederWithoutPaper(boolean bFeederWithoutPaper) {
		this.bFeederWithoutPaper = bFeederWithoutPaper;
	}

	public boolean isPaperMissing() {
		return bPaperMissing;
	}

	public void setbPaperMissing(boolean bPaperMissing) {
		this.bPaperMissing = bPaperMissing;
	}

	public boolean isPaperLittle() {
		return bPaperLittle;
	}

	public void setbPaperLittle(boolean bPaperLittle) {
		this.bPaperLittle = bPaperLittle;
	}

	public boolean isPaperBlock() {
		return bPaperBlock;
	}

	public void setbPaperBlock(boolean bPaperBlock) {
		this.bPaperBlock = bPaperBlock;
	}
}
