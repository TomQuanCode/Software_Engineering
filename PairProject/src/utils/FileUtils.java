package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 文件读写的工具类
 *
 * @author wizardk
 * @email ozx1341530199@gmail.com
 */
public class FileUtils {

    public static void writeAns() {

    }

    /**
     * 根据题目判断对错
     *
     * @param questionFile 题目文件路径
     * @param ansFile      答案文件路径
     * @param outputPath   对错结果文件输出路径
     */
    public static void judge(String questionFile, String ansFile, String outputPath) {
        List<Integer> correctList = new ArrayList<>();
        List<Integer> wrongList = new ArrayList<>();

        BufferedReader qReader = null;
        BufferedReader aReader = null;
        BufferedWriter writer = null;
        String question;
        String answer;
        try {
            qReader = new BufferedReader(new FileReader(questionFile));
            aReader = new BufferedReader(new FileReader(ansFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (qReader == null || aReader == null) {
            return;
        }

        try {
            while ((question = qReader.readLine()) != null && (answer = aReader.readLine()) != null) {
                int number = Integer.parseInt(question.substring(0, question.lastIndexOf(".")));
                String qes = question.substring(question.lastIndexOf(".") + 1);
                String ans = answer.substring(answer.lastIndexOf(".") + 1);
                if (Objects.equals(Calculator.calculate(qes), ans)) {
                    correctList.add(number);
                } else {
                    wrongList.add(number);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                qReader.close();
                aReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            writer = new BufferedWriter(new FileWriter(outputPath));
            writer.write("Correct: " + correctList.size() + " (");
            for (int i = 0; i < correctList.size(); i++) {
                if (i == correctList.size() - 1) {
                    writer.write(correctList.get(i));
                    break;
                }
                writer.write(correctList.get(i) + ", ");
            }
            writer.write(")\n");
            writer.write("Wrong: " + wrongList.size() + "(");
            for (int i = 0; i < wrongList.size(); i++) {
                if (i == wrongList.size() - 1) {
                    writer.write(wrongList.get(i));
                    break;
                }
                writer.write(wrongList.get(i) + ", ");
            }
            writer.write(")\n");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

}
