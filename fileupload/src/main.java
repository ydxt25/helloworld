import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class main {
	static void upload(File file) throws Exception
	{
		FileInputStream fin = new FileInputStream(file);
		byte buf[] = new byte[(int) file.length()];
		fin.read(buf, 0, (int)file.length());
		doupload(buf,file.getName());
		fin.close();
	}
	static byte makePost(String path,String host)
	{
		return 3;
	}
	static void doupload(byte[] buffer,String name) throws Exception
	{
		String boundary = "--------jianglhu7u7";
		String begin = "--" + boundary + "\r\n";
		String params = "Content-Disposition: form-data; name=file; filename=" + name + "\r\n";
		params += "Content-Type:application/octet-stream\r\n\r\n";
		String end = "\r\n--" + boundary + "--" + "\r\n";
		Socket sock = new Socket("192.168.0.100", 1990);
		OutputStream sout = sock.getOutputStream();
		
		int len = buffer.length + begin.length() + end.length() + params.length();
		System.out.println(len);
		String req = "POST /upload HTTP/1.0\r\n";
		req += "Host:192.168.0.100\r\n";
		req += "Content-Length:" + len + "\r\n";
		req += "Content-Type:multipart/form-data;boundary=" + boundary + "\r\n\r\n";
		req += begin;
		req += params;
		sout.write(req.getBytes());
		sout.write(buffer);
		
		sout.write(end.getBytes());
		sock.close();
		
	}
	static void _upload()
	{
		File file = new File("e:/Temp");
		for (File f : file.listFiles())
		{
			if (f.isFile())
			{
				try
				{
					upload(f);
					return ;
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	static void download() throws IOException
	{
		Socket sock = new Socket("www.hao123.com",80);
		byte buf[] = new byte[1024];
		InputStream sin = sock.getInputStream();
		OutputStream sout = sock.getOutputStream();
		String req = "GET / HTTP/1.1\r\nHost:www.hao123.com\r\n\r\n";
		sout.write(req.getBytes());
		int len = 0;
		while((len = sin.read(buf))>0)
		{
			System.out.println(len);
		}
		System.out.println(len);
		sock.close();
	}
	public static void main(String []args)
	{
		try {
			download();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
