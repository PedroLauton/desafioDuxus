<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro de Time</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <h1>Cadastro de Time</h1>

    <form id="cadastroForm">
        <label for="data">Data:</label>
        <input type="date" name="data" id="data" required><br><br>

        <h3>Selecione os Integrantes:</h3>
        <div id="integrantes">
            <c:forEach var="integrante" items="${integrantes}">
                <input type="checkbox" name="integrantes" value="${integrante.id}">
                <label>${integrante.nome}</label><br>
            </c:forEach>
        </div><br>

        <button type="button" onclick="cadastrarTime()">Cadastrar Time</button>
    </form>
	
	<div id="mensagem"></div>
	
	<br><br>
	<a href="/index">Voltar</a>

	<script>
	    function cadastrarTime() {
	        const data = $("#data").val();
	        const integrantes = [];

	        // Obter IDs dos integrantes selecionados
	        $("#integrantes input:checked").each(function() {
	            const id = $(this).val();
	            const nome = $(this).next("label").text(); // Exemplo de captura do nome
	            integrantes.push({ id: id, nome: nome });
	        });

	        // Enviar a requisição via AJAX
	        $.ajax({
	            url: "${pageContext.request.contextPath}/cadastro/time",  // Endpoint do backend
	            type: "POST",
	            contentType: "application/json",
	            data: JSON.stringify({
	                data: data,
	                integrantes: integrantes
	            }),
	            success: function(response) {
	                // Exibe a mensagem de sucesso no HTML
	                $("#mensagem").text(response.message || "Time cadastrado com sucesso!").css("color", "green");
					
					// Limpar os campos do formulário
					$('#cadastroForm')[0].reset();
					                      
					                     
	            },
	            error: function(xhr, status, error) {
	                // Exibe a mensagem de erro no HTML
	                $("#mensagem").text("Erro ao cadastrar o time: " + (xhr.responseJSON ? xhr.responseJSON.message : error)).css("color", "red");
					
					// Limpar os campos do formulário
					$('#cadastroForm')[0].reset();
	            }
	        });
	    }
	</script>
</body>
</html>
