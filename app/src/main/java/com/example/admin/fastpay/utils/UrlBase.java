package com.example.admin.fastpay.utils;

import com.zxz.www.base.utils.SystemSettingUtil;

/**
 * Created by sun on 2017/5/11.
 */

public class UrlBase {

//   isv.ccmknt.com  http://kf.wtehui.com   https://d.umxnt.com/api/merchant/login     www.miaodaokeji.cn

//    public static String LOGINURL = "https://isv.ccmknt.com/api/merchant/login";//登录
//
//    //账单
//    public static String ZHANGDAN = "https://isv.ccmknt.com/api/merchant/Order";
//
//    //收银员
//    public static String SHOUYINYUAN = "https://isv.ccmknt.com/api/merchant/me";
//
//    //扫码枪入库
//    public static String SAOMARUKU = "https://isv.ccmknt.com/api/merchant/TradePay";
//
//    //常见问题
//    public static String CHANGJIANWENTI = "https://isv.ccmknt.com/api/merchant/Questions";
//
//
//    //门店信息
//    public static String MENDIAN = "https://isv.ccmknt.com/api/merchant/store";
//
//    //添加收银员
//    public static String ADDSHOUYINYUAN = "https://isv.ccmknt.com/api/merchant/add";
//
//    //生成二维码
//    public static String SHENGCHENGERWEIMA = "https://isv.ccmknt.com/api/merchant/pay_qr_url";
//
//    //版本更新
//    public static String UPDATABANBEN = "https://isv.ccmknt.com/api/merchant/appUpdate";
//
//    //个人信息
//    public static String DANGEDINGDAN = "https://isv.ccmknt.com/api/merchant/TradeQuery";
//
//    //获取收银员信息
//    public static  String GETCASHIER = "https://isv.ccmknt.com/api/merchant/getCashier";
//
//    //获取店铺
//    public static  String GETANNEX = "https://isv.ccmknt.com/api/merchant/storeList";
//
//    //生成账单
//    public static  String GETCOUNT = "https://isv.ccmknt.com/api/merchant/getTotalAmount";
//
//    //邀请二维码
//    public static String ERWEIMA = "https://isv.ccmknt.com/api/merchant/expandMerchant?id=";
//
//    //首页自定义接口
//    public static  String LISTVIEW = "https://isv.ccmknt.com/api/merchant/getMenu";
//
//    //设备列表
//    public static  String SHEBEI = "https://isv.ccmknt.com/api/merchant/getMachine";
//
//    //设备配置
//    public static  String SHEBEIPEIZHI = "https://isv.ccmknt.com/api/merchant/getMachineCfg";
//
//    //设备配置修改
//    public static  String SHEBEIXIUGAI = "https://isv.ccmknt.com/api/merchant/setMachineCfg";
//
//    //添加设备    https://d.umxnt.com/api/merchant/addMachine添加
//    public static  String TIANJIASHEBEI = "https://isv.ccmknt.com/api/merchant/addMachine";
//
//    //删除设备
//    public static  String DELETESHEBEI = "https://isv.ccmknt.com/api/merchant/delMachine";


//http://www.miaodaochina.cn

    public static String LOGINURL = "https://www.miaodaochina.cn/api/merchant/login";//登录

    //账单
    public static String ZHANGDAN = "https://www.miaodaochina.cn/api/merchant/Order";

    //收银员
    public static String SHOUYINYUAN = "https://www.miaodaochina.cn/api/merchant/me";

    //扫码枪入库
    public static String SAOMARUKU = "https://www.miaodaochina.cn/api/merchant/TradePay";

    //常见问题
    public static String CHANGJIANWENTI = "https://www.miaodaochina.cn/api/merchant/Questions";

    //门店信息
    public static String MENDIAN = "https://www.miaodaochina.cn/api/merchant/store";

    //添加收银员
    public static String ADDSHOUYINYUAN = "https://www.miaodaochina.cn/api/merchant/add";

    //生成二维码
    public static String SHENGCHENGERWEIMA = "https://www.miaodaochina.cn/api/merchant/pay_qr_url";

    //版本更新
    public static String UPDATABANBEN = "https://www.miaodaochina.cn/api/merchant/appUpdate";

    //个人信息
    public static String DANGEDINGDAN = "https://www.miaodaochina.cn/api/merchant/TradeQuery";

    //获取收银员信息
    public static String GETCASHIER = "https://www.miaodaochina.cn/api/merchant/getCashier";

    //获取店铺
    public static String GETANNEX = "https://www.miaodaochina.cn/api/merchant/storeList";

    //生成账单
    public static String GETCOUNT = "https://www.miaodaochina.cn/api/merchant/getTotalAmount";

    //邀请二维码
    public static String ERWEIMA = "https://www.miaodaochina.cn/merchant/expandMerchant?id=";

    //首页自定义接口
    public static String LISTVIEW = "https://www.miaodaochina.cn/api/merchant/getMenu";

    //设备列表
    public static String SHEBEI = "https://www.miaodaochina.cn/api/merchant/getMachine";

    //设备配置
    public static String SHEBEIPEIZHI = "https://www.miaodaochina.cn/api/merchant/getMachineCfg";

    //设备配置修改
    public static String SHEBEIXIUGAI = "https://www.miaodaochina.cn/api/merchant/setMachineCfg";

    //添加设备    https://d.umxnt.com/api/merchant/addMachine添加
    public static String TIANJIASHEBEI = "https://www.miaodaochina.cn/api/merchant/addMachine";

    //删除设备
    public static String DELETESHEBEI = "https://www.miaodaochina.cn/api/merchant/delMachine";

    //我的银行卡
    public static String YINHANGKA = "https://www.miaodaochina.cn/api/merchant/myCard";

    //添加银行卡
    public static String ADDYINHANGKA = "https://www.miaodaochina.cn/api/merchant/mdAddCard";

    //大额提现
    public static String DAETIXIAN = "https://www.miaodaochina.cn/api/merchant/mdDePay";

    //结算记录  mdRecord
    public static String MDRECORD = "https://www.miaodaochina.cn/api/merchant/mdRecord";

    //获取图像验证码
    public static String CAPTCHA_URL = "https://www.miaodaochina.cn/api/captcha/" + System.currentTimeMillis();

    //获取其他验证码
    public static String SEND_CODE = "https://www.miaodaochina.cn/api/send/code";

    //注册
    public static String REGISTER_URL = "https://www.miaodaochina.cn/api/merchant/register";

    //实名认证
    public static String AUTH_URL = "https://www.miaodaochina.cn/api/merchant/authentication";

    //获取市列表
    public static String CITY_URL = "https://www.miaodaochina.cn/api/merchant/city";

    //获取省列表
    public static String PROVINCE_URL = "https://www.miaodaochina.cn/api/merchant/province";

    //获取银行列表
    public static String BINK_URL = "https://www.miaodaochina.cn/api/merchant/bank";

    //绑定银行卡
    public static String BINK_BINDING_URL = "https://www.miaodaochina.cn/api/merchant/bank/binding";

    //上传图片
    public static String UP_LOAD_FILE = "https://www.miaodaochina.cn/api/merchant/uploadfile";

    //店铺信get post
    public static String SHOP_URL = "https://www.miaodaochina.cn/api/merchant/shop";

    //店铺信息，get获取信用卡列表，post添加信用卡
    public static String CREDIT_CARD = "https://www.miaodaochina.cn/api/merchant/credit_bank";


    public static final String QUICKLY_PAY_URL = "http://39.108.170.213/miaodao/order";

    public static final String CODE_PAY_URL = "http://39.108.170.213/miaodao/orderResponse";

    public static final String PAY_WAY_URL = "http://39.108.170.213/miaodao/queryAllPayWay";

    public static final String REMARK_URL = " http://39.108.170.213/miaodao/showremark";



}
