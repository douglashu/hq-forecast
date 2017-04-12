package com.hq.app.olaf.ui.activity.shoukuan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.TextView;

import com.hq.app.olaf.ui.activity.NewIndexActivity;
import com.hq.app.olaf.util.StringUtil;
import com.hq.component.annotation.Layout;
import com.hq.component.network.KLog;
import com.hq.component.utils.JSON;
import com.hq.component.utils.StringUtils;
import com.hq.component.utils.TextHelper;
import com.cardinfo.reddy.util.Validations;
import com.hq.app.olaf.R;
import com.hq.app.olaf.ui.base.HqPayActivity;
import com.hq.app.olaf.ui.bean.order.ScannerOrderResp;
import com.hq.app.olaf.ui.enums.PayChannelEnum;
import com.hq.app.olaf.util.AppUtil;
import com.hq.app.olaf.util.Const;
import com.hq.app.olaf.util.DoubleParseUtil;
import com.hq.app.olaf.util.ToastHelper;

import java.math.BigDecimal;
import java.security.InvalidAlgorithmParameterException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

@Layout(layoutId = R.layout.activity_shoukuan)
public class ShoukuanActivity extends HqPayActivity implements  View.OnClickListener {

    // 标志用户按的是否是整个表达式的第一个数字,或者是运算符后的第一个数字
    private boolean firstDigit = true;
    // 计算的中间结果。
    private double resultNum = 0.0;
    private double tempRstNum = 0.0;
    // 当前运算的运算符
    private String operator = "=";

    // 操作是否合法
    private boolean operateValidFlag = true;

    /** 计算器上的功能键的显示名字 */
    private final String[] COMMAND = { "删除", "CE", "清空" };

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.one)
    TextView one;

    @Bind(R.id.two)
    TextView two;

    @Bind(R.id.three)
    TextView three;

    @Bind(R.id.four)
    TextView four;

    @Bind(R.id.five)
    TextView five;

    @Bind(R.id.six)
    TextView six;

    @Bind(R.id.seven)
    TextView seven;

    @Bind(R.id.eight)
    TextView eight;

    @Bind(R.id.nine)
    TextView nine;

    @Bind(R.id.zero)
    TextView zero;

    @Bind(R.id.point)
    TextView point;

    @Bind(R.id.add)
    TextView add;

    @Bind(R.id.delete)
    TextView delete;

    @Bind(R.id.clear)
    TextView clear;

    @Bind(R.id.value)
    TextView resultText;

    @Bind(R.id.tv_sum)
    TextView tv_sum;

    @Bind(R.id.title)
    TextView title;

    String sumText="";

    List<Double> sumTemp = new ArrayList<>();
    List<String> operTemp = new ArrayList<>();

    DecimalFormat format = new DecimalFormat("###,###,##0.00");

    double maxInputNum=999999.99;//允许输入的最大值,100w

    double currResult=0.0; //记录当前的结果

    String  currResultStr=""; //记录当前按过的数字

    String yunSuanFu="+-*/";//支持的运算符

    boolean isCommitResult=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbarWithoutListener(toolbar);
        tv_sum.setEllipsize(TextUtils.TruncateAt.START);
        title.setText("收款");

        zero.setOnClickListener(this);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        point.setOnClickListener(this);
        add.setOnClickListener(this);
        clear.setOnClickListener(this);
        delete.setOnClickListener(this);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forward(NewIndexActivity.class);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
                finish();
            }
        });

    }


    @OnClick({R.id.forwardSaoMaActivity,R.id.forwardSaoMaActivity2})
    public  void forwardScan(){

        Bundle bundle=new Bundle();
        String money=resultText.getText().toString();
        bundle.putString("money",money);
        if(DoubleParseUtil.parse(money)<=0){
            showSnackBar("金额必须大于0");
            return;
        }
       // forward(SaoMaActivity.class,bundle);
        forward(ScannerActivity.class,bundle);
        finish();
    }




    /**
     * 处理事件
     */
    @Override
    public void onClick(View view) {

        // 获取事件源的标签
        String label = ((TextView)view).getText().toString();
        if (label.equals(COMMAND[0])) {
            // 用户按了"删除"键
            handleBackspace();
        } else if (label.equals(COMMAND[1])) {
            // 用户按了"CE"键
            resultText.setText("0.00");
            currResultStr="";
        } else if (label.equals(COMMAND[2])) {
            // 用户按了"清空"键
            handleC();
        } else if ("0123456789.".indexOf(label) >= 0) {
            // 用户按了数字键或者小数点键


            handleNumber(label);
            // handlezero(zero);
        } else {
            // 用户按了运算符键
            handleOperator(label);
        }

        //显示按下的结果记录
        tv_sum.setText(sumText);
    }

    private void handleBackspace() {
        if(StringUtil.isEmpty(sumText)){
            return;
        }
        if(isEndWithYunSuanFu()){
            sumTemp.remove(sumTemp.size()-1);
            operTemp.remove(operTemp.size()-1);
        }else {
            if (sumTemp.size() > 0) {
                double preRst = sumTemp.get(sumTemp.size() - 1);
                if (operTemp.size() == 0) {
                    resultText.setText(sumText);
                } else {
                    String preOper = operTemp.get(operTemp.size() - 1);

                    currResultStr = sumText.substring(sumText.lastIndexOf(preOper) + 1, sumText.length()-1);
                    try {
                        tempRstNum = calcute(preRst, getNumberFromText(), preOper);

                        // 双精度浮点数的运算
                        long t1;
                        double t2;
                        t1 = (long) tempRstNum;
                        t2 = tempRstNum - t1;
                        if (t2 == 0) {
                            resultText.setText(formatText(String.valueOf(t1)));
                        } else {
                            resultText.setText(formatText(String.valueOf(tempRstNum)));
                        }
                    } catch (InvalidAlgorithmParameterException e) {
                        resultText.setText(e.getMessage());
                    }
                }
            }
        }
        sumText = sumText.substring(0,sumText.length()-1);
        if(sumTemp.size()==0) {
            if(StringUtil.isEmpty(sumText)){
                handleC();
            }else {
                currResultStr = sumText;
                try {
                    tempRstNum = calcute(resultNum,getNumberFromText(),"=");
                    long t1;
                    double t2;
                    t1 = (long) tempRstNum;
                    t2 = tempRstNum - t1;
                    if (t2 == 0) {
                        resultText.setText(formatText(String.valueOf(t1)));
                    } else {
                        resultText.setText(formatText(String.valueOf(tempRstNum)));
                    }
                } catch (InvalidAlgorithmParameterException e) {
                    resultText.setText(e.getMessage());
                }
            }
        }
        if(isEndWithYunSuanFu()){
            currResultStr = "";
        }

    }

    private void handleOperator(String key) {
        if(StringUtil.isEmpty(sumText)&&isClickYunSuanFu(key)){
            return;
        }
//        //当前值以运算符结尾并且再次按运算符
        if(isEndWithYunSuanFu() && isClickYunSuanFu(key)){
            return;
        }
        String result=resultText.getText().toString();
        if(sumText.endsWith(".")){
            sumText = sumText.substring(0,sumText.length()-1);
        }
        sumText+=(key);

//        try {
//            resultNum = calcute(resultNum,getNumberFromText(),operator);
            resultNum = tempRstNum;
            tempRstNum = 0.0;
            sumTemp.add(resultNum);
//
//                // 双精度浮点数的运算
//            long t1;
//            double t2;
//            t1 = (long) resultNum;
//            t2 = resultNum - t1;
//            if (t2 == 0) {
//                resultText.setText(formatText(String.valueOf(t1)));
//            } else {
//                resultText.setText(formatText(String.valueOf(resultNum)));
//            }
//        } catch (InvalidAlgorithmParameterException e) {
//            resultText.setText(e.getMessage());
//        }

        currResultStr="";
        operator = key;
        operTemp.add(operator);
        firstDigit = true;
//        isCommitResult=true;
    }

    private double calcute(double a,double b,String oper ) throws InvalidAlgorithmParameterException {
        if (oper.equals("/")) {
            // 除法运算
            // 如果当前结果文本框中的值等于0
            if (b == 0.0) {
                // 操作不合法
                throw new InvalidAlgorithmParameterException("除数不能为零");
            } else {
                a /= getNumberFromText();
            }
        } else if (oper.equals("1/x")) {
            // 倒数运算
            if (a == 0.0) {
                // 操作不合法
                throw new InvalidAlgorithmParameterException( "零没有倒数");
            } else {
                a = 1 / a;
            }
        } else if (oper.equals("+")) {
            // 加法运算

            a += getNumberFromText();
        } else if (oper.equals("-")) {
            // 减法运算
            a -= getNumberFromText();
        } else if (oper.equals("*")) {
            // 乘法运算
            a *= getNumberFromText();
        } else if (oper.equals("sqrt")) {
            // 平方根运算
            a = Math.sqrt(a);
        } else if (oper.equals("%")) {
            // 百分号运算，除以100
            a = a / 100;
        } else if (oper.equals("+/-")) {
            // 正数负数运算
            a = a * (-1);
        } else if (oper.equals("=")) {
            // 赋值运算
            a = getNumberFromText();
        }
        return a;
    }
    private void handleNumber(String key){
        String result=resultText.getText().toString();
        double d_result=DoubleParseUtil.parse(resultText.getText().toString());

        /*******************处理禁止按键情况************************/
        //处理结果为0，继续按0建不可用
//        if(result.equals("0.00") && key.equals("0")){
//            return;
//        }
        //处理不能0开头不能多个0
        if("0".equals(currResultStr)&&key.equals("0")){
            return;
        }
        if("0".equals(currResultStr)&&!key.equals(".")){
            currResultStr = "";
            sumText = sumText.substring(0,sumText.length()-1);
        }
        //封顶不超过100W
        if(d_result>maxInputNum){
            return;
        }
        /**
         //         *  限制小数位2位:(按过运算符后又可以输入)
         //         *  1.还没有按过运算符号：
         //         *  2. 按过运算符后，也已经输入了2位小数
         //         *  3.currResultStr 当前按过的数字结果已经有2位小数
         //         */
        if( AppUtil.checkTwoNum(currResultStr)){
            return;
        }
        //如果已经是小数又输入.则禁止
        if(currResultStr.contains(".")&&".".equals(key)){
            return;
        }

        if(firstDigit){
            if(key.equals(".")&&StringUtil.isEmpty(currResultStr)) {//如果是“.”,显示0.
                currResultStr = "0.";
                sumText+="0";
            }else{
                currResultStr +=key;
            }
        }else{

            currResultStr +=key;
            
        }
        try {
            tempRstNum = calcute(resultNum,getNumberFromText(),operator);
            long t1;
            double t2;
            t1 = (long) tempRstNum;
            t2 = tempRstNum - t1;
            if (t2 == 0) {
                resultText.setText(formatText(String.valueOf(t1)));
            } else {
                resultText.setText(formatText(String.valueOf(tempRstNum)));
            }
        } catch (InvalidAlgorithmParameterException e) {
            resultText.setText(e.getMessage());
        }
        sumText+=key;

    }

//    /**
//     * 处理Backspace键被按下的事件
//     */
//    private void handleBackspace() {
//        String text = resultText.getText().toString();
//
//        int i = text.length();
//        //当前值不为空也不为0
//        if (i > 0 && !text .equals("0.00") ) {
//
//            /*******************处理禁止按键情况************************/
////            if(isEndWithYunSuanFu() && isCommitResult){//符号结尾不能退格
////                return;
////            }
//            /*******************处理禁止按键情况************************/
//            //去掉小数点后的0,当前显示值中去掉最后加入的一位
//            for(int index=i;index>0;index--){
//                if(text.substring(index-1).equals(currResultStr.substring(currResultStr.length()-1))){
//                    if(currResultStr.substring(currResultStr.length()-1).equals(".")){//如果是“.”也要去掉
//                        text=text.substring(0,text.length()-1);
//                    }
//                    break;
//                }else{
//                    text=text.substring(0,text.length()-1);
//                }
//            }
//            // 退格，将文本最后一个字符去掉
//            text = text.substring(0, text.length() - 1);
//            if(currResultStr.length()>0)
//                currResultStr=currResultStr.substring(0,currResultStr.length()-1);
//
//            if (text.length() == 0 ) {
//                if(resultNum==0) {
//                    // 如果文本没有了内容，则初始化计算器的各种值
//                    handleC();
//                }else{
//                    // 初始化输入的值
//                    resultText.setText("0.00");
//                }
//
//            } else {
//                // 显示新的文本
//                resultText.setText(formatText(text));
//            }
//        }
//    }
//
//    /**
//     * 处理数字键被按下的事件
//     *
//     * @param key
//     */
//    private void handleNumber(String key) {
//
//        String result=resultText.getText().toString();
//        double d_result=DoubleParseUtil.parse(resultText.getText().toString());
//
//        /*******************处理禁止按键情况************************/
//        //处理结果为0，继续按0建不可用
//        if(result.equals("0.00") && key.equals("0")){
//            return;
//        }
//
//        //封顶不超过1一个亿
//        if(d_result>maxInputNum){
//            return;
//        }
//
//        /**
//         *  限制小数位2位:(按过运算符后又可以输入)
//         *  1.还没有按过运算符号：
//         *  2. 按过运算符后，也已经输入了2位小数
//         *  3.currResultStr 当前按过的数字结果已经有2位小数
//         */
//        //当前值以运算符结尾并且再次按运算符
//        //去掉里面的标准数字格式的","
//        result=result.replaceAll(",","");
//        if( AppUtil.checkTwoNum(currResultStr) && !currResultStr.equals("0.00")
//        //|| (isEndWithYunSuanFu() && AppUtil.checkTwoNum(result) && !isCommitResult && !("0.00").equals(result))
//             //   || ( sumText.equals("") && AppUtil.checkTwoNum(result) && !("0.00").equals(result))
//                ){
//            return;
//        }
//
//        /*******************处理禁止按键情况************************/
//
//        currResultStr+=key;
//        isCommitResult=false;
//
//        //按数字不记录结果，只显示值
//
//        // 输入的第一个数字
//        if (firstDigit) {
//            if(key.equals(".")) {//如果是“.”,显示0.
//                if(StringUtils.isEmpty(sumText)) {
//                    resultText.setText("0" + key + "00");
//                }
//                tempText = "0.00";
//            }else{
//                if(StringUtils.isEmpty(sumText)) {
//                    resultText.setText(key + ".00");
//                }
//                tempText = key + ".00";
//            }
//            //以运算符结尾
//            if(isEndWithYunSuanFu() ){
//                //按键为“.”，设置显示值为0.
//                if(key.equals(".")) {
//                    tempText="0.00";
//                }else {
//                    //按键为数字，设置显示值为当前按键数字.
//                    tempText= key;
//                }
//            }
//
////             输入的是小数点，并且之前没有小数点，
//        } else if (key.equals(".")) {
//            /*** 按点不记录值，只显示值**/
//            //没有小数点
//            if(result.indexOf(".") < 0) {
//                //设置显示值为:当前显示值+ .
//                tempText = result + ".";
//            }else{
//
//            }
//
//        } else if (!key.equals(".")) {//按的是数字
//
//            //以运算符结尾就设置显示值为:当前显示值+ 当前按键值
//            // 如果输入的不是小数点，则将数字附在结果文本框的后面
//            tempText = formatText(currResultStr );
//
//
//        }
//        // 以后输入的肯定不是第一个数字了
//        firstDigit = false;
//        sumText+=key;
//    }

    /**
     * 当前输入值是否以运算符结尾
     */
    private boolean isEndWithYunSuanFu(){
        if(sumText.length()>0 && yunSuanFu.indexOf(sumText.substring(sumText.length()-1)) >-1 ){
            return  true;
        }
        return  false;
    }


//    /**
//     * 处理C键被按下的事件
//     */
    private void handleC() {
        // 初始化计算器的各种值
        resultText.setText("0.00");
        firstDigit = true;
        operator = "=";
        sumText="";
//        currResult=0;
        currResultStr="";
        sumTemp = new ArrayList<>();
        operTemp = new ArrayList<>();
//        isCommitResult=false;

    }
//
//    /**
//     * 处理运算符键被按下的事件
//     *
//     * @param key
//     */
//    private void handleOperator(String key) {
//        String result=resultText.getText().toString();
//        //当前值以运算符结尾并且再次按运算符
//        if(isEndWithYunSuanFu() && isClickYunSuanFu(key)){
//            return;
//        }
//
//        //还没提交结果，防止多次提交
//        if(isCommitResult){return;}
//
//        //记录按下的键
////        if(result.endsWith(".")){
////            result=result.substring(0,result.length()-1);
////        }
//        if(sumText.endsWith(".")){
//            sumText = sumText.substring(0,sumText.length()-1);
//        }
//        sumText+=(key);
//
//        if (operator.equals("/")) {
//            // 除法运算
//            // 如果当前结果文本框中的值等于0
//            if (getNumberFromText() == 0.0) {
//                // 操作不合法
//                operateValidFlag = false;
//                resultText.setText("除数不能为零");
//            } else {
//                resultNum /= getNumberFromText();
//            }
//        } else if (operator.equals("1/x")) {
//            // 倒数运算
//            if (resultNum == 0.0) {
//                // 操作不合法
//                operateValidFlag = false;
//                resultText.setText("零没有倒数");
//            } else {
//                resultNum = 1 / resultNum;
//            }
//        } else if (operator.equals("+")) {
//            // 加法运算
//
//            resultNum += getNumberFromText();
//        } else if (operator.equals("-")) {
//            // 减法运算
//            resultNum -= getNumberFromText();
//        } else if (operator.equals("*")) {
//            // 乘法运算
//            resultNum *= getNumberFromText();
//        } else if (operator.equals("sqrt")) {
//            // 平方根运算
//            resultNum = Math.sqrt(resultNum);
//        } else if (operator.equals("%")) {
//            // 百分号运算，除以100
//            resultNum = resultNum / 100;
//        } else if (operator.equals("+/-")) {
//            // 正数负数运算
//            resultNum = resultNum * (-1);
//        } else if (operator.equals("=")) {
//            // 赋值运算
//            resultNum = getNumberFromText();
//        }
//
//        currResult=resultNum;
//        //清空结果记录
//        currResultStr="";
//
//        if (operateValidFlag) {
//            // 双精度浮点数的运算
//            long t1;
//            double t2;
//            t1 = (long) resultNum;
//            t2 = resultNum - t1;
//            if (t2 == 0) {
//                resultText.setText(formatText(String.valueOf(t1)));
//            } else {
//                resultText.setText(formatText(String.valueOf(resultNum)));
//            }
//        }
//        // 运算符等于用户按的按钮
//        lastOperator = operator;
//        operator = key;
//        firstDigit = true;
//        operateValidFlag = true;
//        isCommitResult=true;
//    }

    /**
     * 判断当前按键是否为运算符
     * @param key
     * @return
     */
    public  boolean isClickYunSuanFu(String key){
        return yunSuanFu.contains(key);
    }



    /**
     * 判断当前值中不否包含运算符
     * @return
     */
    public  boolean isNotHasYunSuanFu(){
        for(int i=0;i<yunSuanFu.length();i++){
            if(sumText.indexOf(yunSuanFu.charAt(i))>-1){
                return false;
            }
        }
        return true;
    }


    /**
     * 从结果文本框中获取数字
     *
     * @return
     */
    private double getNumberFromText() {
        double result = 0;
        String text=currResultStr;
        try {
            //去掉里面的标准数字格式的","
            text=text.replaceAll(",","");
            result=Double.valueOf(text).doubleValue();
        } catch (NumberFormatException e) {
        }
        return result;
    }

    /**
     * 通用数字格式化显示的文本值
     */
    public String formatText(String value){

        //去掉里面的标准数字格式的","
        value=value.replaceAll(",","");


        BigDecimal result=null;
        //结果包含"." 但又不是“.”,
        if(value.endsWith(".")){//最后一位是"."
            return value;
        }else if(String.valueOf(value).contains("." )
                && String.valueOf(value).length()>1) {
            int length=String.valueOf(value).split("\\.")[1].length();
            //限制长度为2位
            length=length>2?2:length;
            result=new BigDecimal(Double.valueOf(value)).setScale(length, BigDecimal.ROUND_HALF_UP);
        }else{
            result = new BigDecimal(Double.valueOf(value));
        }
       /* if (AppUtil.checkTwoNum(String.valueOf(result))) {
            format = new DecimalFormat("###,###,##0.00");
            value=format.format(result);
        } else if (AppUtil.checkOneNum(String.valueOf(result))) {
            format = new DecimalFormat("###,###,##0.00");
            value=format.format(result);
        } else if (result.doubleValue()>999) {
            format = new DecimalFormat("###,###,##0");
            value=format.format(result);
        }*/
        format = new DecimalFormat("###,###,##0.00");
        value=format.format(result);
        return  value;
    }

    private String getPayType(PayChannelEnum channel) {
        switch (channel) {
            case ALI_PAY:
                return "支付宝";
            case WEIXIN_PAY:
                return "微信";
        }
        return "";
    }

}
