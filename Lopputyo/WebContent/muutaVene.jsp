<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<script src="scripts/main.js"></script>
<link rel="stylesheet" type="text/css" href="css/main.css">
<meta charset="ISO-8859-1">
<title>Lopputyö</title>
</head>
<body onkeydown="tutkiKey(event)">
	<h1 class="otsikko">Muuta veneen tietoja!</h1>

	<form id="tiedot" autocomplete="off">
		<table class="veneet">
			<thead>
				<tr>
					<th colspan="2">Veneen tiedot</th>
					<th colspan="2" id="ilmo"></th>
					<th colspan="2"><a href="listaaVeneet.jsp">Takaisin
							listaukseen</a></th>
				</tr>
				<tr>
					<th>Nimi</th>
					<th>Malli</th>
					<th>Pituus</th>
					<th>Leveys</th>
					<th>Hinta &euro;</th>
					<th></th>
				<tr>
			</thead>
			<tbody>
				<tr>
					<td><input type="text" name="nimi" id="nimi"></td>
					<td><input type="text" name="merkkimalli" id="merkkimalli">
					</td>
					<td><input type="text" name="pituus" id="pituus"></td>
					<td><input type="text" name="leveys" id="leveys"></td>
					<td><input type="text" name="hinta" id="hinta"></td>
					<td><input type="button" id="tallenna" onClick="muutaVene()"
						value="Tallenna"></td>
				</tr>
			</tbody>
		</table>
		<input type="hidden" name="tunnus" id="tunnus">
	</form>
</body>
<script>

	const tutkiKey = (event) => { //Jos painettu nappain on enter, suorita veneiden haku
		if(event.keyCode == 13){
			muutaVene();
		}
	}
	
	
	document.getElementById("nimi").focus();
	
	let tunnus = requestURLParam("id")
	
	fetch("veneet/haeyksi/" + tunnus, {
		method: "GET"
	})
	.then(response => response.json())
	.then(responseJson => {
		document.getElementById("tunnus").value=responseJson.tunnus
		document.getElementById("nimi").value=responseJson.nimi
		document.getElementById("merkkimalli").value=responseJson.merkkimalli
		document.getElementById("pituus").value=responseJson.pituus
		document.getElementById("leveys").value=responseJson.leveys
		document.getElementById("hinta").value=responseJson.hinta
	})
	
	const muutaVene = () => {
		let ilmo = "";
		
		
		if(document.getElementById("nimi").value.length < 2) {
			ilmo = "Nimi ei kelpaa"
		} else if (document.getElementById("merkkimalli").value.length < 2) {
			ilmo = "Malli ei kelpaa"
		} else if (document.getElementById("pituus").value.length == 0 || document.getElementById("pituus").value*1 != document.getElementById("pituus").value){
			ilmo = "Pituuden tulee olla numero"
		} else if (document.getElementById("leveys").value.length == 0 || document.getElementById("leveys").value < 0 || document.getElementById("leveys").value*1 != document.getElementById("leveys").value) {
			ilmo = "Leveyden tulee olla numero"
		} else if (document.getElementById("leveys").value.length == 0 || document.getElementById("hinta").value*1 != document.getElementById("hinta").value) {
			ilmo = "Hinnan tulee olla numero"
		}
		
		if (ilmo != "") { //Jos ilmoituksessa on tekstiä (virhe on tapahtunut) näytetään virheteksti 
			//Ja tyhjennetään se 3sec päästä. Funktion suoritus loppuu tähän mikäli virhe on tapahtunut
			document.getElementById("ilmo").innerHTML = ilmo;
			setTimeout(() => {document.getElementById("ilmo").innerHTML=""}, 3000)
			return;
		}
		
		let formJson = formDataJsonStr(document.getElementById("tiedot"))
		//Muutetaan formin tiedot jsoniksi main.js scriptin avulla
		
		fetch("veneet" , {
			method: "PUT",
			body: formJson //Annetaan kutsulle bodyksi jsoniksi muutetut formin tiedot
		})
		.then(response => response.json())
		.then(responseJson => {
			let vastaus = responseJson.response
			
			if (vastaus == 0) { //Jos palautus bäkkäriltä kertoo epäonnistumisen
				document.getElementById("ilmo").innerHTML = "Veneen muutos epäonnistui"
			} else if (vastaus == 1) { //Jos bäkkäri ilmoittaa onnistumisesta
				document.getElementById("ilmo").innerHTML = "Veneen muutos onnistui. Uudelleenohjataan listaukseen"
				
					setTimeout(() => {
						document.location.href="listaaVeneet.jsp"
					}, 3000)
			}
	
		})
	}
	

</script>
</html>