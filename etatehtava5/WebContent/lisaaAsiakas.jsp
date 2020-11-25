<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://ajax.aspnetcdn.com/ajax/jquery.validate/1.17.0/jquery.validate.min.js"></script>
<style>
.sivunvaihto {
	text-align: right;
	cursor: pointer;
}
</style>

<meta charset="ISO-8859-1">
<title>Asiakkaan lis�ys</title>
</head>
<body>
	<table id="tiedot">
		<thead>
			<tr>
				<th colspan="5" class="sivunvaihto"><span id="listaaAsiakas">Takaisin
						asiakkaiden listaukseen</span></th>
			</tr>
			<tr>
				<th>Etunimi</th>
				<th>Sukunimi</th>
				<th>Puhelin</th>
				<th>S�hk�posti</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td><input type="text" name="etunimi" id="etunimi"></td>
				<td><input type="text" name="sukunimi" id="sukunimi"></td>
				<td><input type="text" name="puhelin" id="puhelin"></td>
				<td><input type="text" name="sposti" id="sposti"></td>
				<td><input type="submit" id="tallenna" value="Lis��"></td>
			</tr>
		</tbody>

	</table>
	<script>
		$(document).ready(function() {
			$("#tiedot").validate({
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
			var formJsonStr = formDataJsonStr($("#tiedot").serializeArray());
		}
	</script>
</body>
</html>