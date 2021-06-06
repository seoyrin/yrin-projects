package com.brownpoodle.common;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartRequest;

public class FileUploadUtil {
	private Logger logger = Logger.getLogger(FileUploadUtil.class);

	public String uploadFile(MultipartHttpServletRequest request, String dirName) {
		String basepath = request.getSession().getServletContext()
				.getRealPath("resources" + File.separator + "img" + File.separator + dirName);
		System.out.println("basepath: " + basepath);

		File temp = new File(basepath);
		if (!temp.isDirectory()) {
			boolean r = temp.mkdirs();
			System.out.println("r : " + r);
		}

		try {
			Iterator<String> iterator = request.getFileNames();

			String uploadFileName = "";
			MultipartFile mFile = null;
			String orgFileName = ""; // 업로드한 파일명
			String uploadPath = "";
			String afterName = "";

			while (iterator.hasNext()) {
				uploadFileName = iterator.next();
				mFile = request.getFile(uploadFileName);

				orgFileName = mFile.getOriginalFilename();
				System.out.println("orgFileName : " + orgFileName);

				uploadPath = basepath + File.separator + orgFileName;
				System.out.println("upload path :" + uploadPath);

				// 중복된 파일이름 변경
				afterName = FileRename.rename(uploadPath);
				if (afterName != null) {
					uploadPath = basepath + File.separator + afterName;
					
					File f = new File(uploadPath);

					mFile.transferTo(f);
				} else {
					afterName = "-";
				}
			} // while end

			return afterName;
		} catch (Exception e) {
			System.out.println("[ERROR] file upload error! -> " + e.getMessage());
		}

		return null;
	}
	
//	public ArrayList<String> getFileNames() {
//		
//		@SuppressWarnings("unchecked")
//		Iterator<String> en = mr.getFileNames();
//		ArrayList<String> a = new ArrayList<String>();
//		
//		while (en.hasNext()){
//			String f = en.next().toString();
//			a.add(mr.get);
//		}		
//		return a;
//	}
}

	
