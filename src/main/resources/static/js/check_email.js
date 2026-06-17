"use strict";
$(() => {
//  $("#email").on("blur", () => {
  $("#email").on("keyup", () => {
    const hostUrl = "/checkemail/check";
    const inputEmail = $("#email").val();
    console.log("入力された値:" + inputEmail);
    $.ajax({
      url: hostUrl,
      type: "POST",
      dataType: "json",
      data: {
        email: inputEmail,
      },
      async: true,
      // 非同期で処理を行う
    })
      .done(function (data) {
        // コンソールに取得データを表示
        console.log(data);
        console.dir(JSON.stringify(data));
        $("#duplicateMessage").text(data.duplicateMessage);
      })
      .fail(function (XMLHttpRequest, textStatus, errorThrown) {
        alert("エラーが発生しました！");
        console.log("XMLHttpRequest : " + XMLHttpRequest.status);
        console.log("textStatus     : " + textStatus);
        console.log("errorThrown    : " + errorThrown.message);
      });
  });
});
