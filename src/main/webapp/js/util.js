/**
 * Created by Mac on 2017/5/18.
 */




var util = {


    ajax: function (url, data, type, async) {
        var data;

        $.ajax({
            type: type,
            url: url,
            async: async,
            data: data,
            dataType: "json",
            success: function (result) {
                if (result.resultCode == 0) {
                    data = result;
                }


            }
        });


    },


    ip: function () {

        var url = 'http://chaxun.1616.net/s.php?type=ip&output=json&callback=?&_=' + Math.random();
        $.getJSON(url, function (data) {

            var IP = data.Ip;
            $("#IP").text(IP);

        });

    },


    dateFormat: function (time) {
        var datetime = new Date();
        datetime.setTime(time);
        var year = datetime.getFullYear();
        var month = datetime.getMonth() + 1 < 10 ? "0" + (datetime.getMonth() + 1) : datetime.getMonth() + 1;
        var date = datetime.getDate() < 10 ? "0" + datetime.getDate() : datetime.getDate();
        var hour = datetime.getHours() < 10 ? "0" + datetime.getHours() : datetime.getHours();
        var minute = datetime.getMinutes() < 10 ? "0" + datetime.getMinutes() : datetime.getMinutes();
        var second = datetime.getSeconds() < 10 ? "0" + datetime.getSeconds() : datetime.getSeconds();
        return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
    },


// 获取当前网址的某个参数值
    getUrlPara: function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    },

//服务器时间
    escTime: function () {

        $.ajax({type: 'HEAD', async: true}).success(function (data, status, xhr) {

            var t = new Date(xhr.getResponseHeader('Date'));
            var time = util.dateFormat(t)
            return time;

        });

    },
    /*获取指定cookie 的值  ----需要测试*/
    getCookie: function (objname) {

        var cookie = document.cookie;
        if (cookie.indexOf(";") != -1) {
            var arrstr = cookie.split("; ");
            for (var i = 0; i < arrstr.length; i++) {
                var temp = arrstr[i].split("=");
                if (temp[0] == objname)
                    console.error(temp[1]);
                return unescape(temp[1]);
            }


        } else {
            var j = cookie.split("=");
            console.log(j[0]);
            if (j[0] == objname) {
                console.log(unescape("1111111" + j[1]));

            }


        }


    },
    /*获取cookie :JSESSIONID --- 暂时不能用*/
    Cookie: function () {
        util.getCookie('JSESSIONID');


    },
};


//同步改变事件 ------onchang(this)
function changeInput(element) {
    $("#" + $(element).attr("id") + "2").val($(element).val());
};
