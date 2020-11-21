<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<meta charset="ISO-8859-1">
<title>Asiakkaiden listaus</title>
</head>
<body>
	<table id="listaus">
		<thead>
			<tr>
				<th>Etunimi</th>
				<th>Sukunimi</th>
				<th>Puhelin</th>
				<th>Sähköposti</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>

	<script>
		$(document).ready(function() {
			$.ajax({
				url : "asiakkaat",
				type : "GET",
				dataType : "json",
				success : function(result) {
					$.each(result.asiakkaat, function(i, field) {
						var htmlteksti;

						htmlteksti += "<tr>";
						htmlteksti += "<td>" + field.etunimi + "</td>";
						htmlteksti += "<td>" + field.sukunimi + "</td>";
						htmlteksti += "<td>" + field.puhelin + "</td>";
						htmlteksti += "<td>" + field.sposti + "</td>";
						htmlteksti += "</tr>";

						$("#listaus tbody").append(htmlteksti);

					})
				}
			});

		});
	</script>
</body>
</html>