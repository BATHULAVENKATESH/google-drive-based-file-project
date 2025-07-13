package com.google_file_storage.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

	private final Path root = Paths.get("C:\\Users\\bathu\\Downloads\\uploads");

	public void init() throws IOException {
		Files.createDirectories(root);
	}

	public String save(MultipartFile file, Long userId) throws IOException {
		if (file == null || file.isEmpty()) {
			throw new IOException("Uploaded file is empty or null");
		}

		String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
		
		Path filePath = root.resolve(filename);

		Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

		return filePath.toString();
	}

	public Resource load(String filename) throws MalformedURLException {

		Path file = root.resolve(filename);
		return new UrlResource(file.toUri());

	}

	public boolean delete(String filename) throws IOException {
		
		return Files.deleteIfExists(root.resolve(filename));
	}
}
