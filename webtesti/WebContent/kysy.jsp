<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Calendar"%>
<!DOCTYPE html>
<html>
<head>
<style>

/*Tyylit viety suoraan w3schools sivuston esimerkistä
/* Style inputs with type="text", select elements and textareas */
input[type=text], select, textarea {
	width: 15%; /* Full width */
	padding: 12px; /* Some padding */
	border: 1px solid #ccc; /* Gray border */
	border-radius: 4px; /* Rounded borders */
	box-sizing: border-box;
	/* Make sure that padding and width stays in place */
	margin-top: 6px; /* Add a top margin */
	margin-bottom: 16px;
} /* Bottom margin */
</style>
<meta charset="ISO-8859-1">
<title>Käyttäjätietojen kysely</title>
</head>
<body>
	<form action="nayta.jsp" method="POST">
		Nimi: <input type=text name="nimi"> <br>
		<br>Sukunimi: <input type=text name="sukunimi"> <br>
		<br>Sähköposti: <input type=text name="sposti"> <br>
		<br>Puhelin: <input type=text name="puh"> <br>
		<br>Syntymävuosi: <input type=text name="svuosi"> <br>
		<br>
		<input type="submit" value="Lähetä">
	</form>

</body>
</html>