

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenerateTool {
	static String data = ""; // xml转成String后的字符串，用以后面的处理
	static String layoutNames = "R.layout.activity_main"; // 解析的layout名
	static String GenerateActivityNames = "TestGenerateActivity"; // 生成的Activity的名字
	static boolean showOnclick = true; // 是否自动生成onclick函数
	static boolean showHandler = true; // 是否自动生成handler
	static ArrayList<HashMap<String, Object>> parameter = new ArrayList<HashMap<String, Object>>(); // 解析出来对应的控件名和id名
	static ArrayList<Map<String,Object>> onclicks = new ArrayList<Map<String,Object>>(); // 解析出来对应的onclick函数名
	static List<String> funcNames = new ArrayList<String>();

	public static void main(String[] args) {
		getData(Constant.workingPath, layoutNames, GenerateActivityNames,
				Constant.pkgNAme); // 获取xml数据源
		System.out.println("data: " + data);
		dealWithData(data); // 解析数据源
		generate(Constant.workingPath, layoutNames, GenerateActivityNames,
				Constant.pkgNAme, showOnclick, showHandler); // 按条件生成java（activity）文件
	}

	public static void getData(String workingPath, String layoutName,
			String GenerateActivityName, String pkgNAme) {
		File f = new File(workingPath + "/res" + "/" + "layout" + "/"
				+ layoutName.replace("R.layout.", "") + ".xml");
		FileInputStream fis = null;

		// 因为File没有读和写的能力，所以需要使用InputStream流
		try {

			fis = new FileInputStream(f);

			// 定义一个字节数组，相当于缓存
			byte[] bytes = new byte[1024];
			// 得到实际读取到的字节数
			int n = 0;

			// 循环读取
			while ((n = fis.read(bytes)) != -1) {
				// 把字节转成String
				String s = new String(bytes, 0, n);
				data += s;
				// System.out.println(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 不管出现异常与否，一定会执行的句子
				// 关闭文件流“必须”放着
				fis.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	public static void dealWithData(String data) {

		String items[] = data.split(">");
		System.out.println("一共有 " + items.length + " 项" + "\n每项分别是： " + "\n");
		System.out.println("------------");
		for (String item : items) {
			System.out.println("Item:  " + item);
			System.out.println("------------");
			if (item.contains("android:id=")) { // 有定义id的地方
				int startPosition = item.indexOf("<");
				int endPosition = item.indexOf("android");
				String viewName = item
						.substring(startPosition + 1, endPosition)
						.replace(" ", "").replace("\n", "").replace("\r", "");// 得到控件名
				System.out.println("控件名：" + viewName);

				int idPosition = item.indexOf("android:id");

				int startPosition2 = item.indexOf("/", idPosition);
				int endPosition2 = item.indexOf("\"", startPosition2);

				System.out.println("idPosition: " + idPosition
						+ "startPosition2: " + startPosition2
						+ "endPosition2: " + endPosition2);
				String id = item.substring(startPosition2 + 1, endPosition2)
						.replace(" ", "").replace("\n", "").replace("\r", "");// 得到id值
				System.out.println("id名：" + id);
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("viewName", viewName);
				map.put("viewId", id);
				parameter.add(map);
				
				if(showOnclick&&item.contains("android:onClick")){
					int onclickPosition = item.indexOf("android:onClick");

					int startPosition3 = item.indexOf("\"", onclickPosition); //找第一个引号
					int endPosition3 = item.indexOf("\"", startPosition3+1);//找第二个引号
					String onclick = item.substring(startPosition3 + 1, endPosition3)
							.replace(" ", "").replace("\n", "").replace("\r", "");// 得到两个引号之间的onclick值
					System.out.println("click函数： "+onclick);
					boolean exsitFlag = false;
					for(String func:funcNames){   //看有没有存在这个函数，不存在才添加进去
						if(onclick.equals(func)){
							exsitFlag = true;
							break;
						}
						else continue;
					}
					
					if(!exsitFlag){
						funcNames.add(onclick);
					}
					Map<String,Object> clickMap = new HashMap<String, Object>();
					clickMap.put("clickFunc", onclick);
					clickMap.put("id", id);
					onclicks.add(clickMap);
			
				}

			} else{
				continue;
			}
				
		}

	}

	public static void generate(String workingPath, String layoutName,
			String GenerateActivityName, String pkgNAme,
			boolean showOnclickTag, boolean showHandlerTag) {
		String s[] = pkgNAme.split("\\.");
		String javaRelativePath = "";
		for (String tempS : s) {
			javaRelativePath += tempS;
			javaRelativePath += "/";

		}
		System.out.println("javaRelativePath: " + javaRelativePath);
		File f = new File(workingPath + "/src" + "/" + javaRelativePath
				+ GenerateActivityName + ".java");
		System.out.println("f.getAbsolutePath():" + f.getAbsolutePath());
		// 字节输入流
		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream(f);
			String parametersDefine = ""; // 定义变量
			for (HashMap<String, Object> map : parameter) {
				parametersDefine += map.get("viewName").toString() + " "
						+ map.get("viewId").toString() + " =null;" + "\r\n";
			}

			String parameterInitial = "";
			for (HashMap<String, Object> map : parameter) { // 为变量赋值
				parameterInitial += (map.get("viewId").toString() + " = ("
						+ map.get("viewName").toString() + ")"
						+ "findViewById(R.id." + map.get("viewId").toString()
						+ ");" + "\r\n");
			}
			// \r\n是回车换行起作用
			String onCreate = " public class " + GenerateActivityName
					+ " extends Activity{" + " \r\n" + parametersDefine
					+ "	protected void onCreate(Bundle savedInstanceState) {"
					+ "\r\n" + "super.onCreate(savedInstanceState);" + "\r\n"
					+ "setContentView(" + layoutName + ");" + "\r\n"
					+ "initial();" + "\r\n" + "}" + "\r\n";
			String initial = "public void initial(){" + "\r\n"
					+ parameterInitial +

					"}" + "\r\n";
			String myOnclick = "";
			if (showOnclickTag) {
				if(onclicks!=null&&onclicks.size()!=0){
					for(String func:funcNames){
						String tempS = "";
					for(Map<String,Object> item:onclicks){
//						myOnclick = "public void myOnclick(View v){"+"\r\n"+
//								"switch(v.getId()){"+"\r\n"+
//								"case R.id."+item.get("id")+":"+"\r\n"+
//								"break;"+"\r\n"+
//								"}"+"\r\n"+
//								"}";
						if(item.get("clickFunc").equals(func)){
							 tempS += "case R.id."+item.get("id")+":"+"\r\n"+"break;"+"\r\n";
						}
					}
					myOnclick += "public void "+func+"(View v){"+"\r\n"+
					"switch(v.getId()){"+"\r\n"+
					tempS+
					"}"+"\r\n"+
					"}";
					}
				}
				else{
					
//					public void myOnclick(View v){
//						switch(v.getId()){
//						case R.id.hello:
//							break;
//						}
//					}
					myOnclick = "public void myOnclick(View v){"+"\r\n"+
							"switch(v.getId()){"+"\r\n"+
							"default : break;" +"\r\n"+
							"}"+"\r\n"+
							"}";
							
				}
			}

			String myHandler = "";
			if (showHandlerTag) {
				myHandler = "Handler handler = new Handler(){" + "\r\n" +

				"public void handleMessage(Message msg) {" + "\r\n"
						+ "switch(msg.what){" + "\r\n" + "case 0:" + "\r\n"
						+ "break;" + "\r\n" + "case 1:" + "\r\n" + "break;"
						+ "\r\n" + "}" + "\r\n" + "super.handleMessage(msg);"
						+ "\r\n" + "}" + "\r\n" +

						"};" + "\r\n";
			}

			String end = "\r\n" + "}";

			String packageName = "package " + pkgNAme + ";";
			// 定义字节数组
			// byte []bytes=new byte[1024];
			// 如何把String——>bytes数组

			// 如果s串不大的话直接转
			fos.write(packageName.getBytes());
			fos.write(onCreate.getBytes());
			fos.write(initial.getBytes());
			fos.write(myOnclick.getBytes());
			fos.write(myHandler.getBytes());
			fos.write(end.getBytes());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}

}
