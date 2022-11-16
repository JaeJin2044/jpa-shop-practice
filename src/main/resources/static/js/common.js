
//REST API Ajax 호출 하기
function ajaxRestCall(option) {
  //default option
  var defOption = {
    type: "post",
    dataType: "json"
  }

  //option merge
  var optObj = $.extend({}, defOption, option);
  var isComplete = false;
  var deferred = $.Deferred();

  $.ajax(
      optObj
  ).done(function (result, status, responseObj) {
    //$("#loading").hide();
    console.log('done...')
    console.log(result);
    deferred.resolve(result);
  }).fail(function (result, status, responseObj) {
    //$("#loading").hide();
    console.log('fail...');
    console.log(result);
    deferred.reject(result, status);
    // if (result.msg) {
    //   errorMsg = "code:" + result.code + "\n" + "\n" + "msg:" + result.msg;
    //   alert(result.msg);
    // } else {
    //   if (result.responseJSON) {
    //     errorMsg = "error:" + result.responseJSON.error + "\n" + "status:" + result.responseJSON.status + "\n" + result.responseJSON.message;
    //     alert(errorMsg);
    //   }
    // }
  }).always(function (result, status, responseObj) {
    //TODO
  });
  // setTimeout(function () {
  //   if (!isComplete) {
  //     $("#loading").show();
  //   }
  // }, 500);
  return deferred.promise();
}


jQuery.fn.serializeObject = function() {
  var obj = null;
  try {
    if (this[0].tagName && this[0].tagName.toUpperCase() == "FORM") {
      var arr = this.serializeArray();
      if (arr) {
        obj = {};
        jQuery.each(arr, function() {
          obj[this.name] = this.value;
        });
      }
    }
  } catch (e) {
    alert(e.message);
  } finally {
  }
  return obj;
}

//값이 비어있는지 체크하는 함수
function isEmpty(obj) {
  //null 체크
  if (obj === null) return true;
  //타입 체크
  if (obj === undefined) return true;
  //문자열 체크
  if (typeof obj === 'string' && $.trim(obj) === '') return true;
  //배열 체크
  if ($.isArray(obj) && obj.length < 1) return true;
  //객체 체크
  if ($.isEmptyObject(obj)) return true;
  return false;
}

//url 파라미터 추출
function getUrlParams() {
  var params = {};
  window.location.search.replace(/[?&]+([^=&]+)=([^&]*)/gi,
      function(str, key, value) {
        params[key] = decodeURIComponent(value);
      }
  );
  return params;
}

// 날짜 포맷 변경
function fnDateFormat(date, format) {
  var returnDate = "";
  var valid = Date.parse(date);
  // 날짜형식이 유효한지 체크 > 날짜로 변경
  if (!isNaN(valid)) {
    returnDate = new Date(date);
  } else {
    if (date.length == 8) {
      var year = date.substr(0, 4);
      var month = date.substr(4, 2);
      var day = date.substr(6, 4);
      returnDate = new Date(year, month - 1, day);
    } else {
      console.log("error : 날짜형식이 유효하지 않습니다.");
      return returnDate;
    }
  }

  switch (format) {
    case "YYYY-MM-DD":
      returnDate = returnDate.getFullYear() + "-" + pad(returnDate.getMonth() + 1) + "-" + pad(returnDate.getDate());
      break;
    case "YYYY.MM.DD":
      returnDate = returnDate.getFullYear() + "." + pad(returnDate.getMonth() + 1) + "." + pad(returnDate.getDate());
      break;
    case "YYYY-MM-DD HH:mm:ss":
      returnDate = returnDate.getFullYear() + "-" + pad(returnDate.getMonth() + 1) + "-" + pad(returnDate.getDate()) + " " + pad(returnDate.getHours()) + ":" + pad(returnDate.getMinutes());
      break;
    case "YYYY.MM.DD HH:mm:ss":
      returnDate = returnDate.getFullYear() + "." + pad(returnDate.getMonth() + 1) + "." + pad(returnDate.getDate()) + " " + pad(returnDate.getHours()) + ":" + pad(returnDate.getMinutes());
      break;
    case "HH:mm":
      returnDate = pad(returnDate.getHours()) + ":" + pad(returnDate.getMinutes());
      break;
    default:
      break;
  }

  // 날짜 자리수 처리
  function pad(num) {
    num = num + '';
    return num.length < 2 ? '0' + num : num;
  }
  return returnDate;
}

/**
 클립보드 복사
 */
function copyClipBoard(text) {
  // 임시의 textarea 생성
  const $textarea = document.createElement("textarea");

  // body 요소에 존재해야 복사가 진행됨
  document.body.appendChild($textarea);

  // 복사할 특정 텍스트를 임시의 textarea에 넣어주고 모두 셀렉션 상태
  $textarea.value = text;
  $textarea.select();

  // 복사 후 textarea 지우기
  document.execCommand('copy');
  document.body.removeChild($textarea);
}

function isMobile() {
  var UserAgent = navigator.userAgent;
  if (UserAgent.match(/iPhone|iPod|Android|Windows CE|BlackBerry|Symbian|Windows Phone|webOS|Opera Mini|Opera Mobi|POLARIS|IEMobile|lgtelecom|nokia|SonyEricsson/i) != null
      || UserAgent.match(/LG|SAMSUNG|Samsung/) != null) {
    return true;
  } else {
    return false;
  }
}

function goFileDown(sysFile, orgFile){
  location.href = "/download?fileName="+ encodeURIComponent(sysFile) + '&orgFileName='+encodeURIComponent(orgFile);
}
