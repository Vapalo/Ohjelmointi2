<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/main.css">
<meta charset="ISO-8859-1">
<title>Lopputyö</title>
</head>
<body onkeydown="tutkiKey(event)">
	<h1 class="otsikko">Tervetuloa veneiden listaukseen</h1>

	<table class="veneet">
		<thead>
			<tr>
				<th colspan="4">Veneiden listaus</th>
				<th colspan="4" id="ilmo"></th>
			</tr>

			<tr>
				<th colspan="4">Etsi: <input type="text" id="hakusana"
					autocomplete="off"> <input type="button" onClick="haeVeneet()"
					value="Hae"></th>
				<th colspan="4"><a href="lisaaVene.jsp">Lisää vene</a></th>
			</tr>
			<tr>
				<th>Tunnus</th>
				<th>Nimi</th>
				<th>Malli</th>
				<th>Pituus</th>
				<th>Leveys</th>
				<th>Hinta &euro;</th>
				<th colspan="2"></th>
			<tr>
		</thead>
		<tbody id="tbody">
		</tbody>
	</table>
</body>
<script>

	const tutkiKey = (event) => { //Jos painettu nappain on enter, suorita veneiden haku
		if(event.keyCode == 13){
			haeVeneet()
		}
	}
	document.getElementById("hakusana").focus();
	
	const haeVeneet = () => {
		document.getElementById("tbody").innerHTML="";
		
		fetch("veneet/" + document.getElementById("hakusana").value, {
			method: "GET"
		})
		.then(response => response.json())
		.then(responseJson => {
			console.log(responseJson)
			let veneet = responseJson.veneet;
			let htmlString = "";
			
			veneet.map((tulos, index) => {
				htmlString += "<tr key=index>",
				htmlString += "<td>"+tulos.tunnus+"</td>"
				htmlString += "<td>"+tulos.nimi+"</td>",
				htmlString += "<td>"+tulos.merkkimalli+"</td>",
				htmlString += "<td>"+tulos.pituus+"</td>",
				htmlString += "<td>"+tulos.leveys+"</td>",
				htmlString += "<td>"+tulos.hinta+"</td>",
				htmlString += "<td><a href='muutaVene.jsp?id="+tulos.tunnus+"'>Muuta</td>",
				htmlString += "<td><span class='poista' onclick=poista("+tulos.tunnus+")>Poista</span></td>",
				htmlString += "</tr>"
			})
				document.getElementById("tbody").innerHTML = htmlString;
		})
	}
	haeVeneet();
	
	const poista = (tunnus) => {
		if (confirm("Oletko varma, että haluat poistaa veneen jonka tunnus on: " +tunnus+ "?")) {
			
			fetch("veneet/" + tunnus, {
				method: "DELETE"
			})
			.then(response => response.json())
			.then(responseJson => {
				let vastaus = responseJson.response;
				
				if(vastaus == 0){
					document.getElementById("ilmo").innerHTML="Veneen poisto epäonnistui";
				} else if (vastaus == 1){
					document.getElementById("ilmo").innerHTML="Veneen poisto onnistui";
				}
				haeVeneet();
			})
			
		}
	}
	

</script>
</html>