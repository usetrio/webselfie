package trio.webselfie.converter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import trio.webselfie.dto.ScreenshotResponseDto;
import trio.webselfie.enumeration.ScreenshotStatusType;
import trio.webselfie.model.Screenshot;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ScreenshotConverterTest {

	@MockBean
	protected ScreenshotConverter screenshotConverter;

	@Test
	public void shouldConvertScreenshotToScreenshotDto() throws Exception {

		// given
		LocalDateTime now = LocalDateTime.now();
		Screenshot screenshot = new Screenshot();
		ScreenshotResponseDto expected = new ScreenshotResponseDto();

		screenshot.setId(Long.valueOf(1));
		screenshot.setUrl("any-url");
		screenshot.setStatus(ScreenshotStatusType.PROCESSING);
		screenshot.setCreatedAt(now);
		screenshot.setUpdatedAt(now);

		expected.setId(Long.valueOf(1));
		expected.setUrl("any-url");
		expected.setStatus(ScreenshotStatusType.PROCESSING);
		expected.setCreatedAt(now);
		expected.setUpdatedAt(now);

		//
		when(screenshotConverter.toScreenshotDto(screenshot)).thenCallRealMethod();

		//then
		ScreenshotResponseDto result = screenshotConverter.toScreenshotDto(screenshot);
		assertEquals(expected.getId(), result.getId());
		assertEquals(expected.getUrl(), result.getUrl());
		assertEquals(expected.getStatus(), result.getStatus());
		assertEquals(expected.getCreatedAt(), result.getCreatedAt());
		assertEquals(expected.getUpdatedAt(), result.getUpdatedAt());
	}
}
