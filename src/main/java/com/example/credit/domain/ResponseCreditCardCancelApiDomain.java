package com.example.credit.domain;

import lombok.Data;

/**
 * クレジットカードキャンセルAPIのレスポンス情報を扱うドメインクラス.
 */
@Data
public class ResponseCreditCardCancelApiDomain {

    /** ステータス情報 */
    private String status;
    /** レスポンスメッセージ */
    private String message;
    /** エラーコード */
    private String error_code;
}