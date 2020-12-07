<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>

<!-- src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"  -->	
<link rel="stylesheet" type="text/css" href="css/main.css">
<meta charset="ISO-8859-1">
<title>Asiakkaiden listaus</title>
</head>
<body onkeydown="tutkiKey(event)">
	<table id="listaus">
		<thead>
			<tr>
				<th colspan="7" class="sivunvaihto"><a  href="lisaaAsiakas.jsp"id="uusiAsiakas">Lisää
						uusi asiakas</a>
			</tr>
			<tr>
				<th class="tulostaulu" colspan="4">Hakusana:</th>
				<th colspan="2" class="tulostaulu"><input type="text"
					id="hakusana"></th>
				<th class="tulostaulu"><input type="button" value="hae"
					id="hakunappi"></th>
			</tr>
			<tr class="tulostaulu">
				<th>Asiakas ID</th>
				<th>Etunimi</th>
				<th>Sukunimi</th>
				<th>Puhelin</th>
				<th>Sähköposti</th>
				<th></th>
				<th></th>
			</tr>
		</thead>
		<tbody id="tbody">
		</tbody>
	</table>

	<span id="ilmo"></span>

	<script>
	document.getElementById("hakusana").focus()
	const tutkiKey = (event) => { //Onko painettu key enter. Jos on haetaan tiedot
		if(event.keyCode == 13){
			haeAsiakkaat();
		}
	}
		const haeAsiakkaat = () => {
			document.getElementById("tbody").innerHTML="";
			fetch("asiakkaat/" + document.getElementById("hakusana").value, {
				method: "GET"
			})
			.then(response => response.json())
			.then(responseJson => {
				console.log(responseJson)
				let asiakkaat = responseJson.asiakkaat;
				let htmlstr = "";
				
				asiakkaat.map((tulos, index) => (
							htmlstr+="<tr key=index>",
							htmlstr+="<td>"+tulos.asiakas_id+"</td>",
							htmlstr+="<td>"+tulos.etunimi+"</td>",
							htmlstr+="<td>"+tulos.sukunimi+"</td>",
							htmlstr+="<td>"+tulos.puhelin+"</td>",
							htmlstr+="<td>"+tulos.sposti+"</td>",
							htmlstr += "<td><a href='muutaAsiakas.jsp?id="+tulos.asiakas_id+"'>Muuta&nbsp;</td>",
							htmlstr += "<td><span class='poista' onclick=poista("+tulos.asiakas_id+")>Poista</span></td>",
							htmlstr+="</tr>"
				))
				document.getElementById("tbody").innerHTML= htmlstr;
			})
			
		}
		haeAsiakkaat();
		
		const poista = (asiakas_id) => {
			if (confirm("Oletko varma että haluat poistaa asiakkaan jonka id on: " + asiakas_id)){
				fetch("asiakkaat/" + asiakas_id, {
					method: 'DELETE'
				})
				.then(response => response.json())
				.then(responseJson => {
					let vastaus = responseJson.response;
					if(vastaus==0){
						document.getElementById("ilmo").innerHTML="Asiakkaan poisto epäonnistui";
					} else if (vastaus == 1){
						document.getElementById("ilmo").innetHTML="Asiakkaan poisto onnistui";
						
					}
					haeAsiakkaat();
				})
			}
		}
	
//		$(document).ready(function() {
//			
//			$("#uusiAsiakas").click(function() {
//				document.location="lisaaAsiakas.jsp";
//			});
//			
//			
//			haeAsiakkaat();
//			$("#hakunappi").click(function() { //Hakunappia painaessa suoritetaan haeAsiakkaat funktio uudelleen hakusana arvolla
//				haeAsiakkaat();
//			});

			
//			});

	//		$("#hakusana").focus(); //Tällä sadaan cursori hakukenttään kun sivu latautuu

		//});

//		function haeAsiakkaat() {
//			$("#listaus tbody").empty();
//			$.ajax({
//				url : "asiakkaat/" + $("#hakusana").val(), //Tässä hakusanan arvo välitetään eteenpäin servletille ja sitä kautta Daolle
//				type : "GET",
//				dataType : "json",
//				success : function(result) {
//					$.each(result.asiakkaat, function(i, field) {
//						var htmlteksti;
//						
//
//						htmlteksti += "<tr>";
//						htmlteksti += "<td>" + field.asiakas_id + "</td>";
//						htmlteksti += "<td>" + field.etunimi + "</td>";
//						htmlteksti += "<td>" + field.sukunimi + "</td>";
//						htmlteksti += "<td>" + field.puhelin + "</td>";
//						htmlteksti += "<td>" + field.sposti + "</td>";
//					htmlteksti += "<td><a href='muutaAsiakas.jsp?id="+field.asiakas_id+"'>Muuta&nbsp;";
//						htmlteksti += "<span class='poista' onclick=poista("+field.asiakas_id+")>Poista</span></td>";
//						htmlteksti += "</tr>";
//						$("#listaus tbody").append(htmlteksti);
//				//	})
			//	}
		//	})
	//	};
	//		function poista(asiakas_id){
		//		if(confirm("Poista asiakas jonka id on: " + asiakas_id +"?")){
			//		$.ajax({
		//				url: "asiakkaat/"+asiakas_id,
	//				type:"DELETE",
		//				dataType:"json",
			//			success: function(result){
			//			if(result.response==0) { //Jos poisto ei onnistu
			//				$("#ilmo").html("Asiakkaan poisto epäonnistui");	
			//			} else if (result.response==1){ //Jos poisto onnistui
							
		//					alert("Asiakkaan jonka id on:  " +asiakas_id+ " poisto onnistui.");
		//					haeAsiakkaat();
		//				}
		//			}});
		//		}
		//	};
	</script>
</body>
</html>