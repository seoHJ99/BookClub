let api = {
  search: function (url, data, successFunc, fail) {
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
  check: function (url, data, successFunc = success) {
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

function success(res = "") {
  console.log(res);
}

function fail() {
  console.log("실패");
}
