package com.mc.jdbc.rent.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.mc.jdbc.rent.dto.Rent;
import com.mc.jdbc.rent.service.RentService;

public class RentController {
	
	private RentService rentService  = new RentService();

	public String resistRent(String userId, List<String> bkIdxs) {
		
		List<Integer> parsedBkIdxs = bkIdxs.stream()
										.map(e -> Integer.parseInt(e))
										.collect(Collectors.toList());
		
		Rent rent = rentService.insertRent(userId, parsedBkIdxs);
		return rent.getTitle() + "대출이 완료 되었습니다.";
	}

	
	
	
}
