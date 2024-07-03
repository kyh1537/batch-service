package com.keycut.batchservice.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.keycut.batchservice.dto.ExchangesDto.KoreaEximDto;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "exchanges")
@EntityListeners(AuditingEntityListener.class)
public class ExchangesEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long uid;
	private String countryCode;
	private String countryName;
	private String basePrice;
	private String provider;

	@CreatedDate
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime regDate;

	@LastModifiedDate
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updateDate;

	public static ExchangesEntity from(KoreaEximDto koreaEximDto) {
		return ExchangesEntity.builder()
			.countryCode(koreaEximDto.countryCode())
			.countryName(koreaEximDto.countryName())
			.basePrice(koreaEximDto.dealBaseRate())
			.provider("한국수출입은행")
			.build();
	}
}


