let api = {
  search: function (url, data, successFunc = success2, fail = fail2) {
    $.ajax({
      type: "GET",
      url: url,
      data: data,
      success: function (res) {
        successFunc(res);
      },
      error: function () {
        fail();
      },
    });
  },
  check: function (url, data, successFunc = success2, fail = fail2) {
    console.log(data);
    $.ajax({
      type: "POST",
      url: url,
      data: data,
      success: function (res) {
        successFunc(res);
      },
      error: function () {
        fail();
      },
    });
  },
};

function success2(res = "") {
  console.log(res);
}

function fail2() {
  console.log("실패");
}
