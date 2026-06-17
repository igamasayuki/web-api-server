package com.example.practice.controller;

import com.example.practice.form.CheckPasswordForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/checkpassword")
//CrossOrigin対応(異なるサーバーからの呼び出しを許可)
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.DELETE })
public class CheckPasswordController {

	@GetMapping("")
	public String index() {
		return "practise/input_password";
	}

	@ResponseBody
	@PostMapping("/check")
	public Map<String, String> check(CheckPasswordForm checkPasswordForm) {

		String password = checkPasswordForm.getPassword();
		String confirmationPassword = checkPasswordForm.getConfirmationPassword();

		Map<String, String> map = new HashMap<>();

		// 8文字以上チェック
		final int PASSWORD_MAX_LENGTH = 8;
		String robustnessMessage = null;
		if (password.length() >= PASSWORD_MAX_LENGTH) {
			robustnessMessage = "パスワード入力OK!";
		} else {
			robustnessMessage = "パスワードは" + PASSWORD_MAX_LENGTH + "文字以上で入力してください";
		}
		map.put("robustnessMessage", robustnessMessage);

		// パスワード一致チェック
		String disagreementMessage = null;
		if (password.equals(confirmationPassword)) {
			disagreementMessage = "確認用パスワード入力OK!";
		} else {
			disagreementMessage = "パスワードが一致していません";
		}
		map.put("disagreementMessage", disagreementMessage);

		System.out.println(password + ":" + confirmationPassword);

		return map;
	}

}
