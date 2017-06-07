package util.image;

import java.awt.Image;
import java.awt.image.BufferedImage;  
import java.io.File;  
import java.io.FileOutputStream;  
import java.io.IOException;  
  
import javax.imageio.ImageIO;  
  
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  
  
import com.sun.image.codec.jpeg.JPEGCodec;  
import com.sun.image.codec.jpeg.JPEGImageEncoder;  
  
@SuppressWarnings("restriction")  
public class CompressPic{  
      
    private static final Logger log = LoggerFactory.getLogger(CompressPic.class);  
      
    private File file = null; // 文件对象  
      
    private String inputDir; // 输入图路径  
      
    private String outputDir; // 输出图路径  
      
    private String inputFileName; // 输入图文件名  
      
    private String outputFileName; // 输出图文件名  
      
    private int outputWidth = 100; // 默认输出图片宽  
      
    private int outputHeight = 100; // 默认输出图片高  
      
    private boolean proportion = true; // 是否等比缩放标记(默认为等比缩放)  
      
    public CompressPic()  
    { // 初始化变量  
        inputDir = "";  
        outputDir = "";  
        inputFileName = "";  
        outputFileName = "";  
        outputWidth = 100;  
        outputHeight = 100;  
    }  
      
    public void setInputDir(String inputDir)  
    {  
        this.inputDir = inputDir;  
    }  
      
    public void setOutputDir(String outputDir)  
    {  
        this.outputDir = outputDir;  
    }  
      
    public void setInputFileName(String inputFileName)  
    {  
        this.inputFileName = inputFileName;  
    }  
      
    public void setOutputFileName(String outputFileName)  
    {  
        this.outputFileName = outputFileName;  
    }  
      
    public void setOutputWidth(int outputWidth)  
    {  
        this.outputWidth = outputWidth;  
    }  
      
    public void setOutputHeight(int outputHeight)  
    {  
        this.outputHeight = outputHeight;  
    }  
      
    public void setWidthAndHeight(int width, int height)  
    {  
        this.outputWidth = width;  
        this.outputHeight = height;  
    }  
  
    /* 
     * 获得图片大小 传入参数 String path ：图片路径 
     */  
    public long getPicSize(String path)  
    {  
        file = new File(path);  
        return file.length();  
    }  
  
    // 图片处理  
    public String compressPic()  
    {  
          
        FileOutputStream out =null;  
          
        try  
        {  
            // 获得源文件  
            file = new File(inputDir + inputFileName);  
            if (!file.exists())  
            {  
                return "";  
            }  
            Image img = ImageIO.read(file);  
            // 判断图片格式是否正确  
            if (img.getWidth(null) == -1)  
            {  
                log.error(this.getClass().getName()+".compressPic-要压缩的图片格式不正确");  
                return "no";  
            }  
            else  
            {  
                int newWidth;  
                int newHeight;  
                // 判断是否是等比缩放  
                if (this.proportion == true)  
                {  
                    // 为等比缩放计算输出的图片宽度及高度  
                    double rate1 = ((double)img.getWidth(null)) / (double)outputWidth + 0.1;  
                    double rate2 = ((double)img.getHeight(null)) / (double)outputHeight + 0.1;  
                    // 根据缩放比率大的进行缩放控制  
                    double rate = rate1 > rate2 ? rate1 : rate2;  
                    newWidth = (int)(((double)img.getWidth(null)) / rate);  
                    newHeight = (int)(((double)img.getHeight(null)) / rate);  
                }  
                else  
                {  
                    newWidth = img.getWidth(null); // 输出的图片宽度  
                    newHeight = img.getHeight(null); // 输出的图片高度  
                }  
                BufferedImage tag = new BufferedImage((int)newWidth, (int)newHeight, BufferedImage.TYPE_INT_RGB);  
                  
                /* 
                 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢 
                 */  
                tag.getGraphics().drawImage(img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH), 0, 0, null);  
                out = new FileOutputStream(outputDir + outputFileName);  
                // JPEGImageEncoder可适用于其他图片类型的转换  
                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);  
                encoder.encode(tag);  
            }  
        }  
        catch (IOException ioex1)  
        {  
              
            log.error(this.getClass().getName() + ".compressPic-压缩图片时发生异常" + ioex1.getMessage());  
              
        }  
        finally {  
              
            if (out != null)  
            {  
                try  
                {  
                    out.close();  
                }  
                catch (IOException ioex2)  
                {  
                    log.error(this.getClass().getName() + ".compressPic - 不能关闭输出文件" + ioex2.getMessage());  
                }  
            }  
        }  
          
        return "ok";  
    }  
      
    public String compressPic(String inputDir, String outputDir, String inputFileName, String outputFileName)  
    {  
        // 输入图路径  
        this.inputDir = inputDir;  
        // 输出图路径  
        this.outputDir = outputDir;  
        // 输入图文件名  
        this.inputFileName = inputFileName;  
        // 输出图文件名  
        this.outputFileName = outputFileName;  
        return compressPic();  
    }  
      
    public String compressPic(String inputDir, String outputDir, String inputFileName, String outputFileName, int width,  
        int height, boolean gp)  
    {  
        // 输入图路径  
        this.inputDir = inputDir;  
        // 输出图路径  
        this.outputDir = outputDir;  
        // 输入图文件名  
        this.inputFileName = inputFileName;  
        // 输出图文件名  
        this.outputFileName = outputFileName;  
        // 设置图片长宽  
        setWidthAndHeight(width, height);  
        // 是否是等比缩放 标记  
        this.proportion = gp;  
        return compressPic();  
    }  
           
  
    // main测试  
    // compressPic(大图片路径,生成小图片路径,大图片文件名,生成小图片文名,生成小图片宽度,生成小图片高度,是否等比缩放(默认为true))  
    public static void main(String[] arg)  
    {  
        CompressPic mypic = new CompressPic();  
        System.out.println("输入的图片大小：" + mypic.getPicSize("e:\\1.jpg") / 1024 + "KB");  
        mypic.compressPic("e:\\", "e:\\test\\", "1.jpg", "r2.jpg", 100, 100, false);  
          
    }  
}  