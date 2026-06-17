package com.example.credit.client;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.credit.client.form.CreditCardCancelForm;
import com.example.credit.client.form.CreditCardPaymentForm;
import com.example.credit.client.service.CreditCardCancelApiCallService;
import com.example.credit.client.service.CreditCardPaymentApiCallService;
import com.example.credit.domain.ResponseCreditCardCancelApiDomain;
import com.example.credit.domain.ResponseCreditCardPaymentApiDomain;

/**
 * クレジットカード決済クライアントのコントローラー.
 *
 * 画面遷移:
 *   GET  /credit          → 入力画面 (insert)
 *   POST /credit/payment  → 決済処理 → 完了画面へリダイレクト
 *   GET  /credit/finished → 決済完了画面
 *   GET  /credit/toCancel → キャンセル入力画面
 *   POST /credit/cancel   → キャンセル処理 → キャンセル完了画面へリダイレクト
 *   GET  /credit/cancelled → キャンセル完了画面
 */
@Controller
@RequestMapping("/credit")
public class CreditCardClientController {

    @Autowired
    private CreditCardPaymentApiCallService creditCardPaymentApiCallService;
    @Autowired
    private CreditCardCancelApiCallService creditCardCancelApiCallService;

    @ModelAttribute
    public CreditCardPaymentForm setUpForm() {
        return new CreditCardPaymentForm();
    }

    /** 支払い方法入力画面を表示 */
    @RequestMapping("")
    public String index(Model model) {
        Map<Integer, String> paymentMethodMap = new LinkedHashMap<>();
        paymentMethodMap.put(1, "銀行振込");
        paymentMethodMap.put(2, "クレジットカード");
        model.addAttribute("paymentMethodMap", paymentMethodMap);
        return "credit/insert";
    }

    /** 支払い処理を実施 */
    @RequestMapping("/payment")
    public String payment(CreditCardPaymentForm form, RedirectAttributes redirectAttributes) {
        System.out.println("決済フォーム: " + form);
        if ("1".equals(form.getPaymentMethod())) {
            // 銀行振込の場合はそのまま完了画面へ
            return "redirect:/credit/finished";
        }
        ResponseCreditCardPaymentApiDomain response = creditCardPaymentApiCallService.paymentApiService(form);
        redirectAttributes.addFlashAttribute("responseCreditCardPaymentApiDomain", response);
        return "redirect:/credit/finished";
    }

    /** 決済完了画面を表示 */
    @RequestMapping("/finished")
    public String finished() {
        return "credit/finished";
    }

    /** キャンセル実施画面を表示 */
    @RequestMapping("/toCancel")
    public String toCancel() {
        return "credit/cancel";
    }

    /** キャンセル処理を実施 */
    @RequestMapping("/cancel")
    public String cancel(CreditCardCancelForm form, RedirectAttributes redirectAttributes) {
        System.out.println("キャンセルフォーム: " + form);
        ResponseCreditCardCancelApiDomain response = creditCardCancelApiCallService.cancelApiService(form);
        redirectAttributes.addFlashAttribute("responseCreditCardCancelApiDomain", response);
        return "redirect:/credit/cancelled";
    }

    /** キャンセル完了画面を表示 */
    @RequestMapping("/cancelled")
    public String cancelled() {
        return "credit/cancelled";
    }
}