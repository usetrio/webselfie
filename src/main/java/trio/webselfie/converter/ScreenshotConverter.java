package trio.webselfie.converter;

import org.springframework.stereotype.Component;
import trio.webselfie.dto.ScreenshotResponseDto;
import trio.webselfie.model.Screenshot;

@Component
public class ScreenshotConverter {

	public ScreenshotResponseDto toScreenshotDto(Screenshot screenshot) {
		ScreenshotResponseDto screenshotResponseDto = new ScreenshotResponseDto();

		screenshotResponseDto.setId(screenshot.getId());
		screenshotResponseDto.setStatus(screenshot.getStatus());
		screenshotResponseDto.setUrl(screenshot.getUrl());
		screenshotResponseDto.setCreatedAt(screenshot.getCreatedAt());
		screenshotResponseDto.setUpdatedAt(screenshot.getUpdatedAt());

		return screenshotResponseDto;
	}
}
