package com.mc.jdbc.rent.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.mc.jdbc.common.code.RentState;
import com.mc.jdbc.common.validator.Errors;
import com.mc.jdbc.rent.dto.Rent;
import com.mc.jdbc.rent.dto.RentBook;
import com.mc.jdbc.rent.service.RentService;
import com.mc.jdbc.rent.validator.RentFormValidator;

public class RentController {
	
	private RentService rentService  = new RentService();

	public String resistRent(String userId, List<String> bkIdxs) {
		
		// 데이터 파싱
		List<Integer> parsedBkIdxs = bkIdxs.stream()
										.map(e -> Integer.parseInt(e))
										.collect(Collectors.toList());
		
		// 데이터 검증
		Errors errors = RentFormValidator.getInstance().validateResistRent(userId, parsedBkIdxs);
		
		if(errors.hasError()) return errors.toString();
		
		Rent rent = rentService.insertRent(userId, parsedBkIdxs);
		return rent.getTitle() + " 대출이 완료 되었습니다.";
	}

	public String returnRentBook(String rbIdx) {
		
		int parsedRbIdx = Integer.parseInt(rbIdx);
		Errors errors = RentFormValidator.getInstance().validateReturnRentBook(parsedRbIdx);
		
		if(errors.hasError()) return errors.toString();
		
		rentService.updateRentBookReturn(parsedRbIdx, RentState.RE03);
		
		return rbIdx + "가 반납처리 되었습니다.";
	}

	public List<RentBook> searchRentBookWithRmIdx(String rmIdx) {

		int parsedRmIdx = Integer.parseInt(rmIdx);
		List<RentBook> rentBooks = rentService.selectRentBookWithRmIdx(parsedRmIdx);
		
		return rentBooks;
	}

	
	
	
}
