package trio.webselfie.job;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import trio.webselfie.enumeration.ScreenshotStatusType;
import trio.webselfie.model.Screenshot;
import trio.webselfie.repository.ScreenshotRepository;

import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class ScreenshotJob extends QuartzJobBean {

	Logger logger = LoggerFactory.getLogger(ScreenshotJob.class);

	private final ScreenshotRepository screenshotRepository;
	private WebDriver driver = null;

	public ScreenshotJob(ScreenshotRepository screenshotRepository) {
		this.screenshotRepository = screenshotRepository;
	}

	@PostConstruct
	private void postConstruct() {
		WebDriverManager.chromedriver().setup();
	}

	@Override protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

		JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();

		Long screenshotId = jobDataMap.getLong("id");
		String url = jobDataMap.getString("url");

		Screenshot screenshot = screenshotRepository.findById(screenshotId).get();

		try {
			String fileName = "screenshot" + screenshotId + LocalDate.now().toString() + ".jpeg";
			Path filePath = Paths.get("tmp");

			if (Files.notExists(filePath)) {
				Files.createDirectories(filePath);
			}

			driver = new ChromeDriver();
			driver.get(url);

			final File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			final File screenShotOutputFile = new File(filePath + File.separator + fileName);

			FileUtils.copyFile(scrFile, screenShotOutputFile);

			screenshot.setPath(screenShotOutputFile.getPath());
			screenshot.setStatus(ScreenshotStatusType.SUCCESS);
		} catch (Exception e) {
			screenshot.setStatus(ScreenshotStatusType.FAIL);
			logger.error("Error while executing screenshotJob " + screenshotId + ": " + e.getMessage());
		} finally {
			driver.quit();
		}

		screenshot.setUpdatedAt(LocalDateTime.now());
		screenshotRepository.save(screenshot);
	}
}
