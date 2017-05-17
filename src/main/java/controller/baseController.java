package controller;

import com.jfinal.core.Controller;
import entity.Code;
import entity.JsonEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mac on 2017/5/11.
 */
public class baseController extends Controller {

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

    protected String getControllerKey() {
        return this.getAttr("ControllerKey");
    }

    protected boolean isPost() {
        return "post".equals(getRequest().getMethod().toLowerCase());
    }

    protected void goBack() {
        goBack(-1);
    }

    protected void goBack(int step) {
        renderJS("history.go(" + step + ")");
    }

    protected void alertAndGoback(String msg) {
        alertAndGoback(msg, -1);
    }

    protected void alertAndGoback(String msg, int step) {
        renderJS("alert('" + msg + "');history.go(" + step + ")");
    }

    protected void renderJS(String jsContent) {
        renderHtml("<script type=\"text/javascript\">" + jsContent + "</script>");
    }


}
