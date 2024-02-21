let api = {
  search: function (url, data, success, fail) {
    $.ajax({
      type: "GET",
      url: url,
      data: data,
      success: function (res) {
        success(res);
      },
      error: function () {
        fail();
      },
    });
  },
};
