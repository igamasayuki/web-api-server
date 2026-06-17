package com.example.credit.client.form;

import lombok.Data;

/**
 * クレジットカード支払い情報が入るフォーム.
 */
@Data
public class CreditCardPaymentForm {

    /** 購入者ID */
    private String user_id;
    /** 注文番号 */
    private String order_number;
    /** 決済金額 */
    private String amount;
    /** 支払方法(1:銀行振込 / 2:クレジットカード) */
    private String paymentMethod;
    /** カード番号 */
    private String card_number;
    /** カード有効期限(月) */
    private String card_exp_month;
    /** カード有効期限(年) */
    private String card_exp_year;
    /** セキュリティコード */
    private String card_cvv;
    /** カード名義人名 */
    private String card_name;
}