/**
 * Created by Mac on 2017/3/27.
 * 在input中 添加
 * data-validate=""  用于各种验证项
 * data-tips=""  为提示信息
 *
 * 在from 中添加 onsubmit="return FormChecker.check(this)"
 *
 *
 *
 *
 *
 * 需要引入
 *<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.js"></script>
 * <script src="https://cdn.bootcss.com/layer/3.0.1/layer.js"></script>
 *
 */

//同步改变事件 ------onchang(this)
function changeInput(element) {
    $("#" + $(element).attr("id") + "2").val($(element).val());
};

// 获取当前网址的某个参数值
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}


//删除事件
function confirmInfo(info, ref) {
    var r = confirm(info);
    if (r) {
        window.location.href = ref;

    }
}


var FormChecker = {
    check: function (formEle) {
        var checkResult = true;
        console.log("tijioa1");
        var r = confirm("确认提交数据吗 ");

        if (!r) {
            console.log("1");
            return false;
        }
        ;

        var form = $(formEle);
        var sum = 0;
        var sums = 0;
        //查找所有的产品数量
        form.find("input[data-sum]").each(function () {
            var sumValue = $(this).val();
            sum = Number(sum) + Number(sumValue);
            sums++;
        });
        //多个产品但是没有数量
        if (sum <= 0 && sums > 0) {
            layer.msg('必须有一个商品', {icon: 1, time: 1000});
            console.log("2")
            return false;
        }
        ;


        form.find("input[data-rule]").each(function () {
            checkResult = FormChecker.checkInput($(this));
            if (checkResult == false) {
                console.log("3");
                return false;
            }
        });

        form.find("input[data-validate]").each(function () {
            checkResult = FormChecker.validate($(this));
            if (checkResult == false) {
                return false;
            }

        });


        return checkResult;
    },
    validate: function (input) {
        var dataVal = input.data('validate');
        if (dataVal != null && dataVal.length > 0) {
            if (FormChecker.checkInputOneValidate(input, dataVal) == false) {
                return false;


            }


        }


    },
    checkInputOneValidate: function (input, dataval) {
        var value = $.trim(input.val());
        //提示信息
        var tips = input.data("tips");

        if (dataval == 'phone') {
            var length = value.length;
            var mobile = /^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/;
            if (length != 11 || !mobile.test(value)) {
                layer.msg(tips, {icon: 1, time: 1000});

                return false;
            }
            ;
        }
        ;

        if (dataval == 'tel') {
            var mobile = /^((\d{3,4}\-)|)\d{7,8}(|([-\u8f6c]{1}\d{1,5}))$/;
            if (!mobile.test(value) || value == "") {
                layer.msg(tips, {icon: 1, time: 1000});

                return false
            }
            ;
        }
        ;

        if (dataval == 'relname') {
            //大小写  下划线
            var relname = /[\u4e00-\u9fa5]{1,20}|[a-zA-Z\.\s]{1,20}/;
            if (!relname.test(value)) {
                layer.msg(tips, {icon: 1, time: 1000});

                return false
            }
        }
        ;
        if (dataval == 'qq') {
            var qq = /^\d{5,10}$/;
            if (!qq.test(value)) {
                layer.msg(tips, {icon: 1, time: 1000});

                return false;
            }
        }
        ;
        if (dataval == 'weixin') {
            var weixin = /^[a-zA-Z\d_]{5,}$/;
            if (!weixin.test(value)) {
                layer.msg(tips, {icon: 1, time: 1000});

                return false;

            }

        }
        ;
        if (dataval == 'userAccount') {
            var userAccount = /^(([\u4e00-\u9fa5])|([a-zA-Z0-9_-]))+$/;

            if (!userAccount.test(value)) {
                layer.msg(tips, {icon: 1, time: 1000});

                return false;
            }
        }
        ;
        //银行卡号
        if (dataval == 'creditcard') {
            var creditcard = /^(\d{16}|\d{19})$/;

            if (creditcard.test(value)) {
                layer.msg(tips, {icon: 1, time: 1000});

                return false;
            }
        }
        ;
        //身份证号
        if (dataval == 'identityNumber') {
            var identityNumber1 = /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/;
            var identityNumber2 = /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/;

            if (!(identityNumber1.test(value) || identityNumber2.test(value))) {
                layer.msg(tips, {icon: 1, time: 1000});

                return false;
            }
        }
        ;
        // 整数
        if (dataval == 'postNumber') {
            var postNumber = /^[0-9]*[1-9][0-9]*$/;

            if (postNumber.test(value)) {
                layer.msg(tips, {icon: 1, time: 1000});

                return false;
            }
        }
        ;

    },

    checkInput: function (input) {
        //检测一个输入框的数据校验
        var dataRule = input.data("rule");
        var rules = null;
        if (dataRule.indexOf(";") != -1) {
            rules = dataRule.split(";");
        }
        if (rules != null && rules.length > 0) {
            console.log('rules != null && rules.length > 0');
            for (var i in rules) {
                if (FormChecker.checkInputOneRule(input, rules[i]) == false) {

                    return false;
                }
            }
        } else {
            var value = $.trim(input.val());
            var tips = input.data("tips");
            //物流产品检索

            if (dataRule == "required" && !value) {
                console.log('dataRule == "required" && !value');
                // alert(tips);
                layer.msg(tips, {icon: 1, time: 1000});

                return false;
            }


        }

        return true;
    },
    checkInputOneRule: function (input, rule) {
        var value = $.trim(input.val());
        var tips = input.data("tips");
        var result = true;
        if (rule == "required" && !value) {
            console.log('rule == "required" && !value');
            // alert(tips);
            layer.msg(tips, {icon: 1, time: 1000});
            result = false;
            console.log(result + "====" + 1);

        } else if (rule.indexOf("len") != -1) {
            console.log('rule.indexOf("len") != -1');
            var vl = value.length;
            if (rule.indexOf("<=")) {
                console.log('rule.indexOf("<=")');
                var cl = Number(rule.substring(5, rule.length));
                if (vl > cl) {
                    console.log(vl + '----' + cl);
                    // alert(tips);
                    layer.msg(tips, {icon: 1, time: 1000});
                    result = false;
                }
            }
            console.log(result + "======" + 2);
        }
        return result;
    }

}


