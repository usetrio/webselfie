package trio.webselfie.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import trio.webselfie.converter.ScreenshotConverter;
import trio.webselfie.dto.ScreenshotRequestDto;
import trio.webselfie.dto.ScreenshotResponseDto;
import trio.webselfie.enumeration.ScreenshotStatusType;
import trio.webselfie.job.ScreenshotJobScheduler;
import trio.webselfie.model.Screenshot;
import trio.webselfie.repository.ScreenshotRepository;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ScreenshotServiceTest {

	@MockBean
	protected ScreenshotJobScheduler screenshotJobScheduler;

	@Autowired
	protected ScreenshotRepository screenshotRepository;

	@Autowired
	protected ScreenshotConverter screenshotConverter;

	@Autowired
	@InjectMocks
	protected ScreenshotService screenshotService;

	@Test
	public void shouldCreateScreenshot() {

		//given
		ScreenshotRequestDto requestDto = new ScreenshotRequestDto();
		requestDto.setUrl("any-url");

		//when
		doNothing().when(screenshotJobScheduler).schedule(isA(Screenshot.class));
		ScreenshotResponseDto dto = screenshotService.createScreenshot(requestDto);

		//then
		assertThat("Screenshot id shouldn't be null", dto.getId(), notNullValue());
	}

	@Test
	public void shouldGetScreenshot() {

		//given
		Screenshot screenshot = createScreenshot();

		//when
		ScreenshotResponseDto screenshotResponseDto = screenshotService.getScreenshot(screenshot.getId());

		//then
		assertThat(screenshotResponseDto, notNullValue());
		assertThat(screenshotResponseDto.getId(), equalTo(screenshot.getId()));
		assertThat(screenshotResponseDto.getStatus(), equalTo(screenshot.getStatus()));
		assertThat(screenshotResponseDto.getUrl(), equalTo(screenshot.getUrl()));
		assertThat(screenshotResponseDto.getCreatedAt(), equalTo(screenshot.getCreatedAt()));
	}

	private Screenshot createScreenshot() {
		Screenshot screenshot = new Screenshot();

		screenshot.setUrl("any-url");
		screenshot.setStatus(ScreenshotStatusType.PROCESSING);
		screenshot.setCreatedAt(LocalDateTime.now());
		screenshot.setPath("test-tmp/test-file.txt");

		screenshotRepository.save(screenshot);

		return screenshot;
	}
}
