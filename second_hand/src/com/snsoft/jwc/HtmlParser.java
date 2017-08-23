package com.snsoft.jwc;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;




/**
 * 
 * @copyright ：神农大学生软件创新中心 版权所有 (c) 2016
 * 
 * @author Mr. Soldier
 * 
 * @version 1.0
 * 
 * @date 2016年7月9日 下午2:34:16
 * 
 * @Description TODO
 *	学生信息的HTML数据解析器
 */
public class HtmlParser {

	/**
	 * 解析学生基本信息数据
	 * @param html
	 * @return
	 */
	public static StudentDataBean parserStudentBaseData(String html) {
		if(html == null){
			return null;
		}
		Document document = Jsoup.parse(html);
		Elements valueEles = document.getElementsByAttributeValue("width", "275");
		StudentDataBean bean = null;
		if (valueEles.size() > 0) {

			String studentId = valueEles.get(0).html();	//学号
			String name = valueEles.get(1).html();		//姓名
			String pingyin = valueEles.get(2).html();		//姓名拼音
			String englishName = valueEles.get(3).html();//英文姓名
			String usedName =  valueEles.get(4).html();	//曾用名
			String idNumber =  valueEles.get(5).html();	//身份证号
			String sex = valueEles.get(6).html();			//性别
			String type = valueEles.get(7).html();		//学生类别
			String specialType = valueEles.get(8).html();//特殊学生类型
			String state = valueEles.get(9).html();		//学籍状态
			String shouFeiLeiXing = valueEles.get(10).html();//收费类别
			String minZu =  valueEles.get(11).html();		//民族
			String jiGuan =valueEles.get(12).html();		//籍贯
			String birthday =valueEles.get(13).html();	//出生日期
			String zhengZhiMianMao =valueEles.get(14).html(); //政治面貌
			String kaoQu = valueEles.get(15).html();		//考区
			String biYeZhongXue = valueEles.get(16).html(); //毕业中学
			String gaoKaoZongFen = valueEles.get(17).html(); //高考总分
			String luQuHao = valueEles.get(18).html();       //录取号
			String kaoShengHao = valueEles.get(19).html();    //高考考生号
			String yuZhong = valueEles.get(20).html();			//入学考试语种
			String address = valueEles.get(21).html();			//通讯地址
			String youBian = valueEles.get(22).html();			//邮编
			String jiaZhangXinXi = valueEles.get(23).html();	//家长信息
			String ruXueRiQi = valueEles.get(24).html();		//入学日期
			String xiSuo = valueEles.get(25).html();			//系所
			String zhuanYe = valueEles.get(26).html();			//专业
			String zhuanYeFangXiang = valueEles.get(27).html();//专业方向
			String nianJi = valueEles.get(28).html();		//年级
			String banJi = valueEles.get(29).html();		//班级
			String shiFouYouXueJi = valueEles.get(30).html();	//是否有学籍
			String shiFouYouGuoJiaXueJi = valueEles.get(31).html(); //是否有国家学籍
			String xiaoQu = valueEles.get(32).html();     //校区
			String yiDongFou = valueEles.get(33).html();	//异动否
			String waiYuYuZhong = valueEles.get(34).html(); //外语语种
			String suSheDiZhi = valueEles.get(35).html();		//宿舍地址
			String yinCaiShiJiao = valueEles.get(36).html();   //因材施教
			String peiYangCengCi = valueEles.get(37).html();	//培养层次
			String peiYangFangShi = valueEles.get(38).html();	//培养方式
			String fenLiuFangXiang = valueEles.get(39).html();	//分流方向
			String shiFouLiXiao = valueEles.get(40).html();	//是否离校
			String beiZhu = valueEles.get(41).html();			//备注
			String beiZhu1 = valueEles.get(42).html();			//备注1
			String beiZhu2 = valueEles.get(43).html();			//备注2
			String beiZhu3 = valueEles.get(44).html();			//备注3
			
			bean = new StudentDataBean(studentId, name, pingyin, englishName,
					usedName, idNumber, sex, type, specialType, state, 
					shouFeiLeiXing, minZu, jiGuan, birthday, zhengZhiMianMao,
					kaoQu, biYeZhongXue, gaoKaoZongFen, luQuHao, kaoShengHao,
					yuZhong, address, youBian, jiaZhangXinXi, ruXueRiQi, xiSuo,
					zhuanYe, zhuanYeFangXiang, nianJi, banJi, shiFouYouXueJi, 
					shiFouYouGuoJiaXueJi, xiaoQu, yiDongFou, waiYuYuZhong, 
					suSheDiZhi, yinCaiShiJiao, peiYangCengCi, peiYangFangShi,
					fenLiuFangXiang, shiFouLiXiao, beiZhu, beiZhu1, beiZhu2, beiZhu3);
			
		}
		return bean;
	}
	
	

	/**
	 * 解析空教室信息
	 * @param html
	 * @return 空教室名称
	 */
	public static List<String> parserClassroomNames(String html){
		if(html == null){
			return null;
		}
		Document document = Jsoup.parse(html);
		Elements tbodyEles = document.getElementsByTag("tbody");
		if(tbodyEles == null || tbodyEles.size() <= 0){
			return null;
		}
		Element tbodyEle = tbodyEles.get(0);
		Elements trEles = tbodyEle.getElementsByTag("tr");
		if(trEles == null){
			return null;
		}
		
		List<String> classrooms = new ArrayList<String>();
		for(int i = 0; i < trEles.size(); i++){
			Elements tdEles = trEles.get(i).getElementsByTag("td");
			if(tdEles.size() == 10) {
				i++;
				continue;
			}
			classrooms.add(tdEles.get(0).html());
		}
		return classrooms;
	}
	

	
	

}
