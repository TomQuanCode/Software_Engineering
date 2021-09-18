package homework1papercheck;
import java.math.BigInteger;
import java.io.*;
public class MyTest {
    //原文路径
    static String origin_text="D:\\Test_text\\orig.txt";
    //要测试的5个文件路径
    static String texts[]={
            "D:\\Test_text\\orig_0.8_add.txt",
            "D:\\Test_text\\orig_0.8_del.txt",
            "D:\\Test_text\\orig_0.8_dis_1.txt",
            "D:\\Test_text\\orig_0.8_dis_10.txt",
            "D:\\Test_text\\orig_0.8_dis_15.txt"};
    //结果存放文件路径
    static String targets[]= {
            "D:\\Test_text\\result_add.txt",
            "D:\\Test_text\\result_del.txt",
            "D:\\Test_text\\result_dis_1.txt",
            "D:\\Test_text\\result_dis_10.txt",
            "D:\\Test_text\\result_dis_15.txt"
    };

    //获取文件名
    public String getFilename(String path) {
        File file = new File(path);
        return file.getName();

    }

    //计算相似度，path1为原文本路径，path2为测试文件路径，path3为结果存放路径
    public void countSimilarity(String path1,String path2,String path3){
        //读取文件并得到特征值
        TxtFileInput fileInput = new TxtFileInput();
        SimHashFinish hash1 = new SimHashFinish(fileInput.beString(path1), 64);
        hash1.FiDistance(hash1, 3);
        SimHashFinish hash2 = new SimHashFinish(fileInput.beString(path2), 64);
        hash2.FiDistance(hash2, 3);
        //计算相似度
        double distance = hash1.getDistance(hash1.getStrSimHash(),hash2.getStrSimHash());
        //结果精确到两位小数
        String answer = String.format("%.2f",(100-distance*100/128))+"%";
        System.out.println(getFilename(path2)+"与原文相似度为："+answer);
        String s = getFilename(path2)+"与原文相似度为："+answer;
        File file = new File(path3);
        //将字符串转换为字节数
        byte b[] = s.getBytes();
        //输出流
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
            //写入结果
            bw.write(s);
            //关闭流
            bw.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    //测试
    public static void main(String[] args)  {
        MyTest test = new MyTest();
        test.countSimilarity(args[0],args[1],args[2]);
        /*test.countSimilarity("D:\\Test_text\\orig.txt", "D:\\Test_text\\orig_0.8_add.txt", "D:\\Test_text\\result_add.txt");
        test.countSimilarity("D:\\Test_text\\orig.txt", "D:\\Test_text\\orig_0.8_del.txt", "D:\\Test_text\\result_del.txt");
        test.countSimilarity("D:\\Test_text\\orig.txt", "D:\\Test_text\\orig_0.8_dis_1.txt", "D:\\Test_text\\result_dis_1.txt");
        test.countSimilarity("D:\\Test_text\\orig.txt", "D:\\Test_text\\orig_0.8_dis_10.txt", "D:\\Test_text\\result_dis_10.txt");
        test.countSimilarity("D:\\Test_text\\orig.txt", "D:\\Test_text\\orig_0.8_dis_15.txt", "D:\\Test_text\\result_dis_15.txt");*/
    }
}
