package trio.webselfie.rest;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import trio.webselfie.dto.ScreenshotRequestDto;
import trio.webselfie.dto.ScreenshotResponseDto;
import trio.webselfie.service.ScreenshotService;

import java.io.IOException;

@Controller
@RestController
@RequestMapping("/screenshots")
public class ScreenshotController {

	private final ScreenshotService screenshotService;

	public ScreenshotController(ScreenshotService screenshotService) {
		this.screenshotService = screenshotService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ScreenshotResponseDto createScreenshot(@RequestBody ScreenshotRequestDto screenshotRequestDto) {
		return screenshotService.createScreenshot(screenshotRequestDto);
	}

	@GetMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ScreenshotResponseDto getScreenshot(@PathVariable Long id) {
		return screenshotService.getScreenshot(id);
	}

	@GetMapping(value = "/{id}/downloads")
	public ResponseEntity<Resource> download(@PathVariable Long id) throws IOException {
		InputStreamResource resource= screenshotService.getResource(id);

		return ResponseEntity.ok()
				.contentType(MediaType.IMAGE_JPEG)
				.body(resource);
	}
}
