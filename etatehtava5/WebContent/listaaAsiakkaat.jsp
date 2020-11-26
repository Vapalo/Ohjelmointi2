<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<link rel="stylesheet" type="text/css" href="css/main.css">
<meta charset="ISO-8859-1">
<title>Asiakkaiden listaus</title>
</head>
<body>
	<table id="listaus">
		<thead>
			<tr>
				<th colspan="4" class="sivunvaihto"><span id="uusiAsiakas">Lisää uusi asiakas</span>
			</tr>
			<tr>
				<th class="hakuteksti">Hakusana:</th>
				<th colspan="2"><input type="text" id="hakusana"></th>
				<th><input type="button" value="hae" id="hakunappi"></th>
			</tr>
			<tr id="otsikot">
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
			
			$("#uusiAsiakas").click(function() {
				document.location="lisaaAsiakas.jsp";
			});
			
			
			haeAsiakkaat();
			$("#hakunappi").click(function() { //Hakunappia painaessa suoritetaan haeAsiakkaat funktio uudelleen hakusana arvolla
				haeAsiakkaat();
			});

			$(document.body).on("keydown", function(event) { //Tämän avulla saadaan hakutoiminto suoritettua. 13 on enterin value eventissä
				if (event.which == 13) {
					haeAsiakkaat();
				}
			});

			$("#hakusana").focus(); //Tällä sadaan cursori hakukenttään kun sivu latautuu

		});

		function haeAsiakkaat() {
			$("#listaus tbody").empty();
			$.ajax({
				url : "asiakkaat/" + $("#hakusana").val(), //Tässä hakusanan arvo välitetään eteenpäin servletille ja sitä kautta Daolle
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
			})
		};
	</script>
</body>
</html>