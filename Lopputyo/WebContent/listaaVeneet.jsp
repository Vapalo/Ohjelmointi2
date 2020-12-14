<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/main.css">
<meta charset="ISO-8859-1">
<title>Mulla harrastus on</title>
</head>
<body onkeydown="tutkiKey(event)">
	<h1 class="otsikko">Tervetuloa veneiden listaukseen</h1>

	<table class="veneet">
		<thead>
			<tr>
				<th colspan="5">Veneiden listaus</th>
			</tr>
			
			<tr>
				<th colspan="3">Etsi: <input type="text" id="hakusana"></th>
				<th colspan="2"><input type="button" onClick="haeVeneet()" value="Hae">
			</tr>
			<tr>
				<th>Nimi</th>
				<th>Merkkimalli</th>
				<th>Pituus</th>
				<th>Leveys</th>
				<th>Hinta</th>
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
				htmlString+="<tr key=index>",
				htmlString+="<td>"+tulos.nimi+"</td>",
				htmlString+="<td>"+tulos.merkkimalli+"</td>",
				htmlString+="<td>"+tulos.pituus+"</td>",
				htmlString+="<td>"+tulos.leveys+"</td>",
				htmlString+="<td>"+tulos.hinta+"</td>"
			})
				document.getElementById("tbody").innerHTML = htmlString;
		})
	}
	
	haeVeneet();
	

</script>
</html>