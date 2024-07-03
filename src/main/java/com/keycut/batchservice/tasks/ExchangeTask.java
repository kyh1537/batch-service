package com.keycut.batchservice.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.keycut.batchservice.dto.ExchangesDto.KoreaEximDto;
import com.keycut.batchservice.entity.ExchangesEntity;
import com.keycut.batchservice.repository.ExchangesRepository;
import com.keycut.batchservice.util.DateUtil;
import com.keycut.batchservice.util.PropertyProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
@StepScope
public class ExchangeTask implements Tasklet {

	private final RestTemplate restTemplate = new RestTemplate();
	private final ExchangesRepository exchangesRepository;
	private final PropertyProvider propertyProvider;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
		List<KoreaEximDto> exchangeRateList = this.getExchangeRateList();

		Optional<ExchangesEntity> usdEntity = exchangeRateList.stream()
			.filter(s -> s.countryCode().equalsIgnoreCase("USD"))
			.map(ExchangesEntity::from).findFirst();
		if (usdEntity.isEmpty()) {
			log.warn("not found USD Exchanges Rate Info.");
			return null;
		}

		this.exchangesRepository.save(usdEntity.get());
		return RepeatStatus.FINISHED;
	}

	public List<KoreaEximDto> getExchangeRateList() {
		String url = UriComponentsBuilder.fromHttpUrl(this.propertyProvider.getExchangeHost())
			.queryParam("authkey", this.propertyProvider.getExchangeToken())
			.queryParam("searchdate", DateUtil.getTodayLocalDateByPattern("yyyyMMdd"))
			.queryParam("data", "AP01")
			.encode()
			.toUriString();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		ResponseEntity<List<KoreaEximDto>> resApi =
			this.restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers),
				new ParameterizedTypeReference<>() {});

		if (resApi.getStatusCode() != HttpStatus.OK || ObjectUtils.isEmpty(resApi.getBody())) {
			log.warn("KoreaExim Exchange Rate API ERROR. Status[{}][URL : {}]", resApi.getStatusCode(), url);
			return new ArrayList<>();
		}

		return resApi.getBody();
	}
}
