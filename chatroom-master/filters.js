import Vue from 'vue'

//设置只显示几个字符串，默认20个
Vue.filter('filterAmount', function (value, n) {
        if (!n) n = 30;
        if (value && value.length > n) {
            value = value.substring(0, n) + '...';
        }
        return value;
    }
)
