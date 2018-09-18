package scrapy;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class PException extends Exception{
	PException(String msg){
		super(msg);
	}
}

public class Crawl{
	private static String
		ID 	 = "yourID",
		PASS = "yourPassword";
	private static Vector<Integer> solPids = new Vector<Integer>();
	private static Vector<String> solPnames = new Vector<String>();
	static Map<String, String> cookie;
	
	private static Connection.Response login(String ID, String PASS) throws IOException, PException{
		System.out.println("[Try] Login ID : "+ID);
		// get login page
		Connection.Response loginForm = Jsoup
		  .connect("https://www.acmicpc.net/login")
		  .method(Connection.Method.GET)
		  .execute();
		// post data to signin page
		Connection.Response response = Jsoup.connect("https://www.acmicpc.net/signin")
		        .data("login_user_id", ID)
		        .data("login_password", PASS)
		        .data("auto_login", "true")
		        .data("next", "/")
		        .cookies(loginForm.cookies())
		        .method(Connection.Method.POST)
		        .execute();
		cookie = response.cookies();
		

		Connection.Response home = Jsoup
				  .connect("https://www.acmicpc.net/")
				  .method(Connection.Method.GET)
				  .cookies(cookie)
				  .execute();
		if(home.parse().select("a.username").first()==null)
			throw new PException("Login Failed");
		return response;		
	}
	
	private static void getSolvedProblem(String ID, Vector<Integer> solPids, Vector<String> solPnames) throws IOException, PException{
		Connection.Response MyHome = Jsoup
				  .connect("https://www.acmicpc.net/user/"+ID)
				  .method(Connection.Method.GET)
				  .cookies(cookie)
				  .execute();
		Document home = MyHome.parse();
		Element tables = home.select("div.panel-body").first();
		Elements pids = tables.select("span.problem_number > a");
		Elements pnames = tables.select("span.problem_title > a");
		
		System.out.println("[Try] "+ID+"'s solved problems ");
		
		for(int i = 0; i < pnames.size(); i++){
			solPnames.add(pnames.get(i).text());
		}
		for(int i = 0; i < pids.size(); i++){
			solPids.add(new Integer(pids.get(i).text()));
		}
		System.out.println("[Get] Success, "+solPids.size()+" problems");
	}
	

	private static String getProblemSpec(Integer PID) throws IOException, PException{
		Connection.Response problem = Jsoup
				  .connect("https://www.acmicpc.net/problem/"+PID)
				  .method(Connection.Method.GET)
				  .cookies(cookie)
				  .execute();
		Document problemDoc = problem.parse();
		Element tables = problemDoc.select("div#problem-body").first();
		if(!tables.hasText())
			throw new PException("Get Problem failed");
		System.out.println("[Cvt] Success, Problem "+PID+"'s data");
		return tables.html();
	}

	private static Vector<String> getUserSourceAtPid(String ID, Integer PID) throws IOException, PException{
		Vector<String> res = new Vector<String>();
		Connection.Response status = Jsoup
				  .connect("https://www.acmicpc.net/status/?problem_id="+PID
						  	+"&user_id="+ID+"&language_id=-1&result_id=4&from_mine=1")
				  .method(Connection.Method.GET)
				  .execute();
		Document statusOfPid = status.parse();
		
		String srcID = statusOfPid.select("tbody > tr > td").first().text();

		
		Connection.Response src = Jsoup
				  .connect("https://www.acmicpc.net/source/"+srcID)
				  .method(Connection.Method.GET)
				  .cookies(cookie)
				  .execute();
		
		Document srcDoc = src.parse();
		String source = srcDoc.select("textarea[name=source]").first().text();
		if(source==null)
			throw new PException("Get Source failed");
		System.out.println("[Get] Success, Source code of "+PID);
		res.add(source);
		res.add(statusOfPid.select("tbody > tr > td").get(6).text());
		return res;
	}
	private static String getExtendByLang(String lang) throws PException{
		String exe=null;
		switch (lang) {
		case "C":
			exe = ".c";
			break;
		case "C (Clang)":
			exe = ".c";
			break;
		case "C11":
			exe = ".c";
			break;
		case "C++":
			exe = ".cpp";
			break;
		case "C++11":
			exe = ".cpp";
			break;
		case "C++14":
			exe = ".cpp";
			break;
		case "C++ (Clang)":
			exe = ".cpp";
			break;
		case "C++11 (Clang)":
			exe = ".cpp";
			break;
		case "C++14 (Clang)":
			exe = ".cpp";
			break;
		case "C# 4.0":
			exe = ".cs";
			break;
		case "Java":
			exe = ".java";
			break;
		case "Python":
			exe = ".py";
			break;
		case "Python2":
			exe = ".py";
			break;
		case "Python3":
			exe = ".py";
			break;
		case "PyPy":
			exe = ".py";
			break;
		case "PyPy3":
			exe = ".py";
			break;
		case "Ruby 2.2":
			exe = ".rb";
			break;
		case "Text":
			exe = ".txt";
			break;
		case "Go":
			exe = ".go";
			break;
		case "F#":
			exe = ".rb";
			break;
		case "Pascal":
			exe = ".pas";
			break;
		case "Lua":
			exe = ".lua";
			break;
		case "Perl":
			exe = ".pl";
			break;
		case "Fortran":
			exe = ".f";
			break;
		case "Scheme":
			exe = ".scm";
			break;
		case "Ada":
			exe = ".ada";
			break;
		// TXT
		case "awk":
			exe = ".txt";
			break;
		case "OCaml":
			exe = ".txt";
			break;
		case "Brainfuck":
			exe = ".txt";
			break;
		case "Whitespace":
			exe = ".txt";
			break;
		case "Tcl":
			exe = ".txt";
			break;
		case "Assembly (32bit)":
			exe = ".asm";
			break;
		case "D":
			exe = ".txt";
			break;
		case "Clojure":
			exe = ".txt";
			break;
		case "Rhino":
			exe = ".txt";
			break;
		case "Cobol":
			exe = ".cob";
			break;
		case "SpiderMonkey":
			exe = ".txt";
			break;
		case "Pike":
			exe = ".txt";
			break;
		case "sed":
			exe = ".txt";
			break;
		case "Rust":
			exe = ".txt";
			break;
		case "Intercal":
			exe = ".txt";
			break;
		case "bc":
			exe = ".bc";
			break;
		case "VB.NET 4.0":
			exe = ".vb";
			break;
		case "아희":
			exe = ".aheui";
			break;
		case "node.js":
			exe = ".js";
			break;
		default:
			break;
		}

		if(exe == null)
			throw new PException("Language not founded");
		return exe;
	}
	public static void main(String[] args){
		(new File("result/problem")).mkdirs();
		(new File("result/source")).mkdirs();
		
		try{
			login(ID,PASS);	
			getSolvedProblem(ID, solPids, solPnames);
		}catch(PException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}catch(IOException e){
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		
		for(int i = 0 ; i < solPids.size(); i++){
			String problem = null, source = null, lang = null;
			Vector<String> res;
			try{
				problem = getProblemSpec(solPids.get(i));
				res = getUserSourceAtPid(ID,solPids.get(i));
				source = res.get(0); lang = res.get(1);
				PrintWriter pOut = new PrintWriter("result/problem/"+solPids.get(i)+".txt","utf-8");
				PrintWriter sOut = new PrintWriter("result/source/"+solPids.get(i)+getExtendByLang(lang),"utf-8");
				pOut.write(problem);
				sOut.write(source);
				pOut.close();
				sOut.close();
			}catch(PException e){
				System.out.println(e.getMessage());
				e.printStackTrace();
			}catch(IOException e){
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			
			
		}
		
	}
}
