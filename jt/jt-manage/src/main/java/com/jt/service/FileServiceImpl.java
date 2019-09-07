package com.jt.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jt.vo.EasyUIImage;

@Service
@PropertySource("classpath:/properties/image.properties")
public class FileServiceImpl implements FileService {

	@Value("${image.localFileDir}")
	private String localFileDir;
	@Value("${image.urlPath}")
	private String urlPath;
	/**
	 * 判断文件是否为图片
	 */
	@Override
	public EasyUIImage uploadFile(MultipartFile uploadFile) {
		EasyUIImage easyUIImage = new EasyUIImage();
		String fileName = uploadFile.getOriginalFilename();
		fileName = fileName.toLowerCase();
		if (!fileName.matches("^.+\\.(jpg|png|gif)$")) {
			easyUIImage.setError(1);
			return easyUIImage;
		}
		// 3.获取图片的宽高
		try {
			BufferedImage bufferedImage = 
					ImageIO.read(uploadFile.getInputStream());
			int height = bufferedImage.getHeight();
			int width = bufferedImage.getWidth();
			// 如果宽高中有属性为0 则程序终止
			if (height == 0 || width == 0) {
				easyUIImage.setError(1);
				return easyUIImage;
			}
			easyUIImage.setHeight(height).setWidth(width);
			String format = 
					new SimpleDateFormat("yyyy/MM/dd").format(new Date());
			String localDir = localFileDir +format;
			File file = new File(localDir);
			if (!file.exists()) {
				file.mkdirs();
			}
			
			String uuid = UUID.randomUUID().toString().replace("-", "");
			String substring = fileName.substring(fileName.lastIndexOf("."));
			String newFileName = uuid + substring;

			String realFilePath = localDir + "/" + newFileName;
			File file2 = new File(realFilePath);
			uploadFile.transferTo(file2);
			
			String url = urlPath + format + "/" + newFileName;
			easyUIImage.setUrl(url);		
			
		} catch (Exception e) {
			e.printStackTrace();
			easyUIImage.setError(1);// 程序出错 终止
		}
		return easyUIImage;
	}
}
