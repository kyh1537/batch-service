package com.keycut.batchservice;

import java.time.LocalDateTime;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class BatchScheduler {

	private final JobLauncher jobLauncher;
	private final JobRegistry jobRegistry;

	@Scheduled(cron = "0 0 0 * * *")
	public void runJob() {
		try {
			Job job = jobRegistry.getJob("exchangeJob");
			JobParametersBuilder jobParam = new JobParametersBuilder().addLocalDateTime("time", LocalDateTime.now());
			jobLauncher.run(job, jobParam.toJobParameters());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
}
