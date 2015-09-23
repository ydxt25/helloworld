import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class main {
	static void upload(String []path) throws Exception {
		
		String host = "192.168.0.101";
		int port = 1990;
		
		for (String p : path)
		{
			File file = new File(p);
			if(!file.exists())
			{
				System.out.println("file not found!!!");
				return;
			}
			Socket sock = new Socket(host, port);
			
			if(sock.isConnected())
			{
				OutputStream sout = sock.getOutputStream();
				InputStream sin = sock.getInputStream();
				System.out.println("--------------------");
				doupload(host,file,sout,sin);
			}
			else
			{
				System.out.println("socket is closed!!!");
			}
			
		}
		
	}

	

	static void getresp(InputStream in) throws Exception
	{
		byte []buf = new byte[1024*10];
		int len = 0;
		len = in.read(buf);
		System.out.println(len + new String(buf));
		return;
		
	}
	static void doupload(String host,File file,OutputStream sout,InputStream sin) throws Exception {
		String boundary = "--------jianglh";
		String begin = "--" + boundary + "\r\n";
		String end = "\r\n--" + boundary + "--" + "\r\n";
		String params = "Content-Disposition: form-data; name=file; filename=" + file.getName() + "\r\n";
		params += "Content-Type:application/octet-stream\r\n\r\n";
		long filelen = file.length();
		long len = filelen + begin.length() + end.length() + params.length();
		//OutputStream sout = sock.getOutputStream();
		String req = "POST /upload HTTP/1.0\r\n";
		req += "Host:" + host + "\r\n";
		req += "Content-Length:" + len + "\r\n";
		req += "Content-Type:multipart/form-data;boundary=" + boundary + "\r\n\r\n";
		req += begin;
		req += params;
		sout.write(req.getBytes());
		FileInputStream fin = new FileInputStream(file);
		int length = 0;
		byte []buf = new byte[1024];
		while((length = fin.read(buf))>0)
		{
			sout.write(buf,0,length);
		}
		sout.write(end.getBytes());
		
		getresp(sin);
		fin.close();
	}

	static void _upload(String path) {
		
			
	}

	static void download() throws IOException {
		Socket sock = new Socket("www.hao123.com", 80);
		byte buf[] = new byte[1024];
		InputStream sin = sock.getInputStream();
		OutputStream sout = sock.getOutputStream();
		String req = "GET / HTTP/1.1\r\nHost:www.hao123.com\r\n\r\n";
		sout.write(req.getBytes());
		int len = 0;
		while ((len = sin.read(buf)) > 0) {
			System.out.println(len);
		}
		System.out.println(len);
		sock.close();
	}
	static void listdir(File file)
	{
		if(!file.exists())
		{
			return;
		}
		System.out.println(file.getAbsolutePath());
		if(file.isDirectory())
		{
			for(File f: file.listFiles())
			{
				listdir(f);
			}
		}
		else
		{
			
		}
	}
	static byte []makePost(String host,Map<String,String> items) {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		String boundary = "--------jianglh";
		long len = 0;
		String begin = "--" + boundary + "\r\n";
		String end = "--" + boundary + "--" + "\r\n";
		
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> itm : items.entrySet())
		{
			sb.append(begin);
			String key = itm.getKey();
			String val = itm.getValue();
			sb.append("Content-Disposition: form-data; name=");
			sb.append(key);
			sb.append("\r\n\r\n");
			sb.append(val);
			sb.append("\r\n");
		}
		sb.append(end);
		len = sb.length();
		System.out.println(len);
		String req = "POST /test HTTP/1.0\r\n";
		req += "Host:" + host + "\r\n";
		req += "Content-Length:" + len + "\r\n";
		req += "Content-Type:multipart/form-data;boundary=" + boundary + "\r\n\r\n";
		req += sb.toString();
		
		return req.getBytes();
	}
	static void dopost() throws Exception
	{
		Map<String,String> params = new HashMap();
		params.put("jiang", "4444444444444");
		params.put("passwd", "666");
		String host = "192.168.0.101";
		int port = 1990;
		byte [] arr = makePost(host, params);
		System.out.println(arr.length);
		Socket sock = new Socket(host,port);
		OutputStream sout = sock.getOutputStream();
		sout.write(arr);
	}
	
	
	
	static byte []makePost(String host,Map<String,String> items,File file) throws Exception {
		ByteArrayOutputStream bout1 = new ByteArrayOutputStream();
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		String boundary = "--------jianglh";
		long len = 0;
		String begin = "--" + boundary + "\r\n";
		String end = "--" + boundary + "--" + "\r\n";
		
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> itm : items.entrySet())
		{
			sb.append(begin);
			String key = itm.getKey();
			String val = itm.getValue();
			sb.append("Content-Disposition: form-data; name=");
			sb.append(key);
			sb.append("\r\n\r\n");
			sb.append(val);
			sb.append("\r\n");
		}
		bout.write(sb.toString().getBytes());
		
		bout.write(begin.getBytes());
		bout.write(("Content-Disposition: form-data; name=file; filename=" + file.getName() + "\r\n").getBytes());
		bout.write("Content-Type:application/octet-stream\r\n\r\n".getBytes());
		InputStream fin = new FileInputStream(file);
		int l = 0;
		byte []buf = new byte[1024];
		while((l = fin.read(buf))>0)
		{
			bout.write(buf);
		}
		bout.write("\r\n".getBytes());
		
		bout.write(end.getBytes());
		len = bout.size();
		System.out.println(len);
		String req = "POST /test HTTP/1.0\r\n";
		req += "Host:" + host + "\r\n";
		req += "Content-Length:" + len + "\r\n";
		req += "Content-Type:multipart/form-data;boundary=" + boundary + "\r\n\r\n";
		bout1.write(req.getBytes());
		bout.writeTo(bout1);
		
		return bout1.toByteArray();
	}
	
	static void uploadform() throws Exception
	{
		Map<String,String> params = new HashMap();
		params.put("jiang", "4444444444444");
		params.put("passwd", "666");
		String host = "192.168.0.101";
		int port = 1990;
		byte [] arr = makePost(host, params,new File("e:\\123.jpg"));
		System.out.println(arr.length);
		Socket sock = new Socket(host,port);
		OutputStream sout = sock.getOutputStream();
		sout.write(arr);
	}
	
	public static void main(String[] args) {
		try {
			uploadform();
			//dopost();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//listdir(new File("E:\\quickfix"));
//		try {
//			String []files = {"e:/1.jpg","e:/2.jpg"};
//			upload(files);
//			//download();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
