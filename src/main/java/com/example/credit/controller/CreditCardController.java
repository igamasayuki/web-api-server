package com.example.credit.controller;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.credit.domain.RequestCreditCardPaymentApiDomain;
import com.example.credit.domain.ResponseCreditCardPaymentApiDomain;

import jakarta.validation.Valid;

/**
 * クレジットカード決済を処理するWebAPIコントローラー.
 * POST /api/credit/payment
 */
@RestController
@RequestMapping("/api/credit")
public class CreditCardController {

    /**
     * クレジット決済処理（疑似ロジック）.
     *
     * @param request クレジットカード決済リクエスト情報
     * @return 決済結果レスポンス
     */
    @PostMapping("/payment")
    public ResponseCreditCardPaymentApiDomain payment(
            @Valid @RequestBody RequestCreditCardPaymentApiDomain request) {

        System.out.println("リクエスト情報: " + request);

        ResponseCreditCardPaymentApiDomain response = new ResponseCreditCardPaymentApiDomain();

        int expYear;
        int expMonth;
        try {
            expYear = Integer.parseInt(request.getCard_exp_year());
            expMonth = Integer.parseInt(request.getCard_exp_month());
        } catch (NumberFormatException e) {
            // バリデーション通過後の万全策（通常はここに到達しない）
            return buildErrorResponse("E-03", "Error.");
        }

        // E-01: 有効期限切れチェック（月末日まで有効とみなす）
        LocalDate endOfMonth = LocalDate.of(expYear, expMonth, 1)
                .with(TemporalAdjusters.lastDayOfMonth());
        if (endOfMonth.isBefore(LocalDate.now())) {
            return buildErrorResponse("E-01", "The card is expired.");
        }

        // E-02: セキュリティコードチェック
        if (!"123".equals(request.getCard_cvv())) {
            return buildErrorResponse("E-02", "The card information is incorrect.");
        }

        // E-00: 正常
        response.setStatus("success");
        response.setMessage("OK.");
        response.setError_code("E-00");
        System.out.println("レスポンス情報: " + response);
        return response;
    }

    /**
     * バリデーションエラー・型不正エラー → E-03 を返す.
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
    public ResponseCreditCardPaymentApiDomain handleValidationError(Exception e) {
        System.out.println("バリデーションエラー: " + e.getMessage());
        return buildErrorResponse("E-03", "Error.");
    }

    private ResponseCreditCardPaymentApiDomain buildErrorResponse(String errorCode, String message) {
        ResponseCreditCardPaymentApiDomain response = new ResponseCreditCardPaymentApiDomain();
        response.setStatus("error");
        response.setMessage(message);
        response.setError_code(errorCode);
        System.out.println("レスポンス情報: " + response);
        return response;
    }
}