package com.jt.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jt.service.FileService;
import com.jt.vo.EasyUIImage;

@Controller
public class FileController {
	@Autowired
	private FileService fileService;

	@RequestMapping("file")
	public String file(MultipartFile fileImage) throws IllegalStateException, IOException {
		File file = new File("D:/1-JT/jt-image");
		if (!file.exists()) {
			file.mkdirs();
		}
		String fileName = fileImage.getOriginalFilename();
		File file2 = new File("D:/1-JT/jt-image/" + fileName);
		fileImage.transferTo(file2);
		return "redirect:/file.jsp";
	}

	@RequestMapping("/pic/upload")
	@ResponseBody
	public EasyUIImage uploadImage(MultipartFile uploadFile) {
		EasyUIImage uploadFile2 = fileService.uploadFile(uploadFile);
		return uploadFile2;
	}

}
