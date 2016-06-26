package com.bailiangjin.signapktool;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by bailiangjin on 16/6/26.
 */
public class CmdUtils {
    public CmdUtils() {
    }

    public static void main(String[] args) {
        String cmd = "ping www.baidu.com";
        runCmd(cmd);
    }

    public static void runCmd(String cmd) {
        Runtime run = Runtime.getRuntime();

        try {
            Process process;
            String osName = System.getProperties().getProperty("os.name");
            if(osName.toLowerCase().contains("mac")){
                String[] e = new String[]{"/bin/sh", "-c", cmd};
                process = run.exec(e);
            }else {
                String[] e = new String[]{"@echo", cmd};
                process = run.exec(cmd);
            }

            BufferedInputStream bufferedInputStream = new BufferedInputStream(process.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream));

            String lineStr;
            while((lineStr = bufferedReader.readLine()) != null) {
                System.out.println(lineStr);
            }

            if(process.waitFor() != 0 && process.exitValue() == 1) {
                System.err.println("命令执行失败!");
            }

            bufferedReader.close();
            bufferedInputStream.close();
        } catch (Exception var7) {
            var7.printStackTrace();
        }

    }
}