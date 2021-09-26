package telemach.rest.controller;


public class Response {
	private Status status;
	private Object result;

	public enum Status {
		SUCCESS,
		FAIL;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "Response [status=" + status + ", result=" + result + "]";
	}

}
