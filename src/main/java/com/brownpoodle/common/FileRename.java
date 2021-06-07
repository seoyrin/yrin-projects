package com.brownpoodle.common;

import java.io.File;

public abstract class FileRename {

//	public static File rename(File sf) {
//
//		String f = sf.getName();
//		System.out.println("수정 전 파일 이름 : " + f);
//
//		try {
//			String s = f;
//			for (int i = 1; sf.exists(); i++) {
//				int lt = s.lastIndexOf(".");
//				String s1 = s.substring(0, lt);
//				String s2 = s.substring(lt);
//				String st = "_" + i;
//				f = s1.concat(st).concat(s2);
//
//				System.out.println("수정 후 파일 이름: " + f);
//				sf = new File(sf.getParent(), f);
//			}
//
//		} catch (Exception e) {
//			System.out.println("[ERROR] FileRename.rename() >>> : " + e.getMessage());
//		}
//
//		return sf;
//
//	}

	public static String rename(String originPath) {

		System.out.println(" > 수정 전 파일 경로 : " + originPath);

		File tmp = new File(originPath);
		String fileName = tmp.getName();
		System.out.println(" > 수정 전 파일 이름 : " + fileName);

		try {
			String s = fileName;

			for (int i = 1; tmp.exists(); i++) {
				int lt = s.lastIndexOf(".");
				String s1 = s.substring(0, lt);
				String s2 = s.substring(lt);
				String st = "_" + i;
				fileName = s1.concat(st).concat(s2);

				System.out.println(" > 수정 후 파일 이름: " + fileName);
				tmp = new File(tmp.getParent(), fileName);
			}

		} catch (Exception e) {
			System.out.println("[ERROR] FileRename -> rename() : " + e.getMessage());
			fileName=null; // 수정 !!!
		}

		System.out.println(" * 리턴될 파일 이름 : " + fileName);

		return fileName;
	}
}
