# web-api-server

Spring Boot で構築した Web API サーバー。Render（無料枠）へのデプロイを前提に設計。

---

## 技術スタック

| 項目 | 内容 |
|---|---|
| 言語 | Java 21 |
| フレームワーク | Spring Boot 3.x |
| ビルドツール | Gradle |
| テンプレートエンジン | Thymeleaf（クレカクライアント画面用） |
| バリデーション | Spring Validation（Bean Validation） |
| デプロイ先 | Render（無料枠 512MB） |

---

## パッケージ構成

```
com.example
├── WebApiServerApplication.java        # 起動クラス
├── config/
│   └── WebConfig.java                  # CORS設定 / RestTemplate Bean定義
├── health/
│   └── HealthCheckController.java      # ヘルスチェック API
├── credit/
│   ├── controller/
│   │   └── CreditCardController.java   # クレカ決済 API（サーバー側）
│   ├── domain/
│   │   ├── RequestCreditCardPaymentApiDomain.java
│   │   ├── ResponseCreditCardPaymentApiDomain.java
│   │   └── ResponseCreditCardCancelApiDomain.java
│   └── client/                         # クレカ画面（クライアント側）
│       ├── CreditCardClientController.java
│       ├── form/
│       │   ├── CreditCardPaymentForm.java
│       │   └── CreditCardCancelForm.java
│       └── service/
│           ├── CreditCardPaymentApiCallService.java
│           └── CreditCardCancelApiCallService.java
└── practice/
    └── PracticeController.java         # JS/Ajax 研修用 API
```

---

## エンドポイント一覧

### ヘルスチェック

| メソッド | パス | 説明 |
|---|---|---|
| GET | `/api/health` | `"OK"` を返す。Render のスリープ回避用。 |

---

### クレジットカード決済 API

**POST `/api/credit/payment`**

フロントエンドからJSON形式でリクエストを受け取り、疑似ロジックで決済結果を返す。

#### リクエスト

```json
{
  "user_id":       "9999",
  "order_number":  "11111111111111",
  "amount":        "1000",
  "card_number":   "1234567890123456",
  "card_exp_year": "2027",
  "card_exp_month":"12",
  "card_name":     "TARO YAMADA",
  "card_cvv":      "123"
}
```

| フィールド | 型 | バリデーション |
|---|---|---|
| user_id | 文字列 | 必須 |
| order_number | 文字列 | 必須・数字14桁 |
| amount | 文字列 | 必須・数字1〜10桁 |
| card_number | 文字列 | 必須・数字14〜16桁 |
| card_exp_year | 文字列 | 必須・数字4桁 |
| card_exp_month | 文字列 | 必須・数字2桁 |
| card_name | 文字列 | 必須・最大50文字 |
| card_cvv | 文字列 | 必須・数字3〜4桁 |

#### レスポンスパターン

| コード | 条件 | レスポンス例 |
|---|---|---|
| E-00（成功） | card_cvv が `"123"` かつ有効期限が現在以降 | `{"status":"success","message":"OK.","error_code":"E-00"}` |
| E-01（有効期限切れ） | card_exp_year / card_exp_month が現在年月より前 | `{"status":"error","message":"The card is expired.","error_code":"E-01"}` |
| E-02（CVV不一致） | card_cvv が `"123"` 以外 | `{"status":"error","message":"The card information is incorrect.","error_code":"E-02"}` |
| E-03（入力不正） | 必須項目不足・型不正など | `{"status":"error","message":"Error.","error_code":"E-03"}` |

---

**POST `/api/credit/cancel`**

キャンセル処理（疑似）。常に成功レスポンスを返す。

```json
{ "status": "success", "message": "Cancelled.", "error_code": "E-00" }
```

---

### クレジットカード決済クライアント（画面あり）

Thymeleaf による HTML 画面。同プロジェクトの `/api/credit/**` を RestTemplate で呼び出す。

| メソッド | パス | 説明 |
|---|---|---|
| GET | `/credit` | 決済入力フォーム画面 |
| POST | `/credit/payment` | フォーム送信 → API呼び出し → 完了画面へリダイレクト |
| GET | `/credit/finished` | 決済完了画面（APIレスポンス表示） |
| GET | `/credit/toCancel` | キャンセル確認画面 |
| POST | `/credit/cancel` | キャンセル実行 → API呼び出し → キャンセル完了画面へリダイレクト |
| GET | `/credit/cancelled` | キャンセル完了画面（APIレスポンス表示） |

---

### JS/Ajax 研修用 API

| メソッド | パス | レスポンス |
|---|---|---|
| GET | `/api/practice/employees` | 従業員ダミーリスト（10名） |
| POST | `/api/practice/checkemail` | `{"isUnique": true}` |
| POST | `/api/practice/checkpassword` | `{"isValid": true}` |
| POST | `/api/practice/updatestatus` | `{"status": "updated"}` |

---

## ローカル起動

```bash
./gradlew bootRun
```

起動後、`http://localhost:8080` でアクセス可能。

---

## Render へのデプロイ

### 1. Render の設定

Render では **Docker** または **Native Runtime** の2通りでデプロイできる。  
本プロジェクトには `Dockerfile` を同梱しているため、**Docker でのデプロイを推奨**。

#### Docker を使う場合（推奨）

Render のサービス作成時に **「Docker」** を選択するだけで自動的に `Dockerfile` が使われる。  
Build Command / Start Command の手動設定は不要。

```dockerfile
# マルチステージビルド構成
# Stage 1: Gradle でビルド（eclipse-temurin:21-jdk-alpine）
# Stage 2: 軽量JREで実行（eclipse-temurin:21-jre-alpine）
```

> Windows 環境で開発している場合でも、`Dockerfile` 内で `gradlew` の改行コード（CRLF）を自動修正しているため問題なく動作する。

#### Native Runtime を使う場合

| 項目 | 値 |
|---|---|
| Build Command | `./gradlew build -x test` |
| Start Command | `java -jar build/libs/web-api-server-0.0.1-SNAPSHOT.jar` |

### 2. 環境変数の設定

Render のダッシュボードで以下の環境変数を設定する。

| 変数名 | 値 | 説明 |
|---|---|---|
| `APP_BASE_URL` | `https://your-app-name.onrender.com` | クレカクライアント画面から API を呼び出す際のベースURL。**自サービスの URL を設定すること。** |

> **注意:** `APP_BASE_URL` を設定しない場合は `http://localhost:8080` がデフォルトになり、Render 上ではクレカ画面からの決済処理が動作しない。

### 3. Render スリープ対策

Render 無料枠はアクセスがないと約15分でスリープする。  
外部サービス（UptimeRobot 等）から定期的に `GET /api/health` を叩くことでスリープを防止できる。

---

## CORS

`/api/**` に対して全 Origin・全メソッド・全ヘッダーを許可している（`WebConfig.java`）。  
フロントエンドのホスティング先に関わらずリクエスト可能。
