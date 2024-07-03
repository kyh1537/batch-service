package com.keycut.batchservice.jop;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.keycut.batchservice.tasks.ExchangeTask;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class ExchangeJop {

	private final ExchangeTask exchangeTask;

	@Bean
	public Job exchangeJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new JobBuilder("exchangeJob", jobRepository)
			.incrementer(new RunIdIncrementer())
			.start(exchangeStep(jobRepository, transactionManager))
			.build();
	}

	@Bean
	@JobScope
	public Step exchangeStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
		return new StepBuilder("exchangeStep", jobRepository).tasklet(exchangeTask, transactionManager)
			.build();
	}
}
