package ui;

import main.MainHelper;
import utils.*;

import javax.swing.*;

/**
 * @author wizardk
 * @email ozx1341530199@gmail.com
 */
public class MainWindow {
    private JButton generateButton;
    private JTextField qesPath_Tf;
    private JTextField ansPath_Tf;
    private JTextField n_Tf;
    private JTextField r_Tf;
    private JButton qesFileChooseButton;
    private JButton ansFileChooseButton;
    private JTextField resPath_Tf;
    private JButton resFileChooseButton;
    private JButton judgeButton;
    private JLabel qesNumbers;
    private JLabel maxVal;
    private JLabel qesFilePath;
    private JLabel ansFilePath;
    private JLabel outPath;
    private JPanel mPanel;
    private String outputPath;

    public MainWindow() {

        qesFileChooseButton.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int val = fc.showOpenDialog(null);    //文件打开对话框
            if (val == JFileChooser.APPROVE_OPTION) {
                //正常选择文件
                outputPath = fc.getSelectedFile().toString();
                qesPath_Tf.setText(outputPath);
            } else {
                //未正常选择文件，如选择取消按钮
                outputPath = null;
                qesPath_Tf.setText("请选择题目文件");
            }
        });

        ansFileChooseButton.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int val = fc.showOpenDialog(null);    //文件打开对话框
            if (val == JFileChooser.APPROVE_OPTION) {
                //正常选择文件
                outputPath = fc.getSelectedFile().toString();
                ansPath_Tf.setText(outputPath);
            } else {
                //未正常选择文件，如选择取消按钮
                outputPath = null;
                ansPath_Tf.setText("请选择答案文件");
            }
        });

        resFileChooseButton.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int val = fc.showOpenDialog(null);    //文件打开对话框
            if (val == JFileChooser.APPROVE_OPTION) {
                //正常选择文件
                outputPath = fc.getSelectedFile().toString();
                resPath_Tf.setText(outputPath);
            } else {
                //未正常选择文件，如选择取消按钮
                outputPath = null;
                resPath_Tf.setText("请选择统计结果文件输出目录");
            }
        });

        judgeButton.addActionListener(e -> {
            String qesPath = qesPath_Tf.getText();
            String ansPath = ansPath_Tf.getText();
            String resPath = resPath_Tf.getText();
            if (outputPath != null) {
                new Thread(() -> FileUtils.judge(qesPath, ansPath, resPath)).start();
                JOptionPane.showMessageDialog(null, "已输出至指定路径！", "成功", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "源目录或输出目录无效，请重新选择！", "警告", JOptionPane.WARNING_MESSAGE);
            }
        });

        generateButton.addActionListener(e -> {
            int r = Integer.parseInt(r_Tf.getText());
            int n = Integer.parseInt(n_Tf.getText());
            new Thread(() -> MainHelper.generate(r, n)).start();
        });

    }

    public void init() {
        JFrame frame = new JFrame("四则运算生成器 —— MadeBy WizardK and Quan");
        frame.setContentPane(new MainWindow().mPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
