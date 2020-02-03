package trio.webselfie.job;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import trio.webselfie.enumeration.ScreenshotStatusType;
import trio.webselfie.model.Screenshot;
import trio.webselfie.repository.ScreenshotRepository;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ScreenshotJobSchedulerTest {

	@MockBean
	protected Scheduler scheduler;

	@Autowired
	protected ScreenshotRepository screenshotRepository;

	@Autowired
	@InjectMocks
	protected ScreenshotJobScheduler screenshotJobScheduler;

	@Test
	public void shouldScheduleJob() throws SchedulerException {

		//given
		Screenshot screenshot = createScreenshot();

		//when
		screenshotJobScheduler.schedule(screenshot);

		//then
		verify(scheduler).scheduleJob(isA(JobDetail.class), isA(Trigger.class));
		verify(scheduler, times(1)).scheduleJob(isA(JobDetail.class), isA(Trigger.class));
	}

	private Screenshot createScreenshot() {
		Screenshot screenshot = new Screenshot();

		screenshot.setUrl("any-url");
		screenshot.setStatus(ScreenshotStatusType.PROCESSING);
		screenshot.setCreatedAt(LocalDateTime.now());
		screenshot.setPath("any-path");

		screenshotRepository.save(screenshot);

		return screenshot;
	}
}
