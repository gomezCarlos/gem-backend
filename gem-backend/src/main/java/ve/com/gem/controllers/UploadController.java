package ve.com.gem.controllers;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/upload")
public class UploadController {
	
	private static final Logger log = LoggerFactory.getLogger(UploadController.class);
	
	public static final String ROOT = "C:/Users/alonso/Desktop/ng2-admin/src/assets/img/app/profile";
	
	private final ResourceLoader resourceLoader;
	
	@Autowired
	public UploadController(ResourceLoader resourceLoader){
		this.resourceLoader = resourceLoader;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "")
	public ResponseEntity<?> provideUploadInfo(Model model) throws IOException {

		model.addAttribute("files", Files.walk(Paths.get(ROOT))
				.filter(path -> !path.equals(Paths.get(ROOT)))
				.map(path -> Paths.get(ROOT).relativize(path))
				.map(path -> linkTo(methodOn(UploadController.class).getFile(path.toString())).withRel(path.toString()))
				.collect(Collectors.toList()));

		System.out.println(model.toString());
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/{filename:.+}")
	public ResponseEntity<?> getFile(@PathVariable String filename) {
		
		try {
			return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(ROOT, filename).toString()));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "")
	public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file,
								   RedirectAttributes redirectAttributes) {
		
		if (!file.isEmpty()) { 
			try {
				//change file name
				Iterable<String> contentTypeIterable = Splitter.on("/").trimResults().omitEmptyStrings().split(file.getContentType());

				//if (Iterables.get(contentTypeIterable, 0) != "image")
				//	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				
				StringBuffer newFilename = new StringBuffer();
				newFilename.append(SecurityContextHolder.getContext().getAuthentication().getName());
				newFilename.append(".");
				newFilename.append(Iterables.get(contentTypeIterable, 1));

				//Files.copy(file.getInputStream(), Paths.get(ROOT, file.getOriginalFilename()));
				Files.copy(file.getInputStream(), Paths.get(ROOT, newFilename.toString()));
				redirectAttributes.addFlashAttribute("message",
						"You successfully uploaded " + file.getOriginalFilename() + "!");
			} catch (IOException|RuntimeException e) {
				redirectAttributes.addFlashAttribute("message", "Failued to upload " + file.getOriginalFilename() + " => " + e.getMessage());
			}
		} else {
			redirectAttributes.addFlashAttribute("message", "Failed to upload " + file.getOriginalFilename() + " because it was empty");
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}
	

}
