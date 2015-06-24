package com.itcode.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * 将批量的gbk的乱码文件转换到utf8 将gbk的代码放到srcDir之下，转码置destDir之下，暂不支持srcDir之下有目录，有需要再添加
 */
public class UTF8Parser {
	static File srcDir = new File("/Users/along/Documents/workspace/CartoonReader"); // 待转码的GBK格式文件夹

	public static void main(String[] args) {
		File[] fs = srcDir.listFiles();
		try {
			parse(fs);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 目录就迭代遍历；文件就重编码
	 */
	public static void parse(File[] fs) throws IOException {
		for (File gbkFile : fs) {
			if (gbkFile.isDirectory()) {// 如果是文件夹，则递归遍历
				parse(gbkFile.listFiles());
			} else {// 如果是文件，则转化
				if (!gbkFile.getName().substring(gbkFile.getName().lastIndexOf(".")).equals(".java"))// 如果是以java为后缀，则转化
					continue;
				/**
				 * 一：创建文件：xxx.java.temp 二：将字符转码，输出到创建的文件中 三：将老文件删除，将创建的文件重新命名
				 */
				File utf8File = CreateFileUtil.createFile(gbkFile.getParent() + "/" + gbkFile.getName() + ".temp");
				parse2UTF_8(gbkFile, utf8File);
				// deleteGBKFile(gbkFile);//文件不删除也行，下一步重全名会自动将文件覆盖掉
				renameFile(utf8File);
			}
		}
	}

	/**
	 * 为文件重新全名
	 * 
	 * @param utf8File
	 */
	private static void renameFile(File utf8File) {
		String tempName = utf8File.getName();
		String newName = tempName.substring(0, tempName.lastIndexOf("."));
		utf8File.renameTo(new File(utf8File.getParent(), newName));
	}

	/**
	 * 将文件删除
	 * 
	 * @param file
	 */
	private static void deleteGBKFile(File file) {
		if (file.exists())
			file.delete();
	}

	/**
	 * 将gbk文件转化为utf-8文件
	 */
	public static void parse2UTF_8(File file, File destFile) throws IOException {
		StringBuffer msg = new StringBuffer();
		// 读写对象
		PrintWriter ps = new PrintWriter(new OutputStreamWriter(new FileOutputStream(destFile, false), "utf8"));
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "gbk"));

		// 读写动作
		String line = br.readLine();
		while (line != null) {
			msg.append(line).append("\r\n");
			line = br.readLine();
		}
		ps.write(msg.toString());
		br.close();
		ps.flush();
		ps.close();
	}

}