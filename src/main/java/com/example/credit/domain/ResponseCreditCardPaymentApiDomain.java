package com.example.credit.domain;

import lombok.Data;

/**
 * クレジットカード決済APIのレスポンス情報を扱うドメインクラス.
 */
@Data
public class ResponseCreditCardPaymentApiDomain {

    /** ステータス情報 */
    private String status;
    /** レスポンスメッセージ */
    private String message;
    /** エラーコード */
    private String error_code;
}