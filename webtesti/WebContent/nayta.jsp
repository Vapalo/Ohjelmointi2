<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Calendar"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Tulosten näyttö</title>
</head>
<body>
	<%
		String nimi = request.getParameter("nimi");
	String sukunimi = request.getParameter("sukunimi");
	String sahkoposti = request.getParameter("sposti");
	String puhelin = request.getParameter("puh");
	String svuosiSTR = request.getParameter("svuosi");
	
	Calendar calendar = Calendar.getInstance();
	int tamavuosi = calendar.get(Calendar.YEAR);
	
	try{
		int svuosi = Integer.parseInt(svuosiSTR);
		int ika = tamavuosi - svuosi;
		
		if(ika >= 18 ){
			out.print("Nimi: " + nimi + "<br>");
			out.print("Sukunimi: " + sukunimi + "<br>");
			out.print("Sähköposti: " + sahkoposti + "<br>");
			out.print("Puhelin: " + puhelin + "<br>");
			out.print("Ikä: " + ika + "<br>");
		} else {
			out.print("Alaikäinen");
			%>
			<script>
			setTimeout(function() {
			document.location.href='kysy.jsp',5000		
			}, 5000); 
			
			</script>
			<%
		}
	} catch (Exception e){
		out.print("Ikä ei ollut numero");
	}
	
	
	%>
</body>
</html>