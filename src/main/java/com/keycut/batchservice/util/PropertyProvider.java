package com.keycut.batchservice.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Component
public class PropertyProvider {

	@Value("${koreaexim.host}")
	private String exchangeHost;

	@Value("${koreaexim.token}")
	private String exchangeToken;
}
