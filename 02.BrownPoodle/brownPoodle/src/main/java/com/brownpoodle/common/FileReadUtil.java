package com.brownpoodle.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class FileReadUtil {

	// 게시판 이미지 업로드 경로
	private static String FILE_PATH = CommonUtil.B_IMG_UPLOAD_PATH;
	
	public static void readFile(HttpServletRequest req, HttpServletResponse res)
									throws IOException {
		req.setCharacterEncoding("UTF-8");
		
		String fileName = "be-su.gif";
		String filePath = FILE_PATH.concat("\\").concat(fileName);
		File f = new File(filePath);
		
		System.out.println(" > fileName : " + fileName);
		System.out.println(" > filePath : " + filePath);
		System.out.println(" > f : " + f);
		
		// 디렉토리라면 중단
		if (f.isDirectory()) return;
		
		// 파일 사이즈가 크면 중단
		long fileSize = f.length();
		if (fileSize > Integer.MAX_VALUE) {
			System.out.println(" [WARNING] 파일 사이즈가 너무 큽니다! ");
			return;
		}
		
		// MIME TYPE
		String mimeType;
		String disposition = "filename=\"" + filePath + "\"";
		System.out.println(" > disposition : " + disposition);
		
		// 파일 확장자에 따라 MIME TYPE 지정
		if (filePath.toLowerCase().endsWith("xml")) {
			mimeType = "text/xml;charset=utf-8";
		} else if(filePath.toLowerCase().endsWith("doc")) {
			mimeType = "application/msword";
		} else if(filePath.toLowerCase().endsWith("xls")) {
			mimeType = "application/vnd.ms-excel";
		} else if(filePath.toLowerCase().endsWith("xlsx")) {
			mimeType = "application/msword";
		} else if(filePath.toLowerCase().endsWith("hwp")) {
			mimeType = "application/octet-stream";
		} else if(filePath.toLowerCase().endsWith("gif")) {
			mimeType = "image/gif";
		} else if(filePath.toLowerCase().endsWith("jpg")) {
			mimeType = "image/jpeg";
		} else if(filePath.toLowerCase().endsWith("png")) {
			mimeType = "image/png";
		} else {
			mimeType = "application/octet-stream";
		}
		
		// response reset
		res.setBufferSize(0);
		
		fileName = new String(fileName.getBytes("UTF-8"), "UTF-8");
		
		// header 세팅
		res.setHeader("Content-Disposition", "attachment; filename=" + fileName + ";");
		res.setHeader("Content-Transfer-Encoding", "7bit");
		res.setHeader("Accept-Ranges", "bytes");
		res.setContentLength((int)fileSize);
		res.setHeader("Connection", "close");
		res.setContentType(mimeType + ";charset=utf-8");
		
		FileInputStream in = new FileInputStream(f);
		
		final int BUF_SIZE = 8 * 1024;
		final OutputStream out = res.getOutputStream();
		final byte[] buf = new byte[BUF_SIZE];
		
		int n;
		while (-1 != (n = in.read(buf))) {
			out.write(buf, 0, n);
		}
		
		out.flush();
		
	}
}
