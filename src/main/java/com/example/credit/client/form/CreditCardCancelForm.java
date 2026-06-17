package com.example.credit.client.form;

import lombok.Data;

/**
 * キャンセルする注文番号情報が入るフォーム.
 */
@Data
public class CreditCardCancelForm {

    /** 注文番号 */
    private String order_number;
}