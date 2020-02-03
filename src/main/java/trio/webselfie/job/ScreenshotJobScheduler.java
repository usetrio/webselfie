package trio.webselfie.job;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import trio.webselfie.model.Screenshot;

import java.util.UUID;

@Component
public class ScreenshotJobScheduler {

	Logger logger = LoggerFactory.getLogger(ScreenshotJobScheduler.class);

	private final Scheduler scheduler;

	public ScreenshotJobScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public void schedule(Screenshot screenshot) {
		JobDetail jobDetail = buildJobDetail(screenshot);
		Trigger trigger = buildJobTrigger(jobDetail);

		try {
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			logger.error("Error while scheduling screenshotJob: " + e.getMessage());
		}
	}

	private JobDetail buildJobDetail(Screenshot screenshot) {
		JobDataMap jobDataMap = new JobDataMap();

		jobDataMap.put("id", screenshot.getId());
		jobDataMap.put("url", screenshot.getUrl());

		return JobBuilder.newJob(ScreenshotJob.class)
				.withIdentity(UUID.randomUUID().toString(), "screenshot-jobs")
				.withDescription("Create screenshot job")
				.usingJobData(jobDataMap)
				.storeDurably()
				.build();
	}

	private Trigger buildJobTrigger(JobDetail jobDetail) {
		return TriggerBuilder.newTrigger()
				.forJob(jobDetail)
				.withIdentity(jobDetail.getKey().getName(), "screenshot-triggers")
				.withDescription("Create screenshot trigger")
				.withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow()).build();
	}
}
