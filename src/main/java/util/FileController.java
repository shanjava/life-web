package util;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Mac on 2017/3/2.
 */
public class FileController extends Controller {
    public static String upload(UploadFile file) {

        String url = "";
        System.out.println("" + file.getUploadPath() + "/" + file.getFileName());
        String fileName = file.getFileName();
        String extension = fileName.substring(fileName.lastIndexOf("."));
        String prefix;
        String sysurl = PropKit.get("fileurl");
        if (".png".equals(extension) || ".jpg".equals(extension) || ".gif".equals(extension)) {
            prefix = "img";
            fileName = generateWord() + extension;
        } else {
            prefix = "docx";
        }

        String path = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
        JSONObject json = new JSONObject();
        try {
            File source = file.getFile();
            FileInputStream fis = new FileInputStream(source);
            File targetDir = new File(sysurl + "/" + prefix + "/u/"
                    + path);
            if (!targetDir.exists()) {
                targetDir.mkdirs();
            }
            File target = new File(targetDir, fileName);
            if (!target.exists()) {
                target.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(target);
            byte[] bts = new byte[300];
            while (fis.read(bts, 0, 300) != -1) {
                fos.write(bts, 0, 300);
            }

            fos.close();
            fis.close();
            json.put("error", 0);
            json.put("url", "/" + prefix + "/u/" + path + separator() + fileName);
            url = sysurl + "/" + prefix + "/u/" + path + "/" + fileName;
            source.delete();
        } catch (FileNotFoundException e) {
            json.put("error", 1);
            json.put("message", "上传出现错误，请稍后再上传");
        } catch (IOException e) {
            json.put("error", 1);
            json.put("message", "文件写入服务器出现错误，请稍后再上传");
        }
        System.out.println(json.toJSONString());

        return url;

    }

    private static String generateWord() {
        String[] beforeShuffle = new String[]{"2", "3", "4", "5", "6", "7",
                "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
                "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                "W", "X", "Y", "Z"};
        List<String> list = Arrays.asList(beforeShuffle);
        Collections.shuffle(list);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
        }
        String afterShuffle = sb.toString();
        String result = afterShuffle.substring(5, 9);
        return result;
    }


    public static String separator() {
        return System.getProperties().getProperty("file.separator");

    }

    public static String sub(String s) {
        return s.toString().substring(0, s.length() - 1);

    }


    // -----------------------------------------------
//            fileController.Write(sends,"物流","pack",getResponse());

    public void Write(List<Record> list, String titles, String template, HttpServletResponse response) {

        // 得到原始模板路径

        // Jfinal获取项目文件路径
        String templateFilePath = JFinal.me().getServletContext().getRealPath("templete") + File.separator +
                template + "_template.xls";

        // Tomcat获取项目文件路径
//		String templateFilePath = getRequest().getServletContext().getRealPath("templete") + File.separator +
// "xtask_export_template.xls";

        System.out.println("moban：" + templateFilePath);

        // 导出excel的标题
        String title = titles;

        //将导出excel的数据和导出excel的标题放到集合中
        /**
         * 这儿申明的map集合是为了后面transformXLS方法转化需要传入Excel里面的一个Map，
         * jxls根据Template里面的定义和Map里面的对象对Template进行解析，
         * 将Map里面的对象值填入到Excel文件中。
         */
        Map<String, Object> datamap = new HashMap<String, Object>();

        datamap.put("list", list);      // 导出excel的数据
        datamap.put("title", title);      // 导出excel的标题

        InputStream in = null;
        try {
            in = new FileInputStream(templateFilePath);      // 将模板文件转换为文件流

            XLSTransformer transformer = new XLSTransformer();      // jxls生成excel
            System.out.println("transformer = " + transformer);

            HSSFWorkbook wb = (HSSFWorkbook) transformer.transformXLS(in, datamap);      // 将excel流转换为Workbook
            System.out.println("wb = " + wb.toString());
            Sheet sheet = wb.getSheetAt(0);      // 取第一个sheet
            System.out.println("sheet = " + sheet.toString());
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 0));      // 四个参数分别是：起始行，结束行，起始列，结束列

            writeStream(title, wb, response);      // 返回excel

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParsePropertyException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) in.close();
            } catch (IOException e) {
            }
        }
        renderNull();
    }

    /**
     * 输出文件流
     *
     * @param filename
     * @param wb
     * @param response
     */
    public static void writeStream(String filename, Workbook wb, HttpServletResponse response) {
        try {
            filename += ".xls";

            filename.replaceAll("/", "-");
            filename = URLEncoder.encode(filename, "UTF-8");
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            response.setContentType("application/octet-stream;charset=UTF-8");

            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());

            wb.write(outputStream);

            outputStream.flush();
            outputStream.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 导入类
     * @param file
     */

    public static void billionInfo(String file) {
        System.out.println(" = 发票基础信息");
        try {
            jxl.Workbook rwb;
            rwb = jxl.Workbook.getWorkbook(new File(file));
            jxl.Sheet rs = rwb.getSheet(1);//或者rwb.getSheet(0)
            int clos = rs.getColumns();//得到所有的列
            int rows = rs.getRows();//得到所有的行

            System.out.println(clos + " rows:" + rows);
            for (int i = 1; i < rows; i++) {
                //第一个是列数，第二个是行数
                int j = 0;
                //    rs.getCell(j++, i).getContents()
                List<Record> list = Db.find("select id from cx_contract_info where contractNo= '" + rs.getCell(0, i).getContents() + "'");
//                BillionHistory b = new BillionHistory();
//                b.setContractNo(rs.getCell(j++, i).getContents());
//                b.setBillionNo(rs.getCell(j++, i).getContents());
//                b.setBillionNise(rs.getCell(j++, i).getContents());
//                b.setBillionAmount(strToBig(rs.getCell(j++, i).getContents()));
//                b.setBillionDate(Util.stringToDate(rs.getCell(j++, i).getContents()));
//                b.setOperator(rs.getCell(j++, i).getContents());
//                b.setState(Integer.parseInt(rs.getCell(j++, i).getContents()));
//                b.setExpressNo(rs.getCell(j++, i).getContents());
//                b.setExpressOrderNo(rs.getCell(j++, i).getContents());
//                b.setRemarks(rs.getCell(j++, i).getContents());
//                b.save();

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //  return list;

    }

}






