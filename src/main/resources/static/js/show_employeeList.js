"use strict";
$(() => {
  $("#button").on("click", () => {
    const hostUrl = "/employee/employees";
    $.ajax({
      url: hostUrl,
      type: "GET",
      dataType: "json",
      async: true, // 非同期で処理を行う
    })
      .done(function (data) {
        // コンソールに取得データを表示
        console.log(data);
        console.dir(JSON.stringify(data));

        for (const employee of data.employees) {
          // 検索された従業員の数だけテーブルの行を追加する
          $("#employeeList").append(
            `<tr><td>${employee.id}</td>` +
              `<td><img width="30px" height="50px" src="/img/${employee.image}"/></td>` +
              `<td>${employee.name}</td>` +
              `<td>${employee.hireDate}</td>` +
              `</tr>`
          );
        }
      })
      .fail(function (XMLHttpRequest, textStatus, errorThrown) {
        alert("エラーが発生しました！");
        console.log("XMLHttpRequest : " + XMLHttpRequest.status);
        console.log("textStatus     : " + textStatus);
        console.log("errorThrown    : " + errorThrown.message);
      });
  });
});
