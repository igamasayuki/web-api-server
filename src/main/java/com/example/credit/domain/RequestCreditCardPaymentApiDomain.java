package com.example.credit.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * クレジットカード決済APIのリクエスト情報を扱うドメインクラス.
 */
@Data
public class RequestCreditCardPaymentApiDomain {

    /** ユーザID */
    @NotBlank
    private String user_id;

    /** 注文番号(数字14桁) */
    @NotBlank
    @Pattern(regexp = "\\d{14}", message = "order_number must be 14 digits")
    private String order_number;

    /** 決済金額(数字・10桁まで) */
    @NotBlank
    @Pattern(regexp = "\\d{1,10}", message = "amount must be numeric and up to 10 digits")
    private String amount;

    /** カード番号(数字14〜16桁) */
    @NotBlank
    @Pattern(regexp = "\\d{14,16}", message = "card_number must be 14-16 digits")
    private String card_number;

    /** カード有効期限(年)(数字4桁) */
    @NotBlank
    @Pattern(regexp = "\\d{4}", message = "card_exp_year must be 4 digits")
    private String card_exp_year;

    /** カード有効期限(月)(数字2桁) */
    @NotBlank
    @Pattern(regexp = "\\d{2}", message = "card_exp_month must be 2 digits")
    private String card_exp_month;

    /** カード名義人名(最大50文字) */
    @NotBlank
    @Size(max = 50)
    private String card_name;

    /** セキュリティコード(数字3〜4桁) */
    @NotBlank
    @Pattern(regexp = "\\d{3,4}", message = "card_cvv must be 3 or 4 digits")
    private String card_cvv;
}