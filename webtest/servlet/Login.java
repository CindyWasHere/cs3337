package webtest.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import webtest.service.DbService;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Login() {
		super();
	}

	// Goes to the Login.jsp page
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/Login.jsp").forward(request, response);
	}

	// Retrieves data from Login.jsp and uses the DbService to look in the database
	// if there is the email and password
	// If so, it forwards to Index.jsp
	// If not, it sends error and sends back to Login.jsp
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		DbService dbService = new DbService();
		if (dbService.loginUser(email, password)) {
			response.sendRedirect("Index");
		} else {
			out.print("Failed");
			// Create a window pop-up to say that the email/password is incorrect
//			response.sendRedirect("Login");
		}
	}
}