package com.time.application;

import com.time.nlp.TimeNormalizer;
import com.time.nlp.TimeUnit;
import com.time.util.DateUtil;

import java.net.URL;


/**
 * Created by xinqiyang on 2017/09/29.
 */
public class Main {
    public static void main(String[] args) {
        TimeNormalizer normalizer = null;
        try {
            URL url = TimeNormalizer.class.getResource("/TimeExp.m");
            System.out.println(url.toURI().toString());
            normalizer = new TimeNormalizer(url.toURI().toString());
        }catch (Exception e) {

        }
        normalizer.setPreferFuture(true);

        normalizer.parse("Hi，all.下周一下午三点开会");// 抽取时间
        TimeUnit[] unit = normalizer.getTimeUnit();
        System.out.println("Hi，all.下周一下午三点开会");
        System.out.println(DateUtil.formatDateDefault(unit[0].getTime()) + "-" + unit[0].getIsAllDayTime());


        normalizer.parse("****** Hi，all.请问10月3号-10月5号有房么，可以接待么？不好意思，5号已经租出去了，6号可以接待");// 抽取时间
        unit = normalizer.getTimeUnit();
        System.out.println("****** Hi，all.请问10月3号-10月5号有房么，可以接待么？不好意思，5号已经租出去了，6号可以接待");
        for(int i=0; i<unit.length; i++){
            System.out.println(DateUtil.formatDateDefault(unit[i].getTime()) + "-" + unit[i].getIsAllDayTime());
        }

        normalizer.parse("早上六点起床");// 注意此处识别到6天在今天已经过去，自动识别为明早六点（未来倾向，可通过开关关闭：new TimeNormalizer(classPath+"/TimeExp.m", false)）
        unit = normalizer.getTimeUnit();
        System.out.println("早上六点起床");
        System.out.println(DateUtil.formatDateDefault(unit[0].getTime()) + "-" + unit[0].getIsAllDayTime());

        normalizer.parse("周一开会");// 如果本周已经是周二，识别为下周周一。同理处理各级时间。（未来倾向）
        unit = normalizer.getTimeUnit();
        System.out.println("周一开会");
        System.out.println(DateUtil.formatDateDefault(unit[0].getTime()) + "-" + unit[0].getIsAllDayTime());

        normalizer.parse("下下周一开会");//对于上/下的识别
        unit = normalizer.getTimeUnit();
        System.out.println("下下周一开会");
        System.out.println(DateUtil.formatDateDefault(unit[0].getTime()) + "-" + unit[0].getIsAllDayTime());

        normalizer.parse("6:30 起床");// 严格时间格式的识别
        unit = normalizer.getTimeUnit();
        System.out.println("6:30 起床");
        System.out.println(DateUtil.formatDateDefault(unit[0].getTime()) + "-" + unit[0].getIsAllDayTime());

        normalizer.parse("6-3 春游");// 严格时间格式的识别
        unit = normalizer.getTimeUnit();
        System.out.println("6-3 春游");
        System.out.println(DateUtil.formatDateDefault(unit[0].getTime()) + "-" + unit[0].getIsAllDayTime());

        normalizer.parse("6月3春游");// 残缺时间的识别 （打字输入时可便捷用户）
        unit = normalizer.getTimeUnit();
        System.out.println("6月3春游");
        System.out.println(DateUtil.formatDateDefault(unit[0].getTime()) + "-" + unit[0].getIsAllDayTime());

        normalizer.parse("明天早上跑步");// 模糊时间范围识别（可在RangeTimeEnum中修改
        unit = normalizer.getTimeUnit();
        System.out.println("明天早上跑步");
        System.out.println(DateUtil.formatDateDefault(unit[0].getTime()) + "-" + unit[0].getIsAllDayTime());

        normalizer.parse("本周日到下周日出差");// 多时间识别
        unit = normalizer.getTimeUnit();
        System.out.println("本周日到下周日出差");
        System.out.println(DateUtil.formatDateDefault(unit[0].getTime()) + "-" + unit[0].getIsAllDayTime());
        System.out.println(DateUtil.formatDateDefault(unit[1].getTime()) + "-" + unit[1].getIsAllDayTime());

        normalizer.parse("周四下午三点到五点开会");// 多时间识别，注意第二个时间点用了第一个时间的上文
        unit = normalizer.getTimeUnit();
        System.out.println("周四下午三点到五点开会");
        System.out.println(DateUtil.formatDateDefault(unit[0].getTime()) + "-" + unit[0].getIsAllDayTime());
        System.out.println(DateUtil.formatDateDefault(unit[1].getTime()) + "-" + unit[1].getIsAllDayTime());

        //新闻随机抽取长句识别（2016年6月7日新闻,均以当日0点为基准时间计算）
        //例1
        normalizer.parse("7月 10日晚上7 点左右，六安市公安局裕安分局平桥派出所接到辖区居民戴某报警称，到同学家玩耍的女儿迟迟未归，手机也打不通了。很快，派出所又接到与戴某同住一小区的王女士报警：下午5点左右，12岁的儿子和同学在家中吃过晚饭后，"
                + "带着3 岁的弟弟一起出了门，之后便没了消息，手机也关机了。短时间内，接到两起孩子失联的报警，值班民警张晖和队友立即前往小区。", "2016-07-19-00-00-00");
        unit = normalizer.getTimeUnit();
        System.out.println("7月 10日晚上7 点左右，六安市公安局裕安分局平桥派出所接到辖区居民戴某报警称，到同学家玩耍的女儿迟迟未归，手机也打不通了。很快，派出所又接到与戴某同住一小区的王女士报警：下午5点左右，12岁的儿子和同学在家中吃过晚饭后，"
                + "带着3 岁的弟弟一起出了门，之后便没了消息，手机也关机了。短时间内，接到两起孩子失联的报警，值班民警张晖和队友立即前往小区。");
        for(int i = 0; i < unit.length; i++){
            System.out.println("时间文本:"+unit[i].Time_Expression +",对应时间:"+ DateUtil.formatDateDefault(unit[i].getTime()));
        }

        //例2
        normalizer.parse("《辽宁日报》今日报道，7月18日辽宁召开省委常委扩大会，会议从下午两点半开到六点半，主要议题为：落实中央巡视整改要求。", "2016-07-19-00-00-00");
        unit = normalizer.getTimeUnit();
        System.out.println("《辽宁日报》今日报道，7月18日辽宁召开省委常委扩大会，会议从下午两点半开到六点半，主要议题为：落实中央巡视整改要求。");
        for(int i = 0; i < unit.length; i++){
            System.out.println("时间文本:"+unit[i].Time_Expression +",对应时间:"+ DateUtil.formatDateDefault(unit[i].getTime()));
        }

        //
        normalizer.parse("周五下午7点到8点", "2017-07-19-00-00-00");
        unit = normalizer.getTimeUnit();
        System.out.println("周五下午7点到8点");
        for(int i = 0; i < unit.length; i++){
            System.out.println("时间文本:"+unit[i].Time_Expression +",对应时间:"+ DateUtil.formatDateDefault(unit[i].getTime()));
        }

        System.out.println("test");
    }
}
