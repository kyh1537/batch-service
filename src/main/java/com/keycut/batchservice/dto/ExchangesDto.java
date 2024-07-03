package com.keycut.batchservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ExchangesDto {

	@JsonIgnoreProperties(ignoreUnknown = true)
	public record KoreaEximDto(
		Integer result,
		String ttb,
		String tts,
		String bkpr,

		@JsonProperty("cur_unit")
		String countryCode, // 통화 코드
		@JsonProperty("deal_bas_r")
		String dealBaseRate, // 매매 기준율
		@JsonProperty("yy_efee_r")
		String yyEfeeRate,
		@JsonProperty("ten_dd_efee_r")
		String tenDdEfeeRate,
		@JsonProperty("kftc_bkpr")
		String kftcBkpr,
		@JsonProperty("kftc_deal_bas_r")
		String kftcDealBaseRate,
		@JsonProperty("cur_nm")
		String countryName // 국가명
	) {
	}
}
