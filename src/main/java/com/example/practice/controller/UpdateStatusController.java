package com.example.practice.controller;

import com.example.practice.form.UpdateStatusForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/updatestatus")
//CrossOrigin対応(異なるサーバーからの呼び出しを許可)
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class UpdateStatusController {

	private static final Integer NOT_PAYMENT_VALUE = 0;
	private static final String NOT_PAYMENT_NAME = "入金前";
	private static final Integer DEPOSITED_NAME_VALUE = 1;
	private static final String DEPOSITED_NAME = "入金済";
	private static final Integer DELIVERED_VALUE = 2;
	private static final String DELIVERED_NAME = "配送済";
	private static final Integer COMPLETE_VALUE = 3;
	private static final String COMPLETE_Name = "完了";

	@GetMapping("")
	public String index() {
		return "practise/update_status";
	}

	@ResponseBody
	@PostMapping("/update")
	public Map<String, String> update(UpdateStatusForm updateStatusForm) {
		Integer previousStatusValue = updateStatusForm.getPreviousStatusValue();

		Integer nowStatusValue = null;
		String nowStatusName = null;
		String nextStatusName = null;

		if (previousStatusValue == COMPLETE_VALUE) {
			nowStatusValue = NOT_PAYMENT_VALUE;
			nowStatusName = NOT_PAYMENT_NAME;
			nextStatusName = DEPOSITED_NAME;
		} else if (previousStatusValue == NOT_PAYMENT_VALUE) {
			nowStatusValue = DEPOSITED_NAME_VALUE;
			nowStatusName = DEPOSITED_NAME;
			nextStatusName = DELIVERED_NAME;
		} else if (previousStatusValue == DEPOSITED_NAME_VALUE) {
			nowStatusValue = DELIVERED_VALUE;
			nowStatusName = DELIVERED_NAME;
			nextStatusName = COMPLETE_Name;
		} else if (previousStatusValue == DELIVERED_VALUE) {
			nowStatusValue = COMPLETE_VALUE;
			nowStatusName = COMPLETE_Name;
			nextStatusName = NOT_PAYMENT_NAME;
		} else {
			throw new RuntimeException("存在しないステータス番号です");
		}

		Map<String, String> map = new HashMap<>();
		map.put("nowStatusValue", nowStatusValue.toString());
		map.put("nowStatusName", nowStatusName);
		map.put("nextStatusName", nextStatusName);

		return map;
	}

//	// Enumを使用した解答例
//	@ResponseBody
//	@PostMapping("/update")
//	public Map<String, String> updateUseEnum(UpdateStatusForm updateStatusForm) {
//		Integer previousStatusValue = updateStatusForm.getPreviousStatusValue();
//		Map<String, String> map = new HashMap<>();
//		// 前のステータス
//		StatusEnum previousStatusEnum = StatusEnum.of(previousStatusValue);
//		// 現在の変更したステータス
//		StatusEnum nowStatusEnum = StatusEnum.of(previousStatusEnum.getNextStatusValue());
//		// 次のステータス
//		StatusEnum nextStatusEnum = StatusEnum.of(nowStatusEnum.getNextStatusValue());
//
//		map.put("nowStatusName", nowStatusEnum.getStatusName());
//		map.put("nowStatusValue", String.valueOf(nowStatusEnum.getStatusValue()));
//		map.put("nextStatusName", nextStatusEnum.getStatusName());
//
//		return map;
//	}

}
