<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastro de Integrante</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <h1>Cadastro de Integrante</h1>
    <form id="formCadastro">
        <label for="nome">Nome:</label>
        <input type="text" id="nome" name="nome" required>
        <label for="franquia">Franquia:</label>
        <input type="text" id="franquia" name="franquia" required>
        <label for="funcao">Função:</label>
        <input type="text" id="funcao" name="funcao" required>
        <button type="submit" id="btnCadastrar">Cadastrar</button>
    </form>
    <div id="mensagem"></div>
    <br><br>
    <a href="/index">Voltar</a>
    <script>
        $(document).ready(function () {
            $('#formCadastro').on('submit', function (event) {
                event.preventDefault(); //Impede o envio padrão do formulário

                //Desabilitar o botão de cadastro para evitar múltiplos cliques
                $('#btnCadastrar').prop('disabled', true);

                //Exibir uma mensagem de processamento
                $('#mensagem').text('Processando...').css('color', 'orange');

                //Coleta os dados do formulário
                const dadosForm = {
                    nome: $('#nome').val(),
                    franquia: $('#franquia').val(),
                    funcao: $('#funcao').val()
                };

                //Faz a requisição AJAX para o backend
                $.ajax({
                    url: 'http://localhost:8080/cadastro/integrante', // Endpoint do backend
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify(dadosForm), // Envia os dados em JSON
                    success: function (response) {
                        //Exibe a mensagem do backend no HTML
                        $('#mensagem').text(response.mensagem).css('color', 'green');

                        //Limpar os campos do formulário
                        $('#formCadastro')[0].reset();

                        //Habilitar novamente o botão de cadastro
                        $('#btnCadastrar').prop('disabled', false);
                    },
                    error: function (xhr) {
                        //Exibe a mensagem de erro do backend no HTML
                        const erroMensagem = xhr.responseJSON ? xhr.responseJSON.mensagem : 'Erro ao cadastrar integrante.';
                        $('#mensagem').text(erroMensagem).css('color', 'red');

                        //Habilitar o botão de cadastro novamente
                        $('#btnCadastrar').prop('disabled', false);
                    }
                });
            });
        });
    </script>
</body>
</html>