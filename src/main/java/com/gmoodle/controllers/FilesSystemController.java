package com.gmoodle.controllers;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gmoodle.models.entity.Users;
import com.gmoodle.models.services.userservice.IUserService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/files")
public class FilesSystemController {

	@Autowired
	private IUserService userService;

	/*
	 * Se crea el metodo para subir la foto de perfil Se valida si el archivo es
	 * imagen, ya que solo se permitiran imagenes Se crean los directorios
	 * automaticamente (se respalda con try catch)
	 */
	@PostMapping("/upload/profile")
	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, @RequestParam("id") Long id) {
		Map<String, Object> response = new HashMap<>();
		Users user = userService.findById(id);

		// Validar si el campo archivo esta vacío
		if (!file.isEmpty()) {

			// Se intenta crear el directorio raiz de los archivos
			CreateDirectory("files");

			if (isImageValidation(file.getContentType()) == false) {
				response.put("message", "The file was not uploaded");
				response.put("error", "Only images allow");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			/*
			 * Se obtiene el nombre del archivo Se le asigna un uuid para evitar
			 * duplicidades al momento de subir los archivos se reemplazan los caracteres en
			 * blanco del nombre del archivo
			 */
			String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename().replace(" ", "");
			// Se configura la ruta del archivo

			// Se intenta crear el directorio raiz de los archivos
			CreateDirectory("files/profiles");

			Path pathFile = Paths.get("files/profiles").resolve(fileName).toAbsolutePath();
			try {
				// Se sube el archivo al servidor
				Files.copy(file.getInputStream(), pathFile);
			} catch (IOException e) {
				// En caso de error se agrega el un mensaje de error con sus especificaciones y
				// se regresa un error 500
				e.printStackTrace();
				response.put("mensaje", "Error al subir la imagen");
				response.put("error", e.getCause() + " : " + e.getMessage());
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			// Borrar la imagen anterior si existe en el servidor
			deleteLastImage(user.getPhoto());

			// Se actualiza con el nombre de la foto en el modelo Users
			user.setPhoto((String) fileName);

			// Se guarda dicha actualización en la base de datos
			userService.save(user);

			// Se agrega un mensaje de ok en la foto
			response.put("user", user);
			response.put("message", "Upload success!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}

		response.put("message", "No image selected!");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping("/upload/file")
	public ResponseEntity<?> UploadFile(@RequestParam("file") MultipartFile file, @RequestParam Long idUser,
			@RequestParam Long idActivity) {

		Map<String, Object> response = new HashMap<>();
		// Validar si el campo archivo esta vacío
		if (!file.isEmpty()) {

			// Se intenta crear el directorio raiz de los archivos
			CreateDirectory("files");

			if (HasExtensionValidation(file.getOriginalFilename()) == false
					|| BlockedExtensionsValidation(file.getOriginalFilename()) == false) {
				response.put("message", "The file was not uploaded!");
				response.put("error", "Image not valid!");

				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			/*
			 * Se obtiene el nombre del archivo Se le asigna un uuid para evitar
			 * duplicidades al momento de subir los archivos se reemplazan los caracteres en
			 * blanco del nombre del archivo
			 */

			String mimeType = GenerateDirectoryByMime(file.getContentType());

			String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename().replace(" ", "");

			CreateDirectory("files/" + mimeType);

			// Se configura la ruta del archivo
			Path pathFile = Paths.get("files/" + mimeType).resolve(fileName).toAbsolutePath();
			try {
				// Se sube el archivo al servidor
				Files.copy(file.getInputStream(), pathFile);
			} catch (IOException e) {
				// En caso de error se agrega el un mensaje de error con sus especificaciones y
				// se regresa un error 500
				e.printStackTrace();
				response.put("mensaje", "Error el archivo");
				response.put("error", e.getCause() + " : " + e.getMessage());
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			response.put("message", "Imagen subida con exito!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}

		response.put("message", "No se ha seleccionado una imagen para subir");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping("/p/{fileName:.+}")
	public ResponseEntity<Resource> watchPhoto(@PathVariable String fileName) {
		Path pathFile = Paths.get("files/profiles").resolve(fileName).toAbsolutePath();
		Resource resource = null;

		try {
			resource = new UrlResource(pathFile.toUri());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		if (!resource.exists() && !resource.isReadable()) {
			throw new RuntimeException("No se pudo cargar la imagen: " + fileName);
		}

		HttpHeaders header = new HttpHeaders();

		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");

		return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
	}

	// Clase estatica para eliminar las imagenes del servidor
	public static void deleteLastImage(String photo) {
		// Se obtiene el nombre de la foto actual del usuario
		String lastPhoto = photo;
		// Se valida que el nombre realmente exista
		if (lastPhoto != null && lastPhoto.length() > 0) {

			// Se obtiene la ruta de la foto
			Path pathLastPhoto = Paths.get("files").resolve(lastPhoto).toAbsolutePath();
			// Se convierte a tipo File(tipo archivo)
			File fileLastPhoto = pathLastPhoto.toFile();
			// Se valida si existe y si se puede leer
			if (fileLastPhoto.exists() && fileLastPhoto.canRead()) {
				// Se elimina la foto
				fileLastPhoto.delete();
			}
		}
	}

	private String GenerateDirectoryByMime(String mimeType) {
		String[] mime = mimeType.split(Pattern.quote("/"));

		return mime[mime.length - 1];
	}

	private void CreateDirectory(String path) {
		/*
		 * Se intenta crear el directorio (variable path) Si el directorio ya esta
		 * creado no se sobreescribe o se afecta
		 */
		try {
			Files.createDirectories(Paths.get(path));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	private boolean isImageValidation(String mimeType) {
		boolean isValid = true;

		if (mimeType.indexOf("image") < 0) {
			isValid = false;
		}

		return isValid;
	}

	private boolean HasExtensionValidation(String fileName) {
		boolean isValid = true;

		if (fileName.indexOf(".") < 0) {
			isValid = false;
		}

		return isValid;
	}

	private boolean BlockedExtensionsValidation(String fileName) {
		boolean isValid = true;
		List<String> blockedExtensions = new ArrayList<>();
		String[] arr = fileName.split(Pattern.quote("."));
		String ext = arr[arr.length - 1];

		blockedExtensions.add("exe");
		blockedExtensions.add("bat");

		for (String e : blockedExtensions) {
			if (e.equals(ext))
				isValid = false;
		}

		return isValid;
	}
}
