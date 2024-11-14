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
        <h3>Selecione a franquia:</h3>
        <div id="selecaoFranquias">
            <c:forEach var="franquia" items="${selecaoFranquias}">
                <input type="radio" name="franquia" value="${franquia}" onchange="filtrarIntegrantes('${franquia}')">
                <label>${franquia}</label><br>
            </c:forEach>
        </div><br>
        <div id="integrantesSection" style="display: none;">
            <h3>Selecione os Integrantes:</h3>
            <div id="integrantes">
                <c:forEach var="integrante" items="${integrantes}">
                    <div class="integrante" data-franquia="${integrante.franquia}" style="display: none;">
                        <input type="checkbox" name="integrantes" value="${integrante.id}">
                        <label>${integrante.nome}</label><br>
                    </div>
                </c:forEach>
            </div><br>
            <button type="button" onclick="cadastrarTime()">Cadastrar Time</button>
        </div>
    </form>
    <div id="mensagem"></div>
    <br><br>
    <a href="/index">Voltar</a>
    <script>
        function filtrarIntegrantes(franquiaSelecionada) {
            //Exibir a seção de integrantes e botão apenas ao selecionar uma franquia
            $("#integrantesSection").show();

            //Oculta todos os integrantes
            $(".integrante").hide();

            //Mostra apenas os integrantes da franquia selecionada
            $(".integrante").each(function() {
                const franquia = $(this).data("franquia");
                if (franquia === franquiaSelecionada) {
                    $(this).show();
                }
            });
        }

        function cadastrarTime() {
            const data = $("#data").val();
            const integrantes = [];

			if (!data) {
			   $("#mensagem").text("Por favor, selecione uma data.").css("color", "red");
			   return; //Interrompe o envio do formulário
			}
			
            //Obter IDs dos integrantes selecionados
            $("#integrantes input:checked").each(function() {
                const id = $(this).val();
                const nome = $(this).next("label").text();
                integrantes.push({ id: id, nome: nome });
            });
			
			//Verificar se pelo menos um integrante foi selecionado
			if (integrantes.length === 0) {
			    $("#mensagem").text("Selecione pelo menos um integrante para cadastrar o time.").css("color", "red");
			    return; //Interrompe o envio do formulário
			 }

            //Enviar a requisição via AJAX
            $.ajax({
                url: "${pageContext.request.contextPath}/cadastro/time",
                type: "POST",
                contentType: "application/json",
                data: JSON.stringify({
                    data: data,
                    integrantes: integrantes
                }),
                success: function(response) {
                    $("#mensagem").text(response.mensagem).css("color", "green");
                    $('#cadastroForm')[0].reset();
                    $("#integrantesSection").hide(); // Oculta a seção após o cadastro
                },
				error: function(xhr, status, error) {
				    //Verifica se o servidor retornou um JSON com a chave 'mensagem'
				    if (xhr.responseJSON && xhr.responseJSON.mensagem) {
				        $("#mensagem").text(xhr.responseJSON.mensagem).css("color", "red");
				    } else {
				        //Caso contrário, exibe uma mensagem genérica de erro
				        $("#mensagem").text("Erro ao cadastrar o time.").css("color", "red");
				    }
				}
            });
        }
    </script>
</body>
</html>
