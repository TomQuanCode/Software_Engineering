package main;
import utils.*;

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * @author wizardk
 * @email ozx1341530199@gmail.com
 */
public class MainHelper {
    private static final String rootPath = System.getProperty("user.dir");

    public static void generate(int range, int numbers) {
        if (range <= 0 || numbers <= 0) {
            throw new IllegalArgumentException("range and numbers must larger than 0");
        }
        String qesPath = rootPath + "\\Exercises.txt";
        String ansPath = rootPath + "\\Answers.txt";
        BufferedWriter qWriter = null;
        BufferedWriter aWriter = null;
        try {
            qWriter = new BufferedWriter(new FileWriter(qesPath));
            aWriter = new BufferedWriter(new FileWriter(ansPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (qWriter == null || aWriter == null) {
            return;
        }
        try {
            for (int i = 1; i <= numbers; i++) {
                String qes = Expression.createExpressionWithCheck(range);
                String ans = Calculator.calculate(qes);
                FileUtils.writeQesAndAns(i, qes, ans, qWriter, aWriter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                qWriter.close();
                aWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
