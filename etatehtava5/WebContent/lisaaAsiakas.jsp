<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<script src="scripts/main.js"></script>

	<!-- src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"> -->

	<!-- src="https://ajax.aspnetcdn.com/ajax/jquery.validate/1.17.0/jquery.validate.min.js"> -->
<link rel="stylesheet" type="text/css" href="css/main.css">

<title>Asiakkaan lis‰ys</title>
</head>
<body onkeydown="onkoEnter(event)">
	<form id="tiedot" autocomplete="off">
		<table>
			<thead>
				<tr>
					<th colspan="5" class="sivunvaihto"><a href="listaaAsiakkaat.jsp" id="listaaAsiakas">Takaisin
							asiakkaiden listaukseen</a></th>
				</tr>
				<tr class="tulostaulu">
					<th>Etunimi</th>
					<th>Sukunimi</th>
					<th>Puhelin</th>
					<th>S‰hkˆposti</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><input type="text" name="etunimi" id="etunimi"></td>
					<td><input type="text" name="sukunimi" id="sukunimi"></td>
					<td><input type="text" name="puhelin" id="puhelin"></td>
					<td><input type="email" name="sposti" id="sposti"></td>
					<td><input type="button" id="tallenna" onClick="lisaaAsiakas()" value="Lis‰‰"></td>
				</tr>
			</tbody>
		</table>
	</form>
	<span id="ilmo"></span>
	<script>
	
	document.getElementById("etunimi").focus();
	
	const onkoEnter = (event) => {
		if (event.keyCode == 13){ //keycode 13 on enter
			lisaaAsiakas()
		}
	}
	
	const lisaaAsiakas = () => {
		let ilmo = ""
		if(document.getElementById("etunimi").value.length < 2){
			ilmo = "Etunimi ei kelpaa"
		} else if (document.getElementById("sukunimi").value.length < 2){
			ilmo = "Sukunimi ei kelpaa"
		} else if (document.getElementById("puhelin").value*1 != document.getElementById("puhelin").value){
			ilmo = "Puhelinnumero ei kelpaa"
		} else if (document.getElementById("sposti").value.length < 5) {
			ilmo = "S‰hkˆposti ei kelpaa"
		}
		if (ilmo != ""){
			document.getElementById("ilmo").innerHTML=ilmo
			setTimeout(() => {document.getElementById("ilmo").innerHTML=""}, 3000)
			return;
		}
		
		let formJson = formDataJsonStr(document.getElementById("tiedot")) //Muutetaan lomakkeen tiedot jsoniksi
		console.log(formJson)
		//Tiedot l‰hetet‰‰n eteenp‰in POST metodilla.
		//tiedot kulkevat kutsun bodyss‰
		fetch("asiakkaat", {
			method: 'POST',
			body: formJson
		})
		
		.then(response => response.json())
		.then(responseJson => {
			let vastaus = responseJson.response
			
			if(vastaus == 0) {
				document.getElementById("ilmo").innerHTML = "Asiakkaan lis‰ys ep‰onnistui"
			} else if (vastaus == 1) {
				document.getElementById("ilmo").innerHTML = "Asiakkaan lis‰ys onnistui"
			}
			setTimeout(() => {document.getElementById("ilmo").innerHTML=""}, 5000)
		})
		document.getElementById("tiedot").reset() //Tyhjennet‰‰n lomakkeiden sis‰ltˆ luovutuksen j‰lkeen
	}
			
	
	
	
	
//////////////////////////////////////////////////////    AJAX ///////////////////////////////////////////////////////////	
//	
//
//		$(document).ready(function() {
//			$("#tiedot").validate({ //validointi jqueryn avulla objekteja k‰ytt‰en
//				rules : {
//					etunimi : {
//						required : true,
//						minlength : 2
//					},
//					sukunimi : {
//						required : true,
//						minlength : 2
//					},
//					puhelin : {
//						required : true,
//						digits: true,
//						minlength : 4
//					},
//					sposti : {
//						required : true,
//						minlength : 5
//					}
//				},
//				messages : {
//					etunimi : {
//						required : "Puuttuu",
//						minlength : "Liian lyhyt"
//					},
//					sukunimi : {
//						required : "Puuttuu",
//						minlength : "Liian lyhyt"
//					},
//					puhelin : {
//						required : "Puuttuu",
//						minlength : "Liian lyhyt"
//					},
//					sposti : {
//						required : "Puuttuu",
//						minlength : "Liian lyhyt"
//					}
//				},
//
//				submitHandler : function(form) {
//					lisaaTiedot();
//				}
//			});
//
//			$("#listaaAsiakas").click(function() {
//				document.location = "listaaAsiakkaat.jsp"
//			})
//		});
//
//		function lisaaTiedot() {
//			var formJsonStr = formDataJsonStr($("#tiedot").serializeArray()); //Muutetaan lomakkeen tiedot jsoniksi
//			$.ajax({
//				url : "asiakkaat", //Mihin otetaan yhteys
//				data : formJsonStr, //Muuttuja joka sis‰lt‰‰ tiedot
//				type : "POST", //Mink‰ tyyppinen html kutsu l‰hetet‰‰n
//				dataType : "json", //Mink‰ muotoista dataa on kyseess‰
//				success : function(result) {
//					if (result.response == 0) {
//						$("#ilmo").html("Asiakkaan lis‰‰minen ep‰onnistui.");
//					} else if (result.response == 1) {
//						$("#ilmo").html("Asiakkaan lis‰‰minen onnistui.");
//						$("#etunimi", "#sukunimi", "#puhelin", "#sposti").val("");
//					}
//				}
//			});
//		}
	</script>
</body>
</html>