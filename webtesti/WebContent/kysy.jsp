<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Calendar"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="nayta.jsp" method="POST">
		Nimi: <input type=text name="nimi"> <br><br>Sukunimi: <input type=text
			name="sukunimi"> <br><br>Sähköposti: <input type=text name="sposti">
		<br><br>Puhelin: <input type=text name="puh"> <br><br>Syntymävuosi: <input
			type=text name="svuosi"> <br><br><input type="submit" value="Lähetä">
	</form>

</body>
</html>