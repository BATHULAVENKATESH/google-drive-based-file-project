package com.google_file_storage.Controller;



import org.springframework.http.MediaType;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google_file_storage.Entity.FileEntity;
import com.google_file_storage.Repository.FileRepository;
import com.google_file_storage.Service.FileStorageService;

import java.nio.file.Path;

import org.springframework.core.io.Resource; // ✅ CORRECT
import org.springframework.core.io.UrlResource;


@CrossOrigin(origins = "http://localhost:3000")

@RestController
@RequestMapping("/api/files")

public class FileController {
	
	@Autowired
	private FileStorageService storageService;
	
	@Autowired
	private FileRepository fileRepo;

	@PostMapping("/upload")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam Long userId)
			throws IOException {
		
		String path = storageService.save(file, userId);
		
		FileEntity entity = new FileEntity();
		
		entity.setName(file.getOriginalFilename());
		
		entity.setPath(path);
		
		entity.setType(file.getContentType());
		
		entity.setSize(file.getSize());
		
		entity.setUserId(userId);
		
		fileRepo.save(entity);
		
		return ResponseEntity.ok("Uploaded successfully");
	}
	
	
	@GetMapping
	public List<FileEntity> getAllFiles() {
		
	    return fileRepo.findAll();
	}
	

	
	
	@GetMapping("/download/{id}")
	public ResponseEntity<Resource> download(@PathVariable Long id) throws MalformedURLException {
		
	    FileEntity file = fileRepo.findById(id)
	    		
	                        .orElseThrow(() -> new RuntimeException("File not found with id: " + id));

	    Path filePath = (Path) Paths.get(file.getPath()); 
	    
	    Resource resource = new UrlResource(((java.nio.file.Path) filePath).toUri());

	    if (!resource.exists() || !resource.isReadable()) {
	    	
	        throw new RuntimeException("Could not read the file!");
	    }

	    
	    return ResponseEntity.ok()
	    	    .contentType(MediaType.parseMediaType(file.getType())) 
	    	    
	    	    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
	    	    
	    	    .body(resource);

	}
	
	
	@DeleteMapping("/delete/user/{userId}")
	
	public ResponseEntity<String> deleteByUserId(@PathVariable Long userId) throws IOException {
		
	    List<FileEntity> files = fileRepo.findByUserId(userId);

	    if (files.isEmpty()) {
	    	
	        return ResponseEntity.status(404).body("No files found for userId: " + userId);
	    }

	    for (FileEntity file : files) {
	    	
	        storageService.delete(Paths.get(file.getPath()).getFileName().toString());
	        
	        fileRepo.deleteById(file.getId());
	    }

	    return ResponseEntity.ok("Deleted all files for userId: " + userId);
	}
	
	
	
	@DeleteMapping("/delete/{id}")
	
	public ResponseEntity<String> deleteById(@PathVariable Long id) throws IOException {
		
	    FileEntity file = fileRepo.findById(id)
	    		
	        .orElseThrow(() -> new RuntimeException("File not found with id: " + id));

	    storageService.delete(Paths.get(file.getPath()).getFileName().toString());
	    
	    fileRepo.deleteById(id);

	    return ResponseEntity.ok("Deleted file with id: " + id);
	}




}