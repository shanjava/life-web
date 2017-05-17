package entity;

import java.io.Serializable;

public class JsonEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String resultCode;
	private String message;
	private Object data;
	private String ext;

	public JsonEntity() {
		resultCode = "0";
		message = "";
		data = "";
		ext = "";
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public void setErrorCode(Code code){
		this.resultCode= code.getCode();
		this.message= code.getMessage();


	}


}
