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

#### リクエスト仕様

| 項目名 | パラメーター名 | 文字種・制約 |
|---|---|---|
| ユーザーID | `user_id` | 必須 |
| 注文NO | `order_number` | 数字14桁 |
| 決済金額 | `amount` | 数字1〜10桁 |
| クレジットカード番号 | `card_number` | 数字14〜16桁 |
| カード有効期限（年） | `card_exp_year` | 数字4桁 |
| カード有効期限（月） | `card_exp_month` | 数字2桁 |
| カード名義人 | `card_name` | 最大50文字 |
| セキュリティコード | `card_cvv` | 数字3〜4桁 |

#### リクエスト例（JSON）

```json
{
  "user_id":        "9999",
  "order_number":   "11111111111111",
  "amount":         "1000",
  "card_number":    "1234567890123456",
  "card_exp_year":  "2027",
  "card_exp_month": "12",
  "card_name":      "TARO YAMADA",
  "card_cvv":       "123"
}
```

#### エラーコード定義

| エラーコード | 意味 | メッセージ | 発生条件 |
|---|---|---|---|
| E-00 | 成功 | `OK.` | card_cvv が `"123"` かつ有効期限が現在以降 |
| E-01 | 有効期限切れ | `The card is expired.` | card_exp_year / card_exp_month が現在年月より前 |
| E-02 | セキュリティコード不一致 | `The card information is incorrect.` | card_cvv が `"123"` 以外 |
| E-03 | 入力値不正・必須不足 | `Error.` | 必須項目不足・型不正など |

#### レスポンス形式

```json
{ "status": "success", "message": "OK.", "error_code": "E-00" }
```

---

**POST `/api/credit/cancel`**

キャンセル処理（疑似）。常に成功レスポンスを返す。

```json
{ "status": "success", "message": "Cancelled.", "error_code": "E-00" }
```

---

### クレジットカード決済クライアント（画面あり）

Thymeleaf による HTML 画面。同プロジェクトの `/api/credit/**` を RestTemplate で呼び出す。

**動作イメージを見たい場合はここにアクセス:**

| 環境 | URL |
|---|---|
| 本番（Render） | https://web-api-server.onrender.com/credit |
| ローカル | http://localhost:8080/credit |

画面遷移:

| メソッド | パス | 説明 |
|---|---|---|
| GET | `/credit` | 決済入力フォーム画面 ← **ここから始める** |
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

## APIの動作確認（curl）

> **研修生向け:** 以下のコマンドをそのままターミナルに貼り付けて実行できます。
> ローカル確認の場合は URL を `http://localhost:8080/api/credit/payment` に変えてください。

### E-00（成功）

card_cvv を `"123"`・有効期限を未来日付にする。

```bash
curl -s -X POST https://web-api-server.onrender.com/api/credit/payment \
  -H "Content-Type: application/json" \
  -d '{
    "user_id":        "9999",
    "order_number":   "11111111111111",
    "amount":         "1000",
    "card_number":    "1234567890123456",
    "card_exp_year":  "2027",
    "card_exp_month": "12",
    "card_name":      "TARO YAMADA",
    "card_cvv":       "123"
  }' | jq .
```

期待レスポンス:

```json
{ "status": "success", "message": "OK.", "error_code": "E-00" }
```

---

### E-01（有効期限切れ）

card_exp_year / card_exp_month を過去日付にする。

```bash
curl -s -X POST https://web-api-server.onrender.com/api/credit/payment \
  -H "Content-Type: application/json" \
  -d '{
    "user_id":        "9999",
    "order_number":   "11111111111111",
    "amount":         "1000",
    "card_number":    "1234567890123456",
    "card_exp_year":  "2020",
    "card_exp_month": "01",
    "card_name":      "TARO YAMADA",
    "card_cvv":       "123"
  }' | jq .
```

期待レスポンス:

```json
{ "status": "error", "message": "The card is expired.", "error_code": "E-01" }
```

---

### E-02（セキュリティコード不一致）

card_cvv を `"123"` 以外にする。

```bash
curl -s -X POST https://web-api-server.onrender.com/api/credit/payment \
  -H "Content-Type: application/json" \
  -d '{
    "user_id":        "9999",
    "order_number":   "11111111111111",
    "amount":         "1000",
    "card_number":    "1234567890123456",
    "card_exp_year":  "2027",
    "card_exp_month": "12",
    "card_name":      "TARO YAMADA",
    "card_cvv":       "999"
  }' | jq .
```

期待レスポンス:

```json
{ "status": "error", "message": "The card information is incorrect.", "error_code": "E-02" }
```

---

### E-03（入力値不正・必須不足）

必須項目を省略する（ここでは user_id を削除）。

```bash
curl -s -X POST https://web-api-server.onrender.com/api/credit/payment \
  -H "Content-Type: application/json" \
  -d '{
    "order_number":   "11111111111111",
    "amount":         "1000",
    "card_number":    "1234567890123456",
    "card_exp_year":  "2027",
    "card_exp_month": "12",
    "card_name":      "TARO YAMADA",
    "card_cvv":       "123"
  }' | jq .
```

期待レスポンス:

```json
{ "status": "error", "message": "Error.", "error_code": "E-03" }
```

> **補足:** `jq` コマンドがない場合は末尾の `| jq .` を削除しても動作します。

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
