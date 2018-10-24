package JEMP;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.oreilly.servlet.MultipartRequest;

@MultipartConfig
@WebServlet("/Asimetrico")
public class Asimetrico extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public Asimetrico() { super(); }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String s = "C:\\Users\\jose mundo\\.eclipse\\WorkSpace\\Security2\\archvos";
		response.setContentType("text/html");
		new MultipartRequest(request,s);
		response.getWriter().print("Archivos enviados.");
		new OpServer(s);
	}	
}