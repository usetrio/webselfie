package trio.webselfie.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import trio.webselfie.converter.ScreenshotConverter;
import trio.webselfie.dto.ScreenshotRequestDto;
import trio.webselfie.dto.ScreenshotResponseDto;
import trio.webselfie.enumeration.ScreenshotStatusType;
import trio.webselfie.job.ScreenshotJobScheduler;
import trio.webselfie.model.Screenshot;
import trio.webselfie.repository.ScreenshotRepository;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class ScreenshotService {

	private final ScreenshotRepository screenshotRepository;
	private final ScreenshotConverter screenshotConverter;
	private final ScreenshotJobScheduler screenshotJobScheduler;

	public ScreenshotService(ScreenshotRepository screenshotRepository, ScreenshotConverter screenshotConverter, ScreenshotJobScheduler screenshotJobScheduler) {
		this.screenshotRepository = screenshotRepository;
		this.screenshotConverter = screenshotConverter;
		this.screenshotJobScheduler = screenshotJobScheduler;
	}

	public ScreenshotResponseDto createScreenshot(ScreenshotRequestDto screenshotRequestDto) {
		Screenshot newScreenshot = new Screenshot();

		newScreenshot.setUrl(screenshotRequestDto.getUrl());
		newScreenshot.setStatus(ScreenshotStatusType.PROCESSING);
		newScreenshot.setCreatedAt(LocalDateTime.now());

		Screenshot savedScreenshot = screenshotRepository.save(newScreenshot);

		screenshotJobScheduler.schedule(savedScreenshot);

		return screenshotConverter.toScreenshotDto(savedScreenshot);
	}

	public ScreenshotResponseDto getScreenshot(Long id) {
		return screenshotRepository.findById(id)
				.map(screenshot -> screenshotConverter.toScreenshotDto(screenshot))
				.orElse(null);

	}

	public InputStreamResource getResource(Long id) throws IOException {
		Screenshot screenshot = screenshotRepository.findById(id).get();

		return new InputStreamResource(new FileInputStream(new File(screenshot.getPath())));
	}
}
