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
				<th colspan="5" class="sivunvaihto"><span id="uusiAsiakas">Lis�� uusi asiakas</span>
			</tr>
			<tr>
				<th class="tulostaulu" colspan="2">Hakusana:</th>
				<th colspan="2" class="tulostaulu"><input type="text" id="hakusana"></th>
				<th class="tulostaulu"><input type="button" value="hae" id="hakunappi"></th>
			</tr>
			<tr class="tulostaulu">
				<th>Etunimi</th>
				<th>Sukunimi</th>
				<th>Puhelin</th>
				<th>S�hk�posti</th>
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
						htmlteksti += "<td><span class='poista' onclick=poista('"+field.etunimi+field.sukunimi+"')>Poista</span></td>";
						htmlteksti += "</tr>";
						$("#listaus tbody").append(htmlteksti);
					})
				}
			})
		};
			function poista(etunimi, sukunimi){
				if(confirm("Poista henkil�: " + etunimi + " " + sukunimi +"?")){
					$.ajax({
						url: "asiakkaat/"+etunimi+'&'+sukunimi,
						type:"DELETE",
						dataType:"json",
						success: function(result){
						if(result.response==0) { //Jos poisto ei onnistu
							$("#ilmo").html("Asiakkaan poisto ep�onnistui");	
						} else if (result.response==1){ //Jos poisto onnistui
							$("#rivi_"+etunimi).css("background-color", "red");
							alert("Asiakkaan " + etunimi + " " +sukunimi+ " poisto onnistui.");
							haeAsiakkaat();
						}
					}});
				}
			};
	</script>
</body>
</html>