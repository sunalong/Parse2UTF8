package com.itcode.tools;

import java.io.File;

/**
 * 更改文件名
 * 将类似于：
 * /Users/along/Material/Java/副本/n_xyz/sasd.yyy
 * 改名并移动为：
 * /Users/along/Material/Java/副本/n_xyz.yyy
 * Created by along on 16/7/18.
 */
public class ChangeFileNameAdv {
    public static void main(String[] args) {
        ChangeFileNameAdv.changeName(new File("/Users/along/Material/Java/副本"), "_zxx");
    }

    /**
     * 遍历所给的目录
     * 若是目录，继续遍历
     * 若是文件，更改文件名
     */
    public static void changeName(File dir, String regularExpression) {
        System.out.print(dir.getName());
        String destName = getDestName(dir.getName(), regularExpression);
        File[] files = dir.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()){
                changeName(files[i], regularExpression);
            }
            else {
//                String destName = getDestName(files[i].getParent(),regularExpression);
                if (destName != null && files[i].getName().endsWith(".avi")) {
                    File destFile = new File(dir.getParent(), destName);
                    System.out.println("\n正在将文件：\n" + files[i].getAbsolutePath() + "\n改名为：\n" + destFile.getAbsolutePath());
                    files[i].renameTo(destFile);
                }
            }
        }
    }

    /**
     * 获取更改后的名称
     *
     * @param rawName           原文件名
     * @param regularExpression 分割符表达式
     * @return 更改后的文件名
     */
    public static String getDestName(String rawName, String regularExpression) {
        if (!rawName.contains(regularExpression)) {
            System.out.println("文件：" + rawName + "不符合更改规则，因为没有包含字符：" + regularExpression);
            return null;
        }
        String[] splitArr = rawName.split(regularExpression);
        StringBuilder sb = new StringBuilder(splitArr[0] + splitArr[splitArr.length-1]+".avi");
        System.out.println("结果:" + sb);
        return sb.toString();
    }
}
