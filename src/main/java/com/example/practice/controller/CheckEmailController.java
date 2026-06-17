package com.example.practice.controller;

import com.example.practice.form.CheckEmailForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/checkemail")
//CrossOrigin対応(異なるサーバーからの呼び出しを許可)
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class CheckEmailController {

	@GetMapping("")
	public String index() {
		return "practise/input_email";
	}

	@ResponseBody
	@PostMapping("/check")
	public Map<String, String> check(CheckEmailForm checkEmailForm) {
		String email = checkEmailForm.getEmail();
		System.out.println("サーバー側「入力されたemail」：" + email);
		Map<String, String> map = new HashMap<>();
		String duplicateMessage = null;
		if ("iga@sample.com".equals(email)) {
			duplicateMessage = "「" + email + "」は既に登録されているメールアドレスです";
		} else {
			duplicateMessage = "「" + email + "」は登録されていません";
		}
		map.put("duplicateMessage", duplicateMessage);
		return map;
	}
	
}
