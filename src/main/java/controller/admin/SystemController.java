package controller.admin;

import com.jfinal.core.Controller;
import controller.BaseController;
import entity.Code;
import util.systemInfo.ClientStatus;
import util.systemInfo.LinuxSystemTool;
import util.systemInfo.StatusUtil;

import java.io.IOException;

/**
 * 服务器信息类
 * <p> system
 * Created by Mac on 2017/5/17.
 */
public class SystemController extends BaseController {

/*可以用*/


    public  void  session(){

        renderJson(ErrorCode(Code.SUCCESS));


    }

    public void projectInfo() {

        String[] a = new String[2];
        ClientStatus clientStatus = StatusUtil.getClientStatus("life-web", 1, "shan.wang", "", a);

        setAttr("freeMemory", clientStatus.getFreeMemory());
        setAttr("totalMemory", clientStatus.getTotalMemory());
        setAttr("maxMemory", clientStatus.getMaxMemory());
        setAttr("osName", clientStatus.getOsName());
        setAttr("startTime", clientStatus.getStartTime());
        setAttr("runtime", clientStatus.getRuntime());
        setAttr("threadCount", clientStatus.getThreadCount());
        renderJson();
    }





/*需要测试*/
    public void sysInfo() {
        int[] memInfo = new int[0];
        try {
            memInfo = LinuxSystemTool.getMemInfo();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("MemTotal:" + memInfo[0]);
        System.out.println("MemFree:" + memInfo[1]);
        System.out.println("SwapTotal:" + memInfo[2]);
        System.out.println("SwapFree:" + memInfo[3]);

        try {
            System.out.println("CPU利用率:" + LinuxSystemTool.getCpuInfo());
        } catch (IOException e) {
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
    /*emTotal:1016412
MemFree:73632
SwapTotal:1048572
SwapFree:1043988
CPU利用率:0.0033333334
*/
}
