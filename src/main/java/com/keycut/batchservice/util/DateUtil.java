package com.keycut.batchservice.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.util.ObjectUtils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class DateUtil {

	public static String getTodayLocalDateByPattern(String pattern) {
		if (ObjectUtils.isEmpty(pattern)) {
			pattern = "yyyy-MM-dd";
		}
		return LocalDate.now().format(DateTimeFormatter.ofPattern(pattern));
	}
}
