package homework1papercheck;
import java.io.*;
public class TxtFileInput {
    //BeString方法实现将txt文件中的文本内容转化为字符串，参数为文件绝对路径，返回值为字符串
    public String beString(String path){
        int length = 0;
        //新建字符串缓冲区str作为容器
        StringBuffer str = new StringBuffer("");
        File file = new File(path);

        //try—catch捕捉可能出现的异常
        try {
            //创建输入流
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            //readLine()方法逐行读入内容，append()方法向字符串缓冲区str添加字符串
            while((line = br.readLine())!=null){
                if (length==0){
                    str.append(line);
                }else {
                    str.append("\r\n"+line);
                }
                length++;
            }
            //关闭流
            br.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //调用toString()方法，返回长字符串
        return str.toString();
    }
}
