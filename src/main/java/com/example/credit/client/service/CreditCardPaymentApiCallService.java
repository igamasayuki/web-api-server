package com.example.credit.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.credit.client.form.CreditCardPaymentForm;
import com.example.credit.domain.ResponseCreditCardPaymentApiDomain;

/**
 * カード決済APIを呼び出すサービスクラス.
 *
 * 呼び出し先: {credit.api.base-url}/api/credit/payment
 * ローカル実行時は http://localhost:8080 (デフォルト)
 * Renderデプロイ時は APP_BASE_URL 環境変数に自サービスURLを設定すること
 */
@Service
public class CreditCardPaymentApiCallService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${credit.api.base-url}")
    private String baseUrl;

    private static final String PAYMENT_PATH = "/api/credit/payment";

    /**
     * カード決済WebAPIを呼び出し、レスポンスを返す.
     *
     * @param form クレジットカード情報
     * @return WebAPIのレスポンス
     */
    public ResponseCreditCardPaymentApiDomain paymentApiService(CreditCardPaymentForm form) {
        String url = baseUrl + PAYMENT_PATH;
        System.out.println("決済API呼び出し先: " + url);
        return restTemplate.postForObject(url, form, ResponseCreditCardPaymentApiDomain.class);
    }
}