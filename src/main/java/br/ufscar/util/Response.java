package br.ufscar.util;

public class Response {

	private boolean success;
	private Object data;

	public Response(boolean success, Object data) {
		this.setSuccess(success);
		this.setData(data);
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
