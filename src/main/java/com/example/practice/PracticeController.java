package com.example.practice;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * JS/Ajax研修用のAPIコントローラー.
 *
 * GET  /api/practice/employees    - 従業員一覧（ダミー）
 * POST /api/practice/checkemail   - メール重複チェック（ダミー）
 * POST /api/practice/checkpassword - パスワードチェック（ダミー）
 * POST /api/practice/updatestatus - ステータス更新（ダミー）
 */
@RestController
@RequestMapping("/api/practice")
public class PracticeController {

    // -----------------------------------------------------------------------
    // GET /api/practice/employees
    // -----------------------------------------------------------------------

    @GetMapping("/employees")
    public Map<String, Object> getEmployees() {
        List<Map<String, Object>> employees = new ArrayList<>();

        employees.add(createEmployee(1, "山田太郎", "e1.png", "男性", "2018-10-29",
                "taro.yamada@sample.com", "111-1111", "東京都新宿区1-1-1", "090-1111-1111", 300000,
                "山田太郎さんは真面目で責任感が強く、チームのリーダーとして活躍しています。", 2));

        employees.add(createEmployee(2, "鈴木花子", "e2.png", "女性", "2019-04-01",
                "hanako.suzuki@sample.com", "222-2222", "東京都渋谷区2-2-2", "090-2222-2222", 280000,
                "鈴木花子さんはコミュニケーション能力が高く、顧客対応に優れています。", 0));

        employees.add(createEmployee(3, "佐藤次郎", "e1.png", "男性", "2017-07-15",
                "jiro.sato@sample.com", "333-3333", "神奈川県横浜市3-3-3", "090-3333-3333", 320000,
                "佐藤次郎さんは技術力が高く、新しい技術の導入をリードしています。", 1));

        employees.add(createEmployee(4, "田中幸子", "e2.png", "女性", "2020-04-01",
                "sachiko.tanaka@sample.com", "444-4444", "埼玉県さいたま市4-4-4", "090-4444-4444", 260000,
                "田中幸子さんは細かい作業が得意で、品質管理に貢献しています。", 3));

        employees.add(createEmployee(5, "伊藤健太", "e1.png", "男性", "2016-10-01",
                "kenta.ito@sample.com", "555-5555", "千葉県千葉市5-5-5", "090-5555-5555", 350000,
                "伊藤健太さんはプロジェクトマネジメントのスキルが高く、複数のプロジェクトを管理しています。", 2));

        employees.add(createEmployee(6, "渡辺美咲", "e2.png", "女性", "2021-04-01",
                "misaki.watanabe@sample.com", "666-6666", "東京都品川区6-6-6", "090-6666-6666", 240000,
                "渡辺美咲さんはデザインセンスが優れており、UI/UX改善に取り組んでいます。", 0));

        employees.add(createEmployee(7, "中村大輔", "e1.png", "男性", "2015-04-01",
                "daisuke.nakamura@sample.com", "777-7777", "大阪府大阪市7-7-7", "090-7777-7777", 380000,
                "中村大輔さんは営業成績が優秀で、新規顧客の開拓に大きく貢献しています。", 4));

        employees.add(createEmployee(8, "小林恵子", "e2.png", "女性", "2018-01-10",
                "keiko.kobayashi@sample.com", "888-8888", "愛知県名古屋市8-8-8", "090-8888-8888", 290000,
                "小林恵子さんは経理・財務の専門家として、正確な業務処理で信頼されています。", 1));

        employees.add(createEmployee(9, "加藤雄一", "e1.png", "男性", "2019-10-01",
                "yuichi.kato@sample.com", "999-9999", "福岡県福岡市9-9-9", "090-9999-9999", 310000,
                "加藤雄一さんはデータ分析が得意で、経営判断に必要なレポートを作成しています。", 0));

        employees.add(createEmployee(10, "松本由美", "e2.png", "女性", "2022-04-01",
                "yumi.matsumoto@sample.com", "100-1001", "北海道札幌市10-10-10", "090-1000-1000", 230000,
                "松本由美さんは人事・採用業務を担当し、優秀な人材の確保に努めています。", 0));

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("totalEmployeeCount", employees.size());
        result.put("employees", employees);
        return result;
    }

    // -----------------------------------------------------------------------
    // POST /api/practice/checkemail  ※ダミー実装
    // -----------------------------------------------------------------------

    @PostMapping("/checkemail")
    public Map<String, Object> checkEmail() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("isUnique", true);
        return response;
    }

    // -----------------------------------------------------------------------
    // POST /api/practice/checkpassword  ※ダミー実装
    // -----------------------------------------------------------------------

    @PostMapping("/checkpassword")
    public Map<String, Object> checkPassword() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("isValid", true);
        return response;
    }

    // -----------------------------------------------------------------------
    // POST /api/practice/updatestatus  ※ダミー実装
    // -----------------------------------------------------------------------

    @PostMapping("/updatestatus")
    public Map<String, Object> updateStatus() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "updated");
        return response;
    }

    // -----------------------------------------------------------------------
    // 内部ユーティリティ
    // -----------------------------------------------------------------------

    private Map<String, Object> createEmployee(int id, String name, String image, String gender,
            String hireDate, String mailAddress, String zipCode, String address,
            String telephone, int salary, String characteristics, int dependentsCount) {
        Map<String, Object> employee = new LinkedHashMap<>();
        employee.put("id", id);
        employee.put("name", name);
        employee.put("image", image);
        employee.put("gender", gender);
        employee.put("hireDate", hireDate);
        employee.put("mailAddress", mailAddress);
        employee.put("zipCode", zipCode);
        employee.put("address", address);
        employee.put("telephone", telephone);
        employee.put("salary", salary);
        employee.put("characteristics", characteristics);
        employee.put("dependentsCount", dependentsCount);
        return employee;
    }
}