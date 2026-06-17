package com.example.practice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * HTML画面を返すコントローラー.
 *
 * GET /checkemail    - メール入力画面
 * GET /checkpassword - パスワード入力画面
 * GET /updatestatus  - ステータス更新画面
 * GET /employees     - 従業員一覧画面
 */
@Controller
public class ScreenController {

    @GetMapping("/checkemail")
    public String inputEmail() {
        return "input_email";
    }

    @GetMapping("/checkpassword")
    public String inputPassword() {
        return "input_password";
    }

    @GetMapping("/updatestatus")
    public String updateStatus() {
        return "update_status";
    }

    @GetMapping("/employees")
    public String showEmployeeList() {
        return "show_employeeList";
    }
}