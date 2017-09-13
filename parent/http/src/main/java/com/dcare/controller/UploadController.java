/**
 * 
 */
package com.dcare.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dcare.common.message.Packet;

/**
 * @author sampson
 *
 */
@Controller
public class UploadController extends BaseController {

	private static Logger logger = Logger.getLogger(UploadController.class);
	
	@RequestMapping(value = "/upload")
	public void upload(@RequestParam(value="sign",required=false) MultipartFile file,HttpServletRequest request,HttpServletResponse response){
	 	logger.info("开始上传");
        String path = request.getSession().getServletContext().getRealPath("upload");  
        String fileName = file.getOriginalFilename();  
        
        logger.info("path is :" + path);  
        
        File targetFile = new File(path, fileName);  
        if(!targetFile.exists()){  
            targetFile.mkdirs();  
        }  
  
        //保存  
        try {  
            file.transferTo(targetFile); 
            logger.info("保存文件成功");
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        
        Packet rtvPacket = new Packet();
		rtvPacket.setToken(null);
		rtvPacket.setCode(1);
		
		logger.info("上传文件结束 ，返回的信息是  ： " + rtvPacket);
		
		returnJson(response, rtvPacket);
	}
	
	
}
