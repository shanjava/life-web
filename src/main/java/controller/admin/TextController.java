package controller.admin;

import com.jfinal.core.Controller;

import java.util.Date;

/**
 * Created by Mac on 2017/5/1.
 */
public class TextController extends Controller {

    public void index(){

        renderText("欢迎使用maven"+new Date().toString());
    }
}
