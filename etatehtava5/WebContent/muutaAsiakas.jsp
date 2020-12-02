<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<script src="scripts/main.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://ajax.aspnetcdn.com/ajax/jquery.validate/1.17.0/jquery.validate.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/main.css">

<title>Asiakkaan lisäys</title>
</head>
<body>
	<form id="tiedot" autocomplete="off">
		<table>
			<thead>
				<tr>
					<th colspan="5" class="sivunvaihto"><span id="listaaAsiakas">Takaisin
							asiakkaiden listaukseen</span></th>
				</tr>
				<tr class="tulostaulu">
					<th>Etunimi</th>
					<th>Sukunimi</th>
					<th>Puhelin</th>
					<th>Sähköposti</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><input type="text" name="etunimi" id="etunimi"></td>
					<td><input type="text" name="sukunimi" id="sukunimi"></td>
					<td><input type="text" name="puhelin" id="puhelin"></td>
					<td><input type="email" name="sposti" id="sposti"></td>
					<td><input type="submit" id="tallenna" value="Hyväksy"></td>
				</tr>
			</tbody>
			
		</table>
		<input type="hidden" name="asiakasid" id="asiakasid">
	
	</form>
	<span id="ilmo"></span>
	<script>
		$(document).ready(function() {
			
			var asiakas_id = requestURLParam("id");
			//Haetaan vain yksi asiakas id backendistä.
			//Erotellaan yhden hakeminen tätä sivua varten muusta hakemisesta lisäämällä
			//haeyksi urliin
			$.ajax({
				url:"asiakkaat/haeyksi/"+asiakas_id, type:"GET", dataType:"json", success: function(result) {
					$("#asiakasid").val(result.asiakas_id);
					$("#etunimi").val(result.etunimi);
					$("#sukunimi").val(result.sukunimi);
					$("#puhelin").val(result.puhelin);
					$("#sposti").val(result.sposti);
					
					
				}
			});
			
			
			$("#tiedot").validate({ //validointi jqueryn avulla objekteja käyttäen
				rules : {
					etunimi : {
						required : true,
						minlength : 2
					},
					sukunimi : {
						required : true,
						minlength : 2
					},
					puhelin : {
						required : true,
						digits: true,
						minlength : 4
					},
					sposti : {
						required : true,
						minlength : 5
					}
				},
				messages : {
					etunimi : {
						required : "Puuttuu",
						minlength : "Liian lyhyt"
					},
					sukunimi : {
						required : "Puuttuu",
						minlength : "Liian lyhyt"
					},
					puhelin : {
						required : "Puuttuu",
						minlength : "Liian lyhyt"
					},
					sposti : {
						required : "Puuttuu",
						minlength : "Liian lyhyt"
					}
				},

				submitHandler : function(form) {
					muutaTiedot();
				}
			});

			$("#listaaAsiakas").click(function() {
				document.location = "listaaAsiakkaat.jsp"
			})
		});
		
		//Funktiolla muutetaan asiakkaan tiedot. Käytännössä sama asia kuin syötätminen, mutta html kutsu
		//on PUT eikä POIST
		function muutaTiedot() {
			var formJsonStr = formDataJsonStr($("#tiedot").serializeArray()); //Muutetaan lomakkeen tiedot jsoniksi
			$.ajax({
				url : "asiakkaat", //Mihin otetaan yhteys
				data : formJsonStr, //Muuttuja joka sisältää tiedot
				type : "PUT", //Minkä tyyppinen html kutsu lähetetään
				dataType : "json", //Minkä muotoista dataa on kyseessä
				success : function(result) {
					if (result.response == 0) {
						$("#ilmo").html("Asiakkaan tietojen muuttaminen epäonnistui.");
					} else if (result.response == 1) {
						$("#ilmo").html("Asiakkaan tietojen muuttaminen onnistui.");
						$("#etunimi", "#sukunimi", "#puhelin", "#sposti").val("");
					}
				}
			});
		}
	</script>
</body>
</html>