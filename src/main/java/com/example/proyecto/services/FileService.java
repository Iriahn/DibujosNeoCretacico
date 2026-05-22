package com.example.proyecto.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*; //Files, Path, Paths, StandardCopyOption

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FileService { 

  private final Path rootLocation = Paths.get("uploadFiles");

  public String storeFile(MultipartFile file, String id) throws RuntimeException { 
    if (file.isEmpty()) throw new RuntimeException("Fichero vacio");
    String filename = StringUtils.cleanPath(file.getOriginalFilename());
    if (filename.contains (".."))
      throw new RuntimeException("Fichero incorrecto");
    String extension = StringUtils.getFilenameExtension(filename); 
    String storedFilename = id + "." + extension; // -------------------------------------------
    try (InputStream inputStream = file.getInputStream()) { 
      Files.copy(inputStream, this.rootLocation.resolve(storedFilename), 
                    StandardCopyOption.REPLACE_EXISTING);
      return storedFilename;
    }catch (IOException ioe) {
      throw new RuntimeException("Error en escritura");
    }
  }

  public Resource loadAsResource (String filename) throws RuntimeException {
    try {
      Path file = rootLocation.resolve(filename);
      Resource resource = new UrlResource(file.toUri());
      if (resource.exists() || resource.isReadable())
        return resource;
      else
        throw new RuntimeException("Error I0");
    }catch (Exception e) {
      throw new RuntimeException("Error I0");
    }
  }

}