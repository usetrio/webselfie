package trio.webselfie.job;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import trio.webselfie.repository.ScreenshotRepository;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ScreenshotJobTest {

	@Autowired
	private ScreenshotJob screenshotJob;

	@Mock
	protected JobExecutionContext jobExecutionContext;

	@Mock
	protected JobDataMap jobDataMap;

	@Autowired
	protected ScreenshotRepository screenshotRepository;

	@Test
	public void shouldExecuteJob() throws JobExecutionException {
		//given
		Long screenshotId = 1l;
		String url = "any-url";

		//when
		when(jobExecutionContext.getMergedJobDataMap()).thenReturn(jobDataMap);
		when(jobDataMap.getLong("id")).thenReturn(screenshotId);
		when(jobDataMap.getString("url")).thenReturn(url);
		screenshotJob.executeInternal(jobExecutionContext);

		//then
		verify(jobExecutionContext, times(1)).getMergedJobDataMap();
		verify(jobDataMap, times(1)).getLong("id");
		verify(jobDataMap, times(1)).getString("url");

		//TODO find a better way to test jobs and file operations
	}
}
