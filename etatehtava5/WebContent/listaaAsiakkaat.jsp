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
				<th colspan="6" class="sivunvaihto"><span id="uusiAsiakas">Lisää uusi asiakas</span>
			</tr>
			<tr>
				<th class="tulostaulu" colspan="3">Hakusana:</th>
				<th colspan="2" class="tulostaulu"><input type="text" id="hakusana"></th>
				<th class="tulostaulu"><input type="button" value="hae" id="hakunappi"></th>
			</tr>
			<tr class="tulostaulu">
				<th>Asiakas ID </th>
				<th>Etunimi</th>
				<th>Sukunimi</th>
				<th>Puhelin</th>
				<th>Sähköposti</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
	
	<span id="ilmo"></span>

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
						htmlteksti += "<td>" + field.asiakas_id + "</td>";
						htmlteksti += "<td>" + field.etunimi + "</td>";
						htmlteksti += "<td>" + field.sukunimi + "</td>";
						htmlteksti += "<td>" + field.puhelin + "</td>";
						htmlteksti += "<td>" + field.sposti + "</td>";
						htmlteksti += "<td><span class='poista' onclick=poista("+field.asiakas_id+")>Poista</span></td>";
						htmlteksti += "</tr>";
						$("#listaus tbody").append(htmlteksti);
					})
				}
			})
		};
			function poista(asiakas_id){
				if(confirm("Poista asiakas jonka id on: " + asiakas_id +"?")){
					$.ajax({
						url: "asiakkaat/"+asiakas_id,
						type:"DELETE",
						dataType:"json",
						success: function(result){
						if(result.response==0) { //Jos poisto ei onnistu
							$("#ilmo").html("Asiakkaan poisto epäonnistui");	
						} else if (result.response==1){ //Jos poisto onnistui
							
							alert("Asiakkaan jonka id on:  " +asiakas_id+ " poisto onnistui.");
							haeAsiakkaat();
						}
					}});
				}
			};
	</script>
</body>
</html>