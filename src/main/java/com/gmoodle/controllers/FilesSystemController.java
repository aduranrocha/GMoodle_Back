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

import com.gmoodle.models.entity.Activity;
import com.gmoodle.models.entity.Document;
import com.gmoodle.models.entity.Users;
import com.gmoodle.models.services.IActivityService;
import com.gmoodle.models.services.IDocumentService;
import com.gmoodle.models.services.userservice.IUserService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/files")
public class FilesSystemController {

	@Autowired
	private IUserService userService;
	
	@Autowired
	private IActivityService activityService;
	
	@Autowired
	private IDocumentService documentService;
	
	/*
	 * Se utiliza para remplazar la diagonal en el fullPath de los archivos, al momento de guardarla en la base de datos no hay fallo alguno
	 * pero al momento de consultarla por GET, el sistema la interpreta como dicha ruta (que no existe) y no a la correcta para
	 * acceder a la visualización de los archivos
	 */
	private final String DIAGONAL_REPLACEMENT = "*-*";
	private final static String DIAGONAL_REPLACEMENT_STATIC = "*-*";

	/*
	 * Se crea el metodo para subir la foto de perfil Se valida si el archivo es
	 * imagen, ya que solo se permitiran imagenes Se crean los directorios
	 * automaticamente (se respalda con try catch)
	 */
	@PostMapping("/upload/profile")
	public ResponseEntity<?> UploadProfile(@RequestParam("file") MultipartFile file, @RequestParam("id") Long id) {
		Map<String, Object> response = new HashMap<>();
		Users user = userService.findById(id);
		String fullPath = "files/profiles";
		String dbPath = "";

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
			CreateDirectory(fullPath);

			// Se crea la ruta completa de la imagen
			Path pathFile = Paths.get(fullPath).resolve(fileName).toAbsolutePath();
			try {
				// Se sube el archivo al servidor
				Files.copy(file.getInputStream(), pathFile);
			} catch (IOException e) {
				// En caso de error se agrega el un mensaje de error con sus especificaciones y
				// se regresa un error 500
				e.printStackTrace();
				response.put("mensaje", "Error to upload the image");
				response.put("error", e.getCause() + " : " + e.getMessage());
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			// Borrar la imagen anterior si existe en el servidor
			deleteFile(user.getPhoto());

			// Se cambia la diagonal por otro caracter (Consultar comentarios del metodo GetFile())
			dbPath = fullPath.replace("/", DIAGONAL_REPLACEMENT);
			// Se actualiza con el nombre de la foto en el modelo Users
			user.setPhoto(dbPath + DIAGONAL_REPLACEMENT + fileName);

			// Se guarda dicha actualización en la base de datos
			userService.save(user);

			// Se agrega un mensaje de ok en la foto
			response.put("newPath", user.getPhoto());
			response.put("message", "Upload success!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}

		// Si ninguna imagen es seleccionada se retoran un mensaje de error y un estatus
		// 500
		response.put("message", "No image selected!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@PostMapping("/upload/file")
	public ResponseEntity<?> UploadFile(@RequestParam("file") MultipartFile file, @RequestParam Long idUser,
			@RequestParam Long idActivity, @RequestParam String titleFile) {

		/*
		 * Se crean las variables para guardar el tipo de archivo, nombre completo y las
		 * carpetas donde se guardara el archivo
		 */
		String mimeType = "";
		String fileName = "";
		String fullPath = "files/";
		String dbPath = "";
		
		/*
		 * Document: Para guardar los datos del archivo en la base de datos
		 * Users: Para crear la relación del usuario que subio el archivo
		 * Activity: Para crear la relación de la actividad a la cual pertenece el archivo
		 */
		Document document = null;
		Users user = userService.findById(idUser);
		Activity activity = activityService.findById(idActivity);

		Map<String, Object> response = new HashMap<>();
		// Validar si el campo archivo esta vacío
		if (!file.isEmpty()) {

			// Se intenta crear el directorio raiz de los archivos
			CreateDirectory("files");

			/*
			 * Se evalua si el archivo tiene una extension y no es un archivo bloqueado por
			 * extension en caso de que una retorne false se retorna un mensaje de error y
			 * un estatus 500
			 */
			if (HasExtensionValidation(file.getOriginalFilename()) == false
					|| BlockedExtensionsValidation(file.getOriginalFilename()) == false) {
				response.put("message", "The file was not uploaded!");
				response.put("error", "File not valid!");

				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			/*
			 * Se obtiene el nombre del archivo se le asigna un uuid para evitar sobre
			 * escribir al momento de subir los archivos se reemplazan los caracteres en
			 * blanco del nombre del archivo
			 */

			mimeType = GetMimeType(file.getContentType());

			fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename().replace(" ", "");

			fullPath += mimeType;

			// Se intenta crear el directorio donde se guardara el archivo
			CreateDirectory(fullPath);

			// Se configura la ruta del archivo
			Path pathFile = Paths.get(fullPath).resolve(fileName).toAbsolutePath();
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
			
			document = new Document();
			
			document.setActivity(activity);
			document.setIsCheck(false);
			document.setIsEnableDocument(true);
			document.setTitleDocument(titleFile);
			document.setUsers(user);
			
			
			// Se cambia la diagonal por otro caracter (Consultar comentarios del metodo GetFile())
			dbPath = fullPath.replace("/", DIAGONAL_REPLACEMENT);
			document.setPathDoucument(dbPath + DIAGONAL_REPLACEMENT + fileName);
			
			documentService.save(document);

			// El archivo se sube correctamente se retorna un mensaje al usuario con un
			// estatus 200
			response.put("message", "Uploaded success!");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		}

		// Si ningún archivo es seleccionado se retorna un mensaje de error con el
		// estatus 500
		response.put("message", "File not selected!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping("/v/{fileName:.+}")
	public ResponseEntity<Resource> GetFile(@PathVariable String fileName) {

		/*
		 * Se realiza split al nombre del archivo ya que contiene la ruta completa donde se
		 * guardo en el servidor
		 * 0: La carpeta raiz
		 * 1: La carpeta donde se guardo el archivo según su tipo
		 * 2: Nombre del archivo
		 * Se crea la ruta con las carpetas donde se guarda el archivo
		 */
		String partsFile[] = fileName.split(Pattern.quote(DIAGONAL_REPLACEMENT));
		String fullPath = partsFile[0] + "/" + partsFile[1];

		// Se obtiene la ruta absoluta y se asigna a la variable
		Path pathFile = Paths.get(fullPath).resolve(partsFile[2]).toAbsolutePath();
		// Se crea el objeto para poder obtener el archivo
		Resource resource = null;

		/*
		 * Se convierte la ruta del archivo en URI para poder mostrarlo en el código HTML
		 * En caso de error se imprime en la terminal 
		 */
		try {
			resource = new UrlResource(pathFile.toUri());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		// Se comprueba que el recurso se exista y se pueda leer
		if (!resource.exists() && !resource.isReadable()) {
			throw new RuntimeException("Image cannot be read: " + fileName);
		}

		// Se configuran las cabeceras para que la petición las reconozca como archivo y se retorna
		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");
		return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
	}

	// Clase estatica para eliminar las imagenes del servidor
	public static void deleteFile(String fileName) {
		/*
		 * Al momento de actualizar la imagen del perfil del usuario, se elimina la
		 * imagen anterior del servidor y se reemplaza por la imagen nueva.
		 */
		String partsFile[] = fileName.split(Pattern.quote(DIAGONAL_REPLACEMENT_STATIC));
		String fullPath = partsFile[0] + "/" + partsFile[1];
		// Se obtiene el nombre de la foto actual del usuario
		String file = partsFile[2];
		// Se valida que el nombre realmente exista
		if (file != null && file.length() > 0) {

			// Se obtiene la ruta de la foto
			Path pathLastPhoto = Paths.get(fullPath).resolve(file).toAbsolutePath();
			// Se convierte a tipo File(tipo archivo)
			File fileLastPhoto = pathLastPhoto.toFile();
			// Se valida si existe y si se puede leer
			if (fileLastPhoto.exists() && fileLastPhoto.canRead()) {
				// Se elimina la foto
				fileLastPhoto.delete();
			}
		}
	}

	private String GetMimeType(String mimeType) {
		/*
		 * Se obtiene el mimetype y se realiza un split para obtener la primera parte
		 * image/jpg = image text/html = text
		 */
		String[] mime = mimeType.split(Pattern.quote("/"));

		return mime[0];
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
		/*
		 * Este metodo se utiliza al momento de subir la imagen de perfil del usuario se
		 * valida para que el usuario no suba un archivo que no es una imagen
		 */
		boolean isValid = true;

		if (mimeType.indexOf("image") < 0) {
			isValid = false;
		}

		return isValid;
	}

	private boolean HasExtensionValidation(String fileName) {
		/*
		 * Se valida que el archivo subido tenga una extension
		 */
		boolean isValid = true;

		if (fileName.indexOf(".") < 0) {
			isValid = false;
		}

		return isValid;
	}

	private boolean BlockedExtensionsValidation(String fileName) {
		/*
		 * Se bloquean algunas extensiones por seguridad y se valida que la extension
		 * del archivo no se encuentre entre las extensiones bloqueadas
		 */
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

	/*public static String RenameFile(String fileName)
	{
		String[] file = fileName.split(Pattern.quote(DIAGONAL_REPLACEMENT_STATIC));
		
		String oName = file[0] + "/" + file[1] + "/" + file[2];
		String nName = file[0] + "/" + file[1] + "/disabled___" + file[2];
		String fName = nName.replace("/", DIAGONAL_REPLACEMENT_STATIC);
		
		
		File oldName = new File(oName);
		
		File newName = new File(nName);
		
		if(oldName.renameTo(newName))
		{
			return fName;
		}
		
		return "error";
		
		
	}*/
}
