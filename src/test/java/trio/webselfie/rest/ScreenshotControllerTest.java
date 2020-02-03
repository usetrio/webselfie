package trio.webselfie.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import trio.webselfie.dto.ScreenshotRequestDto;
import trio.webselfie.dto.ScreenshotResponseDto;
import trio.webselfie.enumeration.ScreenshotStatusType;
import trio.webselfie.service.ScreenshotService;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class ScreenshotControllerTest {

	@Autowired
	protected MockMvc mockMvc;

	@MockBean
	protected ScreenshotService screenshotService;

	@Before
	public void setUp() {
		Mockito.reset(screenshotService);
	}

	@Test
	public void shouldCreateScreenshot() throws Exception {

		// given
		String commentBody = "{\"url\":\"https://www.google.com\"}";
		ScreenshotRequestDto requestDto = new ScreenshotRequestDto();

		requestDto.setUrl("https://www.google.com");

		ScreenshotResponseDto screenshotResponseDto = createScreenshotDto();

		// when
		when(screenshotService.createScreenshot(requestDto)).thenReturn(screenshotResponseDto);

		//then
		mockMvc.perform(post("/screenshots")
				.content(commentBody)
				.contentType(APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated());
	}

	@Test
	public void shouldReturnScreenshotWithStatusSuccess() throws Exception {

		// given
		ScreenshotResponseDto screenshotResponseDto = createScreenshotDto();

		// when
		when(screenshotService.getScreenshot(1L)).thenReturn(screenshotResponseDto);

		//then
		mockMvc.perform(get("/screenshots/1")
				.contentType(APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("id").exists())
				.andExpect(jsonPath("url").exists())
				.andExpect(jsonPath("status").exists())
				.andExpect(jsonPath("createdAt").exists());

	}

	@Test
	public void shouldDownloadScreenshot() throws Exception {

		// given

		// when
		when(screenshotService.getResource(1L)).thenReturn(any(InputStreamResource.class));

		//then
		mockMvc.perform(get("/screenshots/1/downloads/")
				.contentType(APPLICATION_OCTET_STREAM_VALUE)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	private ScreenshotResponseDto createScreenshotDto() {
		ScreenshotResponseDto screenshotResponseDto = new ScreenshotResponseDto();
		screenshotResponseDto.setId(1L);
		screenshotResponseDto.setUrl("https://www.google.com");
		screenshotResponseDto.setStatus(ScreenshotStatusType.PROCESSING);
		screenshotResponseDto.setCreatedAt(LocalDateTime.now());
		return screenshotResponseDto;
	}

}
