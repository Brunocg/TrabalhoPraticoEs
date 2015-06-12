package br.ufscar.util;

public class SimpleResponse {

	private boolean success;

	public SimpleResponse(boolean success) {
		this.setSuccess(success);
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
