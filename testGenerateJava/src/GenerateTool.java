

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenerateTool {
	static String data = ""; // xmlת��String����ַ��������Ժ���Ĵ���
	static String layoutNames = "R.layout.activity_main"; // ������layout��
	static String GenerateActivityNames = "TestGenerateActivity"; // ���ɵ�Activity������
	static boolean showOnclick = true; // �Ƿ��Զ�����onclick����
	static boolean showHandler = true; // �Ƿ��Զ�����handler
	static ArrayList<HashMap<String, Object>> parameter = new ArrayList<HashMap<String, Object>>(); // ����������Ӧ�Ŀؼ�����id��
	static ArrayList<Map<String,Object>> onclicks = new ArrayList<Map<String,Object>>(); // ����������Ӧ��onclick������
	static List<String> funcNames = new ArrayList<String>();

	public static void main(String[] args) {
		getData(Constant.workingPath, layoutNames, GenerateActivityNames,
				Constant.pkgNAme); // ��ȡxml����Դ
		System.out.println("data: " + data);
		dealWithData(data); // ��������Դ
		generate(Constant.workingPath, layoutNames, GenerateActivityNames,
				Constant.pkgNAme, showOnclick, showHandler); // ����������java��activity���ļ�
	}

	public static void getData(String workingPath, String layoutName,
			String GenerateActivityName, String pkgNAme) {
		File f = new File(workingPath + "/res" + "/" + "layout" + "/"
				+ layoutName.replace("R.layout.", "") + ".xml");
		FileInputStream fis = null;

		// ��ΪFileû�ж���д��������������Ҫʹ��InputStream��
		try {

			fis = new FileInputStream(f);

			// ����һ���ֽ����飬�൱�ڻ���
			byte[] bytes = new byte[1024];
			// �õ�ʵ�ʶ�ȡ�����ֽ���
			int n = 0;

			// ѭ����ȡ
			while ((n = fis.read(bytes)) != -1) {
				// ���ֽ�ת��String
				String s = new String(bytes, 0, n);
				data += s;
				// System.out.println(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// ���ܳ����쳣���һ����ִ�еľ���
				// �ر��ļ��������롱����
				fis.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	public static void dealWithData(String data) {

		String items[] = data.split(">");
		System.out.println("һ���� " + items.length + " ��" + "\nÿ��ֱ��ǣ� " + "\n");
		System.out.println("------------");
		for (String item : items) {
			System.out.println("Item:  " + item);
			System.out.println("------------");
			if (item.contains("android:id=")) { // �ж���id�ĵط�
				int startPosition = item.indexOf("<");
				int endPosition = item.indexOf("android");
				String viewName = item
						.substring(startPosition + 1, endPosition)
						.replace(" ", "").replace("\n", "").replace("\r", "");// �õ��ؼ���
				System.out.println("�ؼ�����" + viewName);

				int idPosition = item.indexOf("android:id");

				int startPosition2 = item.indexOf("/", idPosition);
				int endPosition2 = item.indexOf("\"", startPosition2);

				System.out.println("idPosition: " + idPosition
						+ "startPosition2: " + startPosition2
						+ "endPosition2: " + endPosition2);
				String id = item.substring(startPosition2 + 1, endPosition2)
						.replace(" ", "").replace("\n", "").replace("\r", "");// �õ�idֵ
				System.out.println("id����" + id);
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("viewName", viewName);
				map.put("viewId", id);
				parameter.add(map);
				
				if(showOnclick&&item.contains("android:onClick")){
					int onclickPosition = item.indexOf("android:onClick");

					int startPosition3 = item.indexOf("\"", onclickPosition); //�ҵ�һ������
					int endPosition3 = item.indexOf("\"", startPosition3+1);//�ҵڶ�������
					String onclick = item.substring(startPosition3 + 1, endPosition3)
							.replace(" ", "").replace("\n", "").replace("\r", "");// �õ���������֮���onclickֵ
					System.out.println("click������ "+onclick);
					boolean exsitFlag = false;
					for(String func:funcNames){   //����û�д�����������������ڲ���ӽ�ȥ
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
		// �ֽ�������
		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream(f);
			String parametersDefine = ""; // �������
			for (HashMap<String, Object> map : parameter) {
				parametersDefine += map.get("viewName").toString() + " "
						+ map.get("viewId").toString() + " =null;" + "\r\n";
			}

			String parameterInitial = "";
			for (HashMap<String, Object> map : parameter) { // Ϊ������ֵ
				parameterInitial += (map.get("viewId").toString() + " = ("
						+ map.get("viewName").toString() + ")"
						+ "findViewById(R.id." + map.get("viewId").toString()
						+ ");" + "\r\n");
			}
			// \r\n�ǻس�����������
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
			// �����ֽ�����
			// byte []bytes=new byte[1024];
			// ��ΰ�String����>bytes����

			// ���s������Ļ�ֱ��ת
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
