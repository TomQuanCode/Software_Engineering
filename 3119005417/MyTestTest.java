package homework1papercheck;

import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class MyTestTest {
    /**
     * Test for addition:orig_0.8_add.txt
     */
    @Test
    public void testForAdd() {
        try{
            MyTest t =new MyTest();
            String s[] = new String[]{"D:\\Test_text\\orig.txt","D:\\Test_text\\orig_0.8_add.txt","D:\\Test_text\\result_add.txt"};
            t.main(s);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Test for deletion:orig_0.8_del.txt
     */
    @Test
    public void testForDel() {
        try{
            MyTest t =new MyTest();
            String s[] = new String[]{"D:\\Test_text\\orig.txt","D:\\Test_text\\orig_0.8_del.txt","D:\\Test_text\\result_del.txt"};
            t.main(s);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Test for dis_1:orig_0.8_dis_1.txt
     */
    @Test
    public void testForDis_1() {
        try{
            MyTest t =new MyTest();
            String s[] = new String[]{"D:\\Test_text\\orig.txt","D:\\Test_text\\orig_0.8_dis_1.txt","D:\\Test_text\\result_dis_1.txt"};
            t.main(s);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Test for dis_10:orig_0.8_dis_10.txt
     */
    @Test
    public void testForDis_10() {
        try{
            MyTest t =new MyTest();
            String s[] = new String[]{"D:\\Test_text\\orig.txt","D:\\Test_text\\orig_0.8_dis_10.txt","D:\\Test_text\\result_dis_10.txt"};
            t.main(s);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Test for dis_15:orig_0.8_dis_15.txt
     */
    @Test
    public void testForDis_15() {
        try{
            MyTest t =new MyTest();
            String s[] = new String[]{"D:\\Test_text\\orig.txt","D:\\Test_text\\orig_0.8_dis_15.txt","D:\\Test_text\\result_dis_15.txt"};
            t.main(s);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * Test for FileNotFoundException
     */
    @Test
    public void testForWrongPath() throws FileNotFoundException {
        try{
            MyTest t =new MyTest();
            String s[] = new String[]{"","",""};
            t.main(s);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}