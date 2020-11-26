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
				<th colspan="4" class="sivunvaihto"><span id="uusiAsiakas">Lis�� uusi asiakas</span>
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
				<th>S�hk�posti</th>
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

			$(document.body).on("keydown", function(event) { //T�m�n avulla saadaan hakutoiminto suoritettua. 13 on enterin value eventiss�
				if (event.which == 13) {
					haeAsiakkaat();
				}
			});

			$("#hakusana").focus(); //T�ll� sadaan cursori hakukentt��n kun sivu latautuu

		});

		function haeAsiakkaat() {
			$("#listaus tbody").empty();
			$.ajax({
				url : "asiakkaat/" + $("#hakusana").val(), //T�ss� hakusanan arvo v�litet��n eteenp�in servletille ja sit� kautta Daolle
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