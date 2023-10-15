package com.co.kr.fix;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class FixUploadController {
	
	  private static String UPLOADED_FOLDER=
		         "C:\\AAA\\Seouleducation\\HJY-STUDY\\Portfolio\\img\\uploadtest\\";
	  
	  private static String DOWNLOADED_FOLDER=
		         "..\\..\\resources\\static\\images\\";
	  

	
    
	 @GetMapping("/uploadForm")
	    public String getUploadForm() {
	        return "fix/uploadForm";
	    }
	
	   
	    @PostMapping(value = "/uploadForm", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	    @ResponseBody
	    public String upload(@RequestParam("file") List<MultipartFile> multipartFile) throws IOException {
	
	    	 StringBuilder success = new StringBuilder("-----------upload success list ---------------");
	         StringBuilder fail = new StringBuilder("--------------upload fail list------------------");
	         multipartFile.forEach(file -> {
	             log.info("{}, {}, {}", file.getContentType(),file.getOriginalFilename(),file.getSize());
	             try {
	                 fileUpload(file);
	                 success.append("\n"+file.getOriginalFilename());
	             } catch (IOException e) {
	                 e.printStackTrace();
	                 fail.append("\n"+file.getOriginalFilename());
	             }
	         });
	         String ret = success.toString()+"\n"+fail.toString();

	         return ret;
	    
	    }
	    
	    private void fileUpload(MultipartFile multipartFile) throws IOException {
	    	  File uploadFile = new File(UPLOADED_FOLDER + "/" + multipartFile.getOriginalFilename());
	          uploadFile.createNewFile();
	          try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(uploadFile));
	               BufferedInputStream bis = new BufferedInputStream(multipartFile.getInputStream());) {
	              byte[] buffer = new byte[4096 * 2];
	              int read = 0;
	              while ((read = bis.read(buffer)) != -1) {
	                  bos.write(buffer,0,buffer.length);
	              }
	              bos.flush();
	          } catch (Exception e) {
	              log.info("{}", e);
	          }
	    }

	  

		   @GetMapping("/fix/upload")
		   public String index(Model model) {
			    List<String> list = new ArrayList<String>();
			      File files = new File(UPLOADED_FOLDER);
			
			      String[] fileList = ((File) files).list();
			      for (String name : fileList) {
			    	  list.add(name);
			      }
			      model.addAttribute("list", list);
			      return "fix/upload";
		   }

		   @PostMapping("/fix/upload")
		   public String singleFileUpload
		   (@RequestParam("file") MultipartFile file, Model model) {

		      if (file.isEmpty()) {
		         model.addAttribute("warning", 
		               "Please select a file to upload");
		         return "fix/upload";
		      }

		      try {

		         byte[] bytes = file.getBytes();
		         Path path = Paths.get(UPLOADED_FOLDER + 
		               file.getOriginalFilename());
		         Files.write(path, bytes);

		         model.addAttribute("message", 
		               "You successfully uploaded '" 
		         + file.getOriginalFilename() + "'");

		      } catch (IOException e) {
		         model.addAttribute("error", "Error");
		         return "fix/upload";
		      }

		      List<String> list = new ArrayList<String>();
		      File files = new File(UPLOADED_FOLDER);
		      String[] fileList = ((File) files).list();
		      for (String name : fileList) {
		         list.add(name);
		      }
		      model.addAttribute("list", list);
		      return "fix/upload";
		   }


		   @GetMapping(path="/fix/download/{name}")
		   public ResponseEntity<Resource> download
		     (@PathVariable("name") String name) throws IOException {
		      File file = new File(DOWNLOADED_FOLDER + name);
		      Path path = Paths.get(file.getAbsolutePath());
		      ByteArrayResource resource = new ByteArrayResource
		            (Files.readAllBytes(path));

		      return ResponseEntity.ok().headers(this.headers(name))
		            .contentLength(file.length())
		            .contentType(MediaType.parseMediaType
		                  ("application/octet-stream")).body(resource);
		   }
		 
		  
		   @GetMapping(path = "/fix/delete")
		   public String delete() throws IOException {
			   return "fix/delete";
		   }

		   @PostMapping(path = "/fix/delete")
		   public String delete(@RequestParam("name") String name) 
		         throws IOException {
			   try {
			         Files.deleteIfExists(Paths.get(UPLOADED_FOLDER + name));
			         File deleteFile = new File(UPLOADED_FOLDER);
			         
			         // 파일이 존재하는지 체크 존재할경우 true, 존재하지않을경우 false
			         if(deleteFile.exists()) {
			             // 파일을 삭제
			             deleteFile.delete(); 
			             return "redirect:/fix/upload";
			      }else {
			            log.info("파일이 존재하지 않습니다.");
			        }
			}catch (IOException e) {
		         return "redirect:/fix/upload";
	      }         
			   return "redirect:/fix/fixReg";
	}
			
		
		   private HttpHeaders headers(String name) {

			      HttpHeaders header = new HttpHeaders();
			      header.add(HttpHeaders.CONTENT_DISPOSITION, 
			            "attachment; filename=" + name);
			      header.add("Cache-Control", "no-cache, no-store,"
			            + " must-revalidate");
			      header.add("Pragma", "no-cache");
			      header.add("Expires", "0");
			      return header;

			   }
		   
	

		//썸네일만드는방법
		private boolean checkImageType(File file) {

			try {
				String contentType = Files.probeContentType(file.toPath());

				return contentType.startsWith("image");

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return false;
		}
		
		
		
		private String makeFolder() {
			String str=LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
			
			String folderPath=str.replace("//", File.separator);
			
			//make folder
			File uploadPathFolder=new File(UPLOADED_FOLDER, folderPath);
			
			if(uploadPathFolder.exists()==false) {
				uploadPathFolder.mkdirs();
			}
			return folderPath;
		}
	
		@GetMapping("/display")
		@ResponseBody
		public ResponseEntity<byte[]> getFile(String fileName) {

			log.info("fileName: " + fileName);

			File file = new File(UPLOADED_FOLDER + fileName);

			log.info("file: " + file);

			ResponseEntity<byte[]> result = null;

			try {
				HttpHeaders header = new HttpHeaders();

				header.add("Content-Type", Files.probeContentType(file.toPath()));
				result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}
		
		
				
}


