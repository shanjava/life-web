package service;


import entity.Code;
import entity.JsonEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseService {
	
	protected JsonEntity json = new JsonEntity();

	protected Map<Object, Object> data = new HashMap<Object, Object>();


	public JsonEntity ErrorCode(Code code) {
		json.setErrorCode(code);
		return json;

	}


	public JsonEntity booleanToJson(boolean status) {
		if (status) {
			json.setResultCode("0");
			json.setMessage("操作成功");
		} else {
			json.setResultCode("1");
			json.setMessage("操作失败");
		}
		return json;
	}

	public JsonEntity booleanToJson(boolean status, String message) {
		if (status) {
			json.setResultCode("0");
			json.setMessage(message);
		} else {
			json.setResultCode("1");
			json.setMessage(message);
		}
		return json;
	}

	public <T> JsonEntity listToJson(List<T> dataList) {
		if (dataList != null && dataList.size() > 0) {
			json.setResultCode("0");
			json.setMessage("成功查询到列表信息");
			json.setData(dataList);
		} else {
			json.setResultCode("1");
			json.setMessage("列表为空");
		}
		return json;
	}

	public JsonEntity objectToJson(Object object) {
		if (object != null) {
			json.setResultCode("0");
			json.setMessage("成功查询到信息");
			json.setData(object);
		} else {
			json.setResultCode("1");
			json.setMessage("信息为空");
		}
		return json;
	}

}
