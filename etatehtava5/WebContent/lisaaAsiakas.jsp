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

<title>Asiakkaan lis‰ys</title>
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
					<td><input type="submit" id="tallenna" value="Lis‰‰"></td>
				</tr>
			</tbody>
		</table>
	</form>
	<span id="ilmo"></span>
	<script>
		$(document).ready(function() {
			$("#tiedot").validate({ //validointi jqueryn avulla objekteja k‰ytt‰en
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
					lisaaTiedot();
				}
			});

			$("#listaaAsiakas").click(function() {
				document.location = "listaaAsiakkaat.jsp"
			})
		});

		function lisaaTiedot() {
			var formJsonStr = formDataJsonStr($("#tiedot").serializeArray()); //Muutetaan lomakkeen tiedot jsoniksi
			$.ajax({
				url : "asiakkaat", //Mihin otetaan yhteys
				data : formJsonStr, //Muuttuja joka sis‰lt‰‰ tiedot
				type : "POST", //Mink‰ tyyppinen html kutsu l‰hetet‰‰n
				dataType : "json", //Mink‰ muotoista dataa on kyseess‰
				success : function(result) {
					if (result.response == 0) {
						$("#ilmo").html("Asiakkaan lis‰‰minen ep‰onnistui.");
					} else if (result.response == 1) {
						$("#ilmo").html("Asiakkaan lis‰‰minen onnistui.");
						$("#etunimi", "#sukunimi", "#puhelin", "#sposti").val("");
					}
				}
			});
		}
	</script>
</body>
</html>