package com.example.practice;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * JS/Ajax研修用のAPIコントローラー（画面JSが呼び出すパス用）.
 *
 * POST /checkemail/check      - メール重複チェック（ダミー）
 * POST /checkpassword/check   - パスワードチェック（ダミー）
 * POST /updatestatus/update   - ステータス更新
 */
@RestController
public class PracticeAjaxController {

    // -----------------------------------------------------------------------
    // POST /checkemail/check
    // -----------------------------------------------------------------------

    @PostMapping("/checkemail/check")
    public Map<String, Object> checkEmail() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("duplicateMessage", "");
        return response;
    }

    // -----------------------------------------------------------------------
    // POST /checkpassword/check
    // -----------------------------------------------------------------------

    @PostMapping("/checkpassword/check")
    public Map<String, Object> checkPassword() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("robustnessMessage", "強度：普通");
        response.put("disagreementMessage", "");
        return response;
    }

    // -----------------------------------------------------------------------
    // POST /updatestatus/update
    // -----------------------------------------------------------------------

    private static final String[] STATUS_NAMES = {"入金前", "入金済", "完了"};

    @PostMapping("/updatestatus/update")
    public Map<String, Object> updateStatus(@RequestParam int previousStatusValue) {
        int nowStatusValue = (previousStatusValue + 1) % STATUS_NAMES.length;
        int nextStatusValue = (nowStatusValue + 1) % STATUS_NAMES.length;

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("nowStatusName", STATUS_NAMES[nowStatusValue]);
        response.put("nowStatusValue", nowStatusValue);
        response.put("nextStatusName", STATUS_NAMES[nextStatusValue]);
        return response;
    }
}