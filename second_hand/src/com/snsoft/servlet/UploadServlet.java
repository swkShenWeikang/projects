package com.snsoft.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;

import com.snsoft.dao.UploadDao;
import com.snsoft.util.AllConstant;
import com.snsoft.util.JsonUtils;
import com.snsoft.util.UUIDUtils;

/**
 * 
 * @copyright ：神农大学生软件创新中心 版权所有 © 2017
 * 
 * @author 14信息慎伟康
 * 
 * @version 1.0
 * 
 * @date 2017年7月8日 上午1:33:32
 * 
 * @Description TODO
 *		图片上传接口
 */
@WebServlet("/upload")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UploadServlet() {
        super();
    }

    //文件上传临时文件夹
    private String tempPath = "/temp/";
//    private String tempPath = "/WEB-INF/temp/";//WEB-INF目录下更安全
    //文件上传真实文件夹
    private String attachPath = "/attach/";
    //文件大小，限制2M
    private long fileSize = 1024 * 1024 * 2;
    //文件类型，只能上传图片
    private String[] fileType = new String[] {"BMP", "JPG", "JPEG", "PNG" , "GIF"};
    
	/**
	 * 图片下载接口
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(JsonUtils.jsonResponse(null, AllConstant.CODE_ERROR, "请求异常！"));	
	}

	/**
	 * 图片上传接口。
	 * 接收由客户端提交的临时图片，为其生成唯一的文件名，保存到 /WEB-INF/temp目录下，并且回传图片保存名。
	 * 调用一次只能上传一张图片。
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");
		
		//返回结果
		HashMap<String, String> result = new HashMap<String, String>();
		//取得上传使用的临时目录
    	String uploadPath = request.getSession().getServletContext().getRealPath(tempPath);
    	
    	//创建上传文件工厂
		DiskFileItemFactory factory = new DiskFileItemFactory();
		//设置内存中缓存区的大小，默认10K
		factory.setSizeThreshold(300*1024);
		//设置上传文件临时存放的目录
		factory.setRepository(new File(uploadPath));
		
		//创建上传文件对象[核心]
		ServletFileUpload upload = new ServletFileUpload(factory);
		//设置上传文件的中文编码方式
		upload.setHeaderEncoding("UTF-8");
		
		try {
			/**
			 * 以MIME协议上传的文件，解析request中的所有上传内容
			 * 每个内容封装成一个对象FileItem，
			 * FileItem代表普通字段和上传字段二类
			 */
			List<FileItem> fileItemList  = upload.parseRequest(request);
			for (FileItem fileItem : fileItemList) {
				if(fileItem.isFormField()){
					//是普通表单字段，不是文件上传字段，因接口只有文件流，故不处理
				}else{
					//必定是上传字段
					String realFileName = fileItem.getName();
					int index = realFileName.lastIndexOf("\\");
					if(index>=0){
						//IE浏览器，截取真实文件名
						realFileName = realFileName.substring(index+1);
					}
					
					//获得文件后缀
					String suffix = getSuffix(realFileName);
					
					//判断文件是否为图片
					boolean flag = isPicture(suffix);
					if(!flag){
						response.getWriter().write(JsonUtils.jsonResponse(null, AllConstant.CODE_ERROR, "只能上传图片（支持BMP、JPG、JPEG、PNG、GIF等格式）"));	
						return;
					}
					
					//判断文件大小是否超出限制
					if(fileItem.getSize() > fileSize){
						response.getWriter().write(JsonUtils.jsonResponse(null, AllConstant.CODE_ERROR, "图片大小不能超过2M!"));	
						return;
					}
					
					//通过文件后缀名获取一个UUID文件名
					String uuidFileName = getUuidFileName(suffix);
					
					//取得文件输入流
					InputStream is = fileItem.getInputStream();
					//取得文件输出流
					OutputStream os = new FileOutputStream(uploadPath + File.separator + uuidFileName);
					//文件拷贝
					byte[] buff = new byte[1024];
					int len = 0;
					while((len = is.read(buff)) > 0){
						os.write(buff, 0, len);
						os.flush();
					}
					if(os != null){
						os.close();
					}
					if(is != null){
						is.close();
					}
					//将上传文件产生的临时文件删除，在关闭io流之后删除
					fileItem.delete();
					result.put("saveName", uuidFileName);
					
					
					response.getWriter().write(JsonUtils.jsonResponse(result, AllConstant.CODE_SUCCESS, "图片上传成功!"));	
					return;
				}
			}
			
		} catch (FileUploadException e) {
			e.printStackTrace();
			response.getWriter().write(JsonUtils.jsonResponse(null, AllConstant.CODE_ERROR, "上传图片失败！"));	
			return;
		}
		
	}
	
	/**
	 * 获取文件名后缀
	 * @param realFileName
	 * @return
	 */
	private String getSuffix(String realFileName) {
		int index = realFileName.lastIndexOf(".");
		return realFileName.substring(index + 1);
	}
	
	/**
	 * 判断文件是否为图片文件
	 * @param suffix
	 * @return
	 */
	private boolean isPicture(String suffix) {
		boolean flag = false;
		for (String type : fileType) {
			if(type.equalsIgnoreCase(suffix)){
				flag = true;
			}
		}
		return flag;
	}
	
	/**
	 * 根据后缀名获取UUID文件名
	 * @param suffix
	 * @return
	 */
	private String getUuidFileName(String suffix) {
		return UUIDUtils.getUUID() + "." + suffix;
	}

	/**
	 * 获取attach文件夹子目录
	 * @param attach
	 * @param saveName
	 * @return
	 */
	private String getChildDirectoryPath(String attach, String saveName) {
		int code = saveName.hashCode();//用hashcode做位与运算
		int dir1 = code & 0x1F;//获得第1层目录
		int dir2 = code >> 5 & 0x1F;//获得第2层目录
		File file = new File(attach + File.separator + dir1 + File.separator + dir2);
		//如果该目录未存在
		if(!file.exists()){
			//一次性创建2层目录
			file.mkdirs();
		}
		
		return  dir1 + File.separator + dir2 + File.separator;
	}

	/**
	 * 将临时图片从 temp目录下移动到attach目录下，并保存记录到数据库中
	 * @param request
	 * @param saveName	图片保存名
	 * @param parentId	图片父级ID
	 * @return
	 */
	public boolean saveAttach(HttpServletRequest request, String saveName, String parentId){
		
		if(request == null || StringUtils.isEmpty(saveName) || StringUtils.isEmpty(parentId)){
			return false;
		}
		//取得上传使用的临时目录
    	String temp = request.getSession().getServletContext().getRealPath(tempPath);
    	//取得上传使用的附件目录
    	String attach = request.getSession().getServletContext().getRealPath(attachPath);
    	File tempFile = new File(temp + File.separator + saveName);
		if(!tempFile.exists()){// 临时文件不存在
			return false;
		}
		
		//通过位运算换算出attach目录下的子目录
		String childDirectoryPath = getChildDirectoryPath(attach, saveName);
		
		InputStream is = null;
		OutputStream os = null;
    	try {
			is = new FileInputStream(tempFile);
			os = new FileOutputStream(attach + File.separator + childDirectoryPath + saveName);
			byte[] buff = new byte[1024];
			int len = 0;
			while((len = is.read(buff)) > 0){
				os.write(buff, 0, len);
				os.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(os != null){
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
    	tempFile.delete();//删除临时文件
    	
    	//数据库添加记录
    	HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", UUIDUtils.getUUID());
		params.put("parentId", parentId);
		params.put("saveName", childDirectoryPath + saveName);
		
		UploadDao ud = new UploadDao();
		boolean flag = ud.addAttach(params);
		if(!flag){
			return false;
		}
		
		return true;
	}
	
	
	

	/**
	 * 将附件实体从attach文件夹中删除，并删除记录
	 * @param request	
	 * @param saveName	图片保存名
	 */
	public boolean deleteAttach(HttpServletRequest request, String saveName){
		if(request == null || StringUtils.isEmpty(saveName)){
			return false;
		}
		//取得上传使用的附件目录
    	String attach = request.getSession().getServletContext().getRealPath(attachPath);
		File file = new File(attach + File.separator + saveName);
    	if(!file.exists()){
    		System.out.println("wu!!!");
    		return false;
    	}
    	file.delete();//删除图片
    	//删除图片记录
    	UploadDao ud = new UploadDao();
		boolean flag = ud.deleteAttach(saveName);
    	
		if(!flag){
			return false;
		}
		return true;
	}

}
