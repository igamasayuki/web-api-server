package com.example.credit.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.credit.client.form.CreditCardCancelForm;
import com.example.credit.domain.ResponseCreditCardCancelApiDomain;

/**
 * カード決済キャンセルAPIを呼び出すサービスクラス.
 *
 * 呼び出し先: {credit.api.base-url}/api/credit/cancel
 */
@Service
public class CreditCardCancelApiCallService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${credit.api.base-url}")
    private String baseUrl;

    private static final String CANCEL_PATH = "/api/credit/cancel";

    /**
     * カード決済キャンセルWebAPIを呼び出し、レスポンスを返す.
     *
     * @param form キャンセル情報
     * @return WebAPIのレスポンス
     */
    public ResponseCreditCardCancelApiDomain cancelApiService(CreditCardCancelForm form) {
        String url = baseUrl + CANCEL_PATH;
        System.out.println("キャンセルAPI呼び出し先: " + url);
        return restTemplate.postForObject(url, form, ResponseCreditCardCancelApiDomain.class);
    }
}