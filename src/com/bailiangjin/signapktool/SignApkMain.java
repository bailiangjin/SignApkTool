package com.bailiangjin.signapktool;

import com.kevin.javaawtlib.callback.FilePathCallback;
import com.kevin.javaawtlib.swing.DefaultValueJTextField;
import com.kevin.javaawtlib.swing.FileChooseBtn;
import com.kevin.javabaselib.utils.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by bailiangjin on 16/6/25.
 */
public class SignApkMain extends JFrame {

    public static String password;
    public static String keyFilePath;
    public static String signedApkPath;
    public static String unsignedApkFilePath;
    public static String alias;

    public SignApkMain() {
    }

    public static void constructGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(3);
        frame.setBounds(400, 200, 450, 200);
        frame.setTitle("Android Apk签名工具——by bailiangjin");
        frame.setLayout(new GridLayout(6, 2));
        final ArrayList list = new ArrayList();
        final DefaultValueJTextField textField_unSignApk = new DefaultValueJTextField("未签名Apk文件路径", 1000);
        final DefaultValueJTextField textField_keyStore = new DefaultValueJTextField("签名key路径", 1000);
        final DefaultValueJTextField textField_signed = new DefaultValueJTextField("签名后Apk输出路径", 1000);
        FileChooseBtn btn1 = new FileChooseBtn("点击选择未签名Apk文件", new FilePathCallback() {
            public void onFileSelected(String filePath) {
                System.out.println("未签名Apk文件:" + filePath);
                textField_unSignApk.setText(filePath);
            }
        });
        FileChooseBtn btn2 = new FileChooseBtn("点击选择签名文件", new FilePathCallback() {
            public void onFileSelected(String filePath) {
                System.out.println("签名文件:" + filePath);
                textField_keyStore.setText(filePath);
            }
        });
        FileChooseBtn btn3 = new FileChooseBtn("点击选择签名后Apk输出路径", new FilePathCallback() {
            public void onFileSelected(String filePath) {
                System.out.println("签名后Apk输出路径:" + filePath);
                textField_signed.setText(filePath);
            }
        });
        JLabel label1 = new JLabel("Alias:", 0);
        JLabel label2 = new JLabel("password:", 0);
        DefaultValueJTextField textField_alias = new DefaultValueJTextField("请输入Alias", 1000);
        final JPasswordField passwordField = new JPasswordField();
        final JButton btn_cancel = new JButton("取消");
        final JButton btn_sign = new JButton("执行签名");
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == btn_cancel) {
                    System.exit(0);
                }

                if (e.getSource() == btn_sign) {
                    boolean isComplete = true;
                    StringBuffer sb = new StringBuffer();
                    sb.append("<html><body>");
                    Iterator dialog = list.iterator();

                    while (dialog.hasNext()) {
                        DefaultValueJTextField labelContent = (DefaultValueJTextField) dialog.next();
                        if (labelContent.isDefaultValue()) {
                            String jbtn = labelContent.getDefaultValue() + ":未设置值" + "<br>";
                            sb.append(jbtn);
                            isComplete = false;
                        }
                    }

                    if (StringUtils.isEmpty(passwordField.getText())) {
                        sb.append("密码:未设置");
                        isComplete = false;
                    }

                    sb.append("</body></html>");
                    if (!isComplete) {
                        final JDialog dialog1 = new JDialog(frame, "提示");
                        dialog1.setBounds(100, 100, 200, 200);
                        JLabel labelContent1 = new JLabel("Alias:", 0);
                        labelContent1.setText(sb.toString());
                        dialog1.add(labelContent1);
                        JButton jbtn1 = new JButton("确定");
                        jbtn1.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                dialog1.hide();
                            }
                        });
                        JPanel pan = new JPanel();
                        pan.setLayout(new FlowLayout());
                        pan.add(labelContent1);
                        pan.add(jbtn1);
                        dialog1.add(pan);
                        dialog1.show();
                    } else {
                        password = passwordField.getText();
                        keyFilePath = textField_keyStore.getText();
                        unsignedApkFilePath = textField_unSignApk.getText();
                        signedApkPath = textField_signed.getText()+ File.separator+"signedApk.apk";
                        alias = textField_alias.getText();
                        SignCmdBean signCmdBean = new SignCmdBean(password, keyFilePath, signedApkPath, unsignedApkFilePath, alias);
                        signCmdBean.executeSign();

                        // 退出 关闭窗口界面
                        System.exit(0);
                    }
                }

            }
        };
        list.add(textField_unSignApk);
        list.add(textField_keyStore);
        list.add(textField_signed);
        list.add(textField_alias);
        btn_cancel.addActionListener(actionListener);
        btn_sign.addActionListener(actionListener);
        frame.add(textField_unSignApk);
        frame.add(btn1);
        frame.add(textField_keyStore);
        frame.add(btn2);
        frame.add(textField_signed);
        frame.add(btn3);
        frame.add(label1);
        frame.add(textField_alias);
        frame.add(label2);
        frame.add(passwordField);
        frame.add(btn_cancel);
        frame.add(btn_sign);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                SignApkMain.constructGUI();
            }
        });
    }
}
